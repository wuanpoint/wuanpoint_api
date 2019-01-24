package org.wuancake.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.wuancake.entity.Helloworld;

/**
 * Created by Administrator on 2019/1/24.
 */
@Mapper
public interface HelloworldMapper {

    @Select("SELECT message FROM helloworld LIMIT 1")
    Helloworld sayHello();
}
