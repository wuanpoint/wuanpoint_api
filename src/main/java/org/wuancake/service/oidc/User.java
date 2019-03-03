package org.wuancake.service.oidc;

import lombok.Data;

import java.text.ParseException;
import java.util.Date;

/**
 * 暂存oidc返回的用户信息的用户类
 */
@Data
public class User {
    private Long id;
    private String mail;
    private String name;
    private String avatar_url;
    private String sex;
    private Date birthday;//2010-11-11格式

    public User(Long id, String mail, String name, String avatar_url, String sex, Date birthday) throws ParseException {
        this.id = id;
        this.mail = mail;
        this.name = name;
        this.avatar_url = avatar_url;
        this.sex = sex;
    }

    public User() {
    }
}
