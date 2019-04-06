package com.imooc.controller;

import com.imooc.pojo.Users;
import com.imooc.pojo.UsersReport;
import com.imooc.service.UserService;
import com.imooc.utils.IMoocJSONResult;
import com.imooc.vo.PublisherVideo;
import com.imooc.vo.UsersVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * @功能描述：TODO
 * @创建日期: 2019/3/29 23:59
 */

@RestController
@Api(value = "用户相关业务的接口",tags = {"用户相关业务的controller"})
@RequestMapping("/user")
public class UserController extends BasicController{
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户上传头像",notes = "用户上传头像的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
    @PostMapping("/uploadFace")
    public IMoocJSONResult uploadFace(String userId, @RequestParam("file") MultipartFile[] files) throws Exception {
        if(StringUtils.isBlank(userId)){
            return IMoocJSONResult.errorMsg("用户Id不能为空");
        }

        //文件保存的命名空间
        String fileSpace = FILE_SPACE;
        //保存到数据库的相对路径
        String uploadPathDB = "/" + userId +"/face";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if(files !=null && files.length>0){
                String fileName = files[0].getOriginalFilename();
                if(StringUtils.isNoneBlank(fileName)){
                    //文件上传的最终保存路径
                    String finalFacePath = fileSpace + uploadPathDB + "/" +fileName;
                    //设置数据库保存的路径
                    uploadPathDB += ("/" + fileName);
                    File outFile = new File(finalFacePath);
                    if(outFile.getParentFile() !=null || !outFile.getParentFile().isDirectory()){
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream,fileOutputStream);
                }
            }else{
                IMoocJSONResult.errorMsg("上传出错");
            }
        } catch (IOException e) {
            e.printStackTrace();
            IMoocJSONResult.errorMsg("上传出错");
        }finally {
            if(fileOutputStream!=null){
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }
        Users user = new Users();
        user.setId(userId);
        user.setFaceImage(uploadPathDB);
        userService.updateUserInfo(user);
        return IMoocJSONResult.ok(uploadPathDB);

    }

    @PostMapping("/query")
    @ApiOperation(value = "查询用户信息",notes = "查询用户信息的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true, dataType = "String", paramType = "query")
    public IMoocJSONResult query(String userId, String fanId) {
        if(StringUtils.isBlank(userId)){
            return IMoocJSONResult.errorMsg("用户Id不能为空");
        }
        Users userInfo = userService.queryUserInfo(userId);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userInfo,usersVO);
        usersVO.setFollow(userService.queryIfFollow(userId, fanId));
        return IMoocJSONResult.ok(usersVO);
    }

    @PostMapping("/queryPublisher")
    public IMoocJSONResult queryPublisher(String loginUserId, String videoId, String publisherUserId) {
        if(StringUtils.isBlank(publisherUserId)){
            return IMoocJSONResult.errorMsg("不能为空");
        }
        //1. 查询视频发布者的信息
        Users userInfo = userService.queryUserInfo(publisherUserId);
        UsersVO publisher = new UsersVO();
        BeanUtils.copyProperties(userInfo,publisher);

        //2. 查询当前登陆者和视频的点赞关系
        boolean userLikeVideo = userService.isUserLikeVideo(loginUserId, videoId);
        PublisherVideo bean = new PublisherVideo();
        bean.setPublisher(publisher);
        bean.setUserLikeVideo(userLikeVideo);
        return IMoocJSONResult.ok(bean);
    }

    @PostMapping("/beyourfans")
    public IMoocJSONResult beyourfans(String userId, String fanId) {
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)){
            return IMoocJSONResult.errorMsg("");
        }
        userService.saveUserFanRelation(userId, fanId);
        return IMoocJSONResult.ok("关注成功");
    }

    @PostMapping("/dontbeyourfans")
    public IMoocJSONResult dontbeyourfans(String userId, String fanId) {
        if(StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)){
            return IMoocJSONResult.errorMsg("");
        }
        userService.deleteUserFanRelation(userId, fanId);
        return IMoocJSONResult.ok("取消关注成功");
    }

    @PostMapping("/reportUser")
    public IMoocJSONResult reportUser(@RequestBody UsersReport usersReport) throws Exception {

        // 保存举报信息
        userService.reportUser(usersReport);

        return IMoocJSONResult.errorMsg("举报成功...有你平台变得更美好...");
    }
}
