package com.imooc.service;

import com.imooc.pojo.Users;
import com.imooc.pojo.UsersReport;

/**
 * @功能描述：TODO
 * @创建日期: 2019/3/29 10:53
 */
public interface UserService {
    //判断用户名是否存在
    public boolean queryUsernameIsExist(String username);
    //保存
    public void saveUser(Users user);
    //登录的时候查找用户是否存在
    Users queryUserForLogin(String username, String password);

    //修改用户信息
    void updateUserInfo(Users user);

    //查询用户
    Users queryUserInfo(String userId);

    //查询用户是否点赞视频
    boolean isUserLikeVideo(String userId, String videoId);

    //增加用户和粉丝的关系
    void saveUserFanRelation(String userId, String fanId);

    //删除用户和粉丝的关系
    void deleteUserFanRelation(String userId, String fanId);

    //查询用户是否已关注
    boolean queryIfFollow(String userId, String fanId);

    // 举报用户
    public void reportUser(UsersReport userReport);
}
