package org.wuancake.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ericheel on 2019/2/18.
 *
 * 影片分类详情
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@XmlRootElement
public class MoviesTypeDetails {

    /**
     * 分类id
     */
    @XmlElement
    private int typeId;

    /**
     * 分类名称
     */
    @XmlElement
    private String typeName;
}
