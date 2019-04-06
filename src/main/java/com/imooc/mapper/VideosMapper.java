package com.imooc.mapper;

import com.imooc.pojo.Videos;
import com.imooc.pojo.VideosExample;
import java.util.List;

import com.imooc.vo.VideosVO;
import org.apache.ibatis.annotations.Param;

public interface VideosMapper {
    long countByExample(VideosExample example);

    int deleteByExample(VideosExample example);

    int deleteByPrimaryKey(String id);

    int insert(Videos record);

    int insertSelective(Videos record);

    List<Videos> selectByExample(VideosExample example);

    Videos selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Videos record, @Param("example") VideosExample example);

    int updateByExample(@Param("record") Videos record, @Param("example") VideosExample example);

    int updateByPrimaryKeySelective(Videos record);

    int updateByPrimaryKey(Videos record);

    //根据前端查询的关键字，符合视频描述的内容返回给前端
    List<VideosVO> queryAllVideos(@Param("videoDesc") String videoDesc,@Param("userId") String userId);

    //对视频喜欢的数量进行累加
    void addVideoLikeCount(String videoId);

    //对视频喜欢的数量进行累减
    void reduceVideoLikeCount(String videoId);

    // 查询点赞视频
    List<VideosVO> queryMyLikeVideos(@Param("userId") String userId);

    //查询关注的视频
    public List<VideosVO> queryMyFollowVideos(String userId);
}