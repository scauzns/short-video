package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.utils.MD5Utils;
import com.imooc.vo.UsersVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * @功能描述：TODO
 * @创建日期: 2019/3/29 10:26
 */
@RestController
@Api(value = "用户注册登录的接口",tags = {"注册和登录的controller"})
public class RegistLoginController extends BasicController{
    @Autowired
    private UserService usersService;

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return "test hello world";
    }

    //notes：备注信息
    @ApiOperation(value = "用户注册",notes = "用户注册的接口")
    @PostMapping("/regist")
    public IMoocJSONResult regist(@RequestBody Users users) throws Exception {
        //1. 判断用户名和密码必须不为空
        if(StringUtils.isBlank(users.getUsername()) || StringUtils.isBlank(users.getPassword())){
            return IMoocJSONResult.errorMsg("用户名和密码不能为空");
        }
        //2. 判断用户名是否存在
        boolean usernameIsExist = usersService.queryUsernameIsExist(users.getUsername());
        if(!usernameIsExist){
            users.setNickname(users.getUsername());
            users.setPassword(MD5Utils.getMD5Str(users.getPassword()));
            users.setFansCounts(0);
            users.setFollowCounts(0);
            users.setReceiveLikeCounts(0);
            usersService.saveUser(users);
        }else{
            return IMoocJSONResult.errorMsg("用户名已存在，请换一个再试");
        }
        users.setPassword("");//为了安全考虑

        UsersVO usersVO = setUserRedisSessionToken(users);

        //3. 保存用户，注册信息
        return IMoocJSONResult.ok(usersVO);
    }

    public UsersVO setUserRedisSessionToken(Users userModel){
        String uniqueToken = UUID.randomUUID().toString();
        redis.set(USER_REDIS_SESSION+":"+userModel.getId(), uniqueToken, 1000 * 60 * 30);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userModel,usersVO);
        usersVO.setUserToken(uniqueToken);
        return usersVO;
    }

    @ApiOperation(value = "用户登录",notes = "用户登录的接口")
    @PostMapping("/login")
    public IMoocJSONResult login(@RequestBody Users users) throws Exception {
        String username = users.getUsername();
        String password = users.getPassword();
        //1. 判断用户名和密码必须不为空
        if(StringUtils.isBlank(username) || StringUtils.isBlank(password)){
            return IMoocJSONResult.errorMsg("用户名和密码不能为空");
        }
        //2. 判断用户名是否存在
        Users userResult = usersService.queryUserForLogin(username,MD5Utils.getMD5Str(password));
        if(userResult != null){
            userResult.setPassword("");
            UsersVO usersVO = setUserRedisSessionToken(userResult);
            return IMoocJSONResult.ok(usersVO);
        }else{
            return IMoocJSONResult.errorMsg("用户名或密码不正确，请重试");
        }
    }

    @ApiOperation(value = "用户注销",notes = "用户注销的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
    @PostMapping("/logout")
    public IMoocJSONResult logout(String userId) throws Exception {
        redis.del(USER_REDIS_SESSION + ":" + userId);

        return IMoocJSONResult.ok();

    }
}
