package cn.bugio.wiki.domain.dto;

import lombok.Data;
import lombok.Getter;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/07/22
 */
@Data
public class UserUpdateEnableReq {
    //开启或者关闭
    private Boolean enable;
}
