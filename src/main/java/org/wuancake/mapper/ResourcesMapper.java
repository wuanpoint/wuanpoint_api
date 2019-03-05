package org.wuancake.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.wuancake.entity.Resources;
import org.wuancake.entity.ResourcesType;

import java.util.List;

/**
 * 资源相关的mapper接口
 */
@Mapper
public interface ResourcesMapper {
    /**
     * 获取全部资源分类列表
     * @return
     */
    @Select("SELECT type_id typeId ,type_name typeName FROM resources_type_details")
    List<ResourcesType> getResourcesType();

    /**
     * 获取分享者id
     * @param id
     * @return
     */
    @Select("SELECT sharer FROM resources WHERE `resource_id` = #{id}")
    Integer getSharerId(@Param("id") Integer id);

    /**
     * 编辑资源
     * @param resources
     * @return
     */
    @Update("UPDATE resources SET `resource_type` = #{resources.resourceType}," +
            "`title` = #{resources.title}," +
            "`url` = #{resources.url}," +
            "`password` = #{resources.password}," +
            "`instruction` = #{resources.instruction}")
    int update(@Param("resources") Resources resources);
}
