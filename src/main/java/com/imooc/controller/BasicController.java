package com.imooc.controller;

import com.imooc.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @功能描述：TODO
 * @创建日期: 2019/3/29 22:24
 */
@RestController
public class BasicController {
    @Autowired
    public RedisOperator redis;
    public static final String USER_REDIS_SESSION = "user-redis-session";

    // 文件保存的命名空间
    public static final String FILE_SPACE = "E:/imooc_videos_dev"; //windows
//    public static final String FILE_SPACE = "/imooc_videos_dev"; //linux

    // ffmpeg所在目录
    public static final String FFMPEG_EXE = "E:\\ffmpeg\\ffmpeg\\bin\\ffmpeg.exe"; //windows
//    public static final String FFMPEG_EXE = "/usr/bin/ffmpeg"; //linux

    //每页分页的记录数
    public static final Integer PAGE_SIZE = 5;
}
