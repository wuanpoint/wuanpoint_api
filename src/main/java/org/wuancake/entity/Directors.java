package org.wuancake.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ericheel on 2019/2/18.
 * 导演实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@ToString
public class Directors {

    /**
     * 导演id
     */
    @XmlElement
    private int id;

    /**
     * 导演姓名
     */
    @XmlElement
    private String name;

}
