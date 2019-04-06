package com.imooc.controller;

import com.imooc.service.BgmService;
import com.imooc.utils.IMoocJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @功能描述：TODO
 * @创建日期: 2019/3/30 11:41
 */
@RestController
@RequestMapping("/bgm")
@Api(value = "背景音乐业务的接口",tags = {"背景音乐业务的controller"})
public class BgmController{
    @Autowired
    private BgmService bgmService;

    @PostMapping("/list")
    @ApiOperation(value = "获取背景音乐列表",notes = "获取背景音乐列表的接口")
    public IMoocJSONResult list(){
        return IMoocJSONResult.ok(bgmService.queryBgmList());
    }
}
