package com.imooc.service;

import com.imooc.pojo.Bgm;

import java.util.List;

/**
 * @功能描述：TODO
 * @创建日期: 2019/3/30 11:30
 */
public interface BgmService {
    /**
     * 查询背景音乐列表
     * @return
     */
    List<Bgm> queryBgmList();

    /**
     * 查询某个Bgm
     */
    Bgm queryBgmById(String bgmId);
}
