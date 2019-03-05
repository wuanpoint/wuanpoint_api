package org.wuancake.response.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * Created by PWF on 2019/3/5.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@ToString
public class ResourceVO {
    /**
     * 影片id
     */
//    @XmlElement
//    private int moviesId;

    /**
     * 资源id
     */
    @XmlElement
    private int id;

    /**
     * 资源种类id
     */
//    @XmlElement
//    private int resourceType;

    /**
     * 资源种类name
     */
    @XmlElement
    private String type;

    /**
     * 资源标题
     */
    @XmlElement
    private String title;

    /**
     * 资源描述
     */
    @XmlElement
    private String instruction;

    /**
     * 分享者id
     */
    @XmlElement
    private int sharer;

    /**
     * 资源链接
     */
    @XmlElement
    private String url;

    /**
     * 资源密码(网盘)
     */
    @XmlElement
    private String password;

    /**
     * 资源发布时间
     */
    @XmlElement
    private Timestamp createdAt;
}
