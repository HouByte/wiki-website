package cn.bugio.wiki.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/07/09
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ContentDeleteDto {
    private List<Long> ids;
}
