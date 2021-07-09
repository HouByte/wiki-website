package cn.bugio.wiki.domain.dto;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
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
    @NotNull(message = "电子书对应不能为空")
    private Long ebookId;

    /**
     *  父id 
     */
    @NotNull(message = "父文档不能为空")
    private Long parent;

    /**
     *  名称
     */
    @NotNull(message = "名称不能为空")
    private String name;

    /**
     *  顺序
     */
    @NotNull(message = "顺序不能为空")
    private Integer sort;

    /**
     * 阅读数
     */
    @Column(name = "view_count")
    private Integer viewCount;

    /**
     *  点赞数
     */
    @Column(name = "vote_count")
    private Integer voteCount;
}