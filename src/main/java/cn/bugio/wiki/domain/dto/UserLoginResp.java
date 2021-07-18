package cn.bugio.wiki.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserLoginResp  implements Serializable {
    /**
     *  ID
     */
    private Long id;

    /**
     * 登陆名
     */
    private String loginName;

    /**
     *  昵称
     */
    private String name;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否可用 0 不可用 1 可用 数据库默认0
     */
    private Boolean enable;

    /**
     * 登入口令
     */
    private String token;

}