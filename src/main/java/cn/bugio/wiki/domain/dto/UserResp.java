package cn.bugio.wiki.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResp {
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
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;
}