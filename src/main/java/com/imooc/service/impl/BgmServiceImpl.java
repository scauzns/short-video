package com.imooc.service.impl;

import com.imooc.mapper.BgmMapper;
import com.imooc.pojo.Bgm;
import com.imooc.pojo.BgmExample;
import com.imooc.service.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @功能描述：TODO
 * @创建日期: 2019/3/30 11:30
 */
@Service
public class BgmServiceImpl implements BgmService {
    @Autowired
    private BgmMapper bgmMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Bgm> queryBgmList(){

        return bgmMapper.selectByExample(null);
    }

    @Override
    public Bgm queryBgmById(String bgmId) {
        return bgmMapper.selectByPrimaryKey(bgmId);
    }
}
