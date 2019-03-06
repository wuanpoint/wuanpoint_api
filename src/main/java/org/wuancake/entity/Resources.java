package org.wuancake.entity;

import lombok.Data;

import java.util.Date;

/**
 * 资源类
 */
@Data
public class Resources {
    private Integer moviesId;//影片id
    private Integer resourceId;//资源id
    private Integer resourceType;//资源种类id
    private String type;//资源种类的名称，不存入数据库
    private String title;//资源标题
    private String instruction;//资源描述
    private Sharer sharer;//分享者
    private String url;//下载链接
    private String password;//密码
    private Date updatedAt;//修改时间
    private Date createdAt;//创建时间

    public Resources(Integer resource_id, Integer resource_type, String title, String instruction, String url, String password,Integer sharerId, String sharerName) {
        this.resourceId = resource_id;
        this.resourceType = resource_type;
        this.title = title;
        this.instruction = instruction;
        this.sharer = new Sharer(sharerId, sharerName);
        this.url = url;
        this.password = password;
    }

    public Resources() {
    }

    /**
     * 分享者信息
     */
    @Data
    class Sharer {
        public Integer id;
        public String name;

        public Sharer(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public void setSharer(Integer sharerId, String sharerName) {
        this.sharer = new Sharer(sharerId, sharerName);
    }

    public Integer getSharerId(){
        return this.sharer.id;
    }

    public String getSharerName(){
        return this.sharer.name;
    }
}
