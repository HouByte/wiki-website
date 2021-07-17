package cn.bugio.wiki.domain.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/07/17
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Statistic30Resp {
    /**
     * 快照日期
     */
    @JsonFormat( pattern="MM-dd",timezone="GMT+8")
    private Date date;

    /**
     * 阅读增长
     */
    private Integer viewIncrease;

    /**
     * 点赞增长
     */
    private Integer voteIncrease;
}
