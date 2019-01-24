package org.wuancake.service;

import org.springframework.transaction.annotation.Transactional;
import org.wuancake.entity.Helloworld;

/**
 * Created by Administrator on 2019/1/24.
 */
//@Transactional
public interface IHelloworldService {

    Helloworld sayHello();
}
