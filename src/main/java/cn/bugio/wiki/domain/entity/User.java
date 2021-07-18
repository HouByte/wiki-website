package cn.bugio.wiki.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "user")
public class User {
    /**
     *  ID
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 登陆名
     */
    @Column(name = "login_name")
    private String loginName;

    /**
     *  昵称
     */
    private String name;

    /**
     *  密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;
}