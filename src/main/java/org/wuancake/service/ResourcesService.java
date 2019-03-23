package org.wuancake.service;

import org.wuancake.entity.Resources;
import org.wuancake.entity.ResourcesType;

import java.sql.SQLException;
import java.util.List;

/**
 * 资源类service
 */
public interface ResourcesService {
    /**
     * 编辑资源
     * @param resources
     * @return
     */
    boolean updateResources(Resources resources);

    /**
     * 获取资源类型列表
     * @return
     */
    List<ResourcesType> getResourcesType();

    /**
     * 增加资源
     * @param resources
     */
    void addResources(Resources resources) throws SQLException;

    Integer getResourceTypeIdByResourceTypeName(String type);
}
