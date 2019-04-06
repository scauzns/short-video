package com.imooc.mapper;

import com.imooc.pojo.Users;
import com.imooc.pojo.UsersExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UsersMapper {
    long countByExample(UsersExample example);

    int deleteByExample(UsersExample example);

    int deleteByPrimaryKey(String id);

    int insert(Users record);

    int insertSelective(Users record);

    List<Users> selectByExample(UsersExample example);

    Users selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") Users record, @Param("example") UsersExample example);

    int updateByExample(@Param("record") Users record, @Param("example") UsersExample example);

    int updateByPrimaryKeySelective(Users record);

    int updateByPrimaryKey(Users record);

    //用户受喜欢数累加
    void addReceiveLikeCount(String userId);

    //用户受喜欢数累减
    void reduceReceiveLikeCount(String userId);

    //增加粉丝数量
    void addFansCount(String userId);

    //减少粉丝数
    void reduceFansCount(String userId);

    //增加粉丝数量
    void addFollersCount(String userId);

    //减少粉丝数
    void reduceFollersCount(String userId);
}