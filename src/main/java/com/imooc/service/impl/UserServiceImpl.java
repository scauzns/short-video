package com.imooc.service.impl;

import com.imooc.mapper.UsersFansMapper;
import com.imooc.mapper.UsersLikeVideosMapper;
import com.imooc.mapper.UsersMapper;
import com.imooc.mapper.UsersReportMapper;
import com.imooc.pojo.*;
import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.org.n3r.idworker.Sid;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @功能描述：TODO
 * @创建日期: 2019/3/29 10:53
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired
    private UsersFansMapper usersFansMapper;

    @Autowired
    private UsersReportMapper usersReportMapper;
    @Autowired
    private Sid sid;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryUsernameIsExist(String username) {
        UsersExample usersExample = new UsersExample();
        usersExample.createCriteria().andUsernameEqualTo(username);
        List<Users> users = usersMapper.selectByExample(usersExample);
        return (users==null || users.size()==0)  ? false : true;
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(Users user) {
        String userId = sid.nextShort();
        user.setId(userId);
        usersMapper.insert(user);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserForLogin(String username, String password) {
        UsersExample usersExample = new UsersExample();
        usersExample.createCriteria().andUsernameEqualTo(username).andPasswordEqualTo(password);
        List<Users> users = usersMapper.selectByExample(usersExample);
        if(users.size()==0)
            return null;
        return users.get(0);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateUserInfo(Users user) {
        UsersExample usersExample = new UsersExample();
        usersExample.createCriteria().andIdEqualTo(user.getId());
        usersMapper.updateByExampleSelective(user,usersExample);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserInfo(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean isUserLikeVideo(String userId, String videoId) {
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)){
            return false;
        }
        UsersLikeVideosExample example = new UsersLikeVideosExample();
        example.createCriteria().andUserIdEqualTo(userId).andVideoIdEqualTo(videoId);
        List<UsersLikeVideos> list = usersLikeVideosMapper.selectByExample(example);
        if(list!=null && list.size()>0){
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUserFanRelation(String userId, String fanId) {
        UsersFans userFan = new UsersFans();
        String relId = sid.nextShort();
        userFan.setId(relId);
        userFan.setUserId(userId);
        userFan.setFanId(fanId);
        usersFansMapper.insert(userFan);
        usersMapper.addFansCount(userId);
        usersMapper.addFollersCount(fanId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserFanRelation(String userId, String fanId) {
        UsersFansExample example = new UsersFansExample();
        example.createCriteria().andFanIdEqualTo(fanId).andUserIdEqualTo(userId);
        usersFansMapper.deleteByExample(example);
        usersMapper.reduceFansCount(userId);
        usersMapper.reduceFollersCount(fanId);
    }

    @Override
    public boolean queryIfFollow(String userId, String fanId) {
        UsersFansExample example = new UsersFansExample();
        example.createCriteria().andFanIdEqualTo(fanId).andUserIdEqualTo(userId);
        List<UsersFans> list = usersFansMapper.selectByExample(example);
        if(list != null && !list.isEmpty() && list.size()>0){
            return true;
        }
        return false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void reportUser(UsersReport userReport) {

        String urId = sid.nextShort();
        userReport.setId(urId);
        userReport.setCreateDate(new Date());

        usersReportMapper.insert(userReport);
    }
}
