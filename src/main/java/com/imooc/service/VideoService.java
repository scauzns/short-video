package com.imooc.service;

import com.imooc.pojo.Comments;
import com.imooc.pojo.Videos;
import com.imooc.utils.PagedResult;

import java.util.List;

/**
 * @功能描述：TODO
 * @创建日期: 2019/3/30 18:11
 */
public interface VideoService {
    /**
     * 保存视频
     */
    public String saveVideo(Videos video);

    /**
     * 修改视频的封面
     * @param videoId
     * @param coverPath
     * @return
     */
    public void updateVideo(String videoId, String coverPath);

    /**
     * 分页查询视频列表，并插入热搜记录
     * @param page
     * @param pageSize
     * @return
     */
    public PagedResult getAllVideos(Videos video, Integer isSaveRecord,  Integer page, Integer pageSize);

    /**
     * 查询热搜词
     * @return
     */
    public List<String> getHotWords();

    /**
     * 用户喜欢视频
     * @param userId
     * @param videoId
     * @param videoCreaterId
     */
    public void userLikeVideo(String userId, String videoId, String videoCreaterId);

    /**
     * 用户不喜欢视频/取消点赞
     * @param userId
     * @param videoId
     * @param videoCreaterId
     */
    public void userUnLikeVideo(String userId, String videoId, String videoCreaterId);

    /**
     * @Description: 查询我喜欢的视频列表
     */
    public PagedResult queryMyLikeVideos(String userId, Integer page, Integer pageSize);

    /**
     * @Description: 查询我关注的人的视频列表
     */
    public PagedResult queryMyFollowVideos(String userId, Integer page, Integer pageSize);

    /**
     * @Description: 用户留言
     */
    public void saveComment(Comments comment);

    /**
     * @Description: 留言分页
     */
    public PagedResult getAllComments(String videoId, Integer page, Integer pageSize);
}
