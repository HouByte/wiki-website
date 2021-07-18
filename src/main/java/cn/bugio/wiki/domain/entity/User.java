package cn.bugio.wiki.domain.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
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
     * 邮箱
     */
    private String email;

    /**
     * 是否可用 0 不可用 1 可用 数据库默认0
     */
    private Boolean enable;
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