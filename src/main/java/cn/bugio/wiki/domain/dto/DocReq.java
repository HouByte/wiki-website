package cn.bugio.wiki.domain.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DocReq {
    /**
     * id
     */
    private Long id;

    /**
     *  电子书id
     */
    @NotEmpty(message = "【电子书】对应不能为空")
    private Long ebookId;

    /**
     *  父id 
     */
    @NotEmpty(message = "【父文档】不能为空")
    private Long parent;

    /**
     *  名称
     */
    @NotEmpty(message = "【名称】不能为空")
    private String name;

    /**
     *  顺序
     */
    @NotEmpty(message = "【顺序】不能为空")
    private Integer sort;

    /**
     * 内容
     */
    @NotEmpty(message = "【内容】不能为空")
    private String content;

    /**
     * 阅读数
     */
    private Integer viewCount;

    /**
     *  点赞数
     */
    private Integer voteCount;
}