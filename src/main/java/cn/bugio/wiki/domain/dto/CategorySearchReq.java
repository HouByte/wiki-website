package cn.bugio.wiki.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/07/06
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategorySearchReq {

    private String keyword;
}
