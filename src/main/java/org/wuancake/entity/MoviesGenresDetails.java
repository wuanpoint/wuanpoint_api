package org.wuancake.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ericheel on 2019/2/18.
 * 影片类型详情表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@XmlRootElement
public class MoviesGenresDetails {

    /**
     * 类型id
     */
    @XmlElement
    private int genresId;

    /**
     * 类型名称
     */
    @XmlElement
    private String genresName;

}
