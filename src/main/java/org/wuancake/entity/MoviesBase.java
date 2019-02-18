package org.wuancake.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * Created by ericheel on 2019/2/18.
 * 影片基础信息表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@ToString
public class MoviesBase {

    /**
     * 影片id
     */
    @XmlElement
    private int id;

    /**
     * 影片（首页）分类
     */
    @XmlElement
    private int type;

    /**
     * 影片标题
     */
    @XmlElement
    private String title;

    /**
     * 摘要
     */
    @XmlElement
    private String digest;

    /**
     * 资源添加时间
     */
    @XmlElement
    private Timestamp createdAt;
}
