package org.wuancake.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.wuancake.entity.Helloworld;
import org.wuancake.mapper.HelloworldMapper;
import org.wuancake.service.IHelloworldService;

/**
 * Created by Administrator on 2019/1/24.
 */
@Service
public class HelloworldServiceImpl implements IHelloworldService {

//    @Autowired
//    private HelloworldMapper helloworldMapper;

    @Override
    public Helloworld sayHello() {
//        return helloworldMapper.sayHello();
        return new Helloworld("helloworld");
    }
}
