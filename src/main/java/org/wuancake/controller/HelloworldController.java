package org.wuancake.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.wuancake.entity.Helloworld;
import org.wuancake.service.IHelloworldService;

/**
 * Created by Administrator on 2019/1/24.
 */
@RestController
public class HelloworldController {

    @Autowired
    private IHelloworldService helloworldService;

    @RequestMapping(value = "helloworld")
    public Helloworld sayHello() {
        return helloworldService.sayHello();
    }
}
