package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.service.VideoService;
import com.imooc.utils.PagedResult;
import com.imooc.utils.TimeAgoUtils;
import com.imooc.utils.org.n3r.idworker.Sid;
import com.imooc.vo.CommentsVO;
import com.imooc.vo.VideosVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @功能描述：TODO
 * @创建日期: 2019/3/30 18:12
 */
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideosMapper videosMapper;
    @Autowired
    private Sid sid;
    @Autowired
    private SearchRecordsMapper recordsMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private CommentsMapper commentMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public String saveVideo(Videos video) {
        String id = sid.nextShort();
        video.setId(id);
        videosMapper.insertSelective(video);
        return id;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateVideo(String videoId, String coverPath) {
        Videos video = new Videos();
        video.setId(videoId);
        video.setCoverPath(coverPath);
        videosMapper.updateByPrimaryKeySelective(video);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize) {
        //保存热搜词
        String desc = video.getVideoDesc();
        String userId = video.getUserId();
        if(isSaveRecord != null && isSaveRecord == 1){
            SearchRecords record = new SearchRecords();
            String recordId = sid.nextShort();
            record.setId(recordId);
            record.setContent(desc);
            recordsMapper.insert(record);
        }
        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapper.queryAllVideos(desc, userId);
        PageInfo<VideosVO> pageList = new PageInfo<>(list);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pageList.getTotal());
        return pagedResult;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<String> getHotWords() {
        return recordsMapper.getHotWords();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void userLikeVideo(String userId, String videoId, String videoCreaterId) {
        //1. 保存用户和视频的喜欢点赞关联关系表
        String likeId = sid.nextShort();
        UsersLikeVideos ulv = new UsersLikeVideos();
        ulv.setId(likeId);
        ulv.setUserId(userId);
        ulv.setVideoId(videoId);
        usersLikeVideosMapper.insert(ulv);

        //2. 视频喜欢数量累加
        videosMapper.addVideoLikeCount(videoId);

        //3. 用户受喜欢数量的累加
        usersMapper.addReceiveLikeCount(userId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId) {
        //1. 删除用户和视频的喜欢点赞关联关系表
        UsersLikeVideosExample example = new UsersLikeVideosExample();
        example.createCriteria().andUserIdEqualTo(userId).andVideoIdEqualTo(videoId);
        usersLikeVideosMapper.deleteByExample(example);


        //2. 视频喜欢数量累减
        videosMapper.reduceVideoLikeCount(videoId);

        //3. 用户受喜欢数量的累减
        usersMapper.reduceReceiveLikeCount(userId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapper.queryMyLikeVideos(userId);

        PageInfo<VideosVO> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setPage(page);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public PagedResult queryMyFollowVideos(String userId, Integer page, Integer pageSize) {
        PageHelper.startPage(page, pageSize);
        List<VideosVO> list = videosMapper.queryMyFollowVideos(userId);

        PageInfo<VideosVO> pageList = new PageInfo<>(list);

        PagedResult pagedResult = new PagedResult();
        pagedResult.setTotal(pageList.getPages());
        pagedResult.setRows(list);
        pagedResult.setPage(page);
        pagedResult.setRecords(pageList.getTotal());

        return pagedResult;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveComment(Comments comment) {
        String id = sid.nextShort();
        comment.setId(id);
        comment.setCreateTime(new Date());
        commentMapper.insert(comment);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedResult getAllComments(String videoId, Integer page, Integer pageSize) {

        PageHelper.startPage(page, pageSize);

        List<CommentsVO> list = commentMapper.queryComments(videoId);

        for (CommentsVO c : list) {
            String timeAgo = TimeAgoUtils.format(c.getCreateTime());
            c.setTimeAgoStr(timeAgo);
        }

        PageInfo<CommentsVO> pageList = new PageInfo<>(list);

        PagedResult grid = new PagedResult();
        grid.setTotal(pageList.getPages());
        grid.setRows(list);
        grid.setPage(page);
        grid.setRecords(pageList.getTotal());

        return grid;
    }
}
