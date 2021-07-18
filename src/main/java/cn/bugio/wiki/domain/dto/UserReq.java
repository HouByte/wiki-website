package cn.bugio.wiki.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserReq {
    /**
     *  ID
     */
    private Long id;

    /**
     * 登陆名
     */
    @NotNull(message = "【用户名】不能为空")
    private String loginName;

    /**
     *  昵称
     */
    @NotNull(message = "【昵称】不能为空")
    private String name;

    /**
     *  密码
     */
    @NotNull(message = "【密码】不能为空")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$", message = "【密码】至少包含 数字和英文，长度6-32")
    private String password;

    /**
     * 邮箱
     */
    @NotNull(message = "【邮箱】不能为空")
    @Length(min = 6, max = 50, message = "【邮箱】6~50位")
    @Pattern(regexp = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "【邮箱】非法邮箱")
    private String email;

    /**
     * 头像
     */
    private String avatar;
}