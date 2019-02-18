package org.wuancake.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by ericheel on 2019/2/18.
 * 影片详情
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@XmlRootElement
public class MoviesDetails extends MoviesBase {

    /**
     * 影片原名
     */
    @XmlElement
    private String originalTitle;

    /**
     * 制片国家/地区
     */
    @XmlElement
    private String countries;

    /**
     * 年代
     */
    @XmlElement
    private String year;

    /**
     * 影片别名
     */
    @XmlElement
    private String aka;

    /**
     * 豆瓣链接
     */
    @XmlElement
    private String urlDouban;


}
