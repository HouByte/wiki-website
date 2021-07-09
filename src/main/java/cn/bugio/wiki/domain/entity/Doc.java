package cn.bugio.wiki.domain.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "doc")
public class Doc {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     *  电子书id
     */
    @Column(name = "ebook_id")
    private Long ebookId;

    /**
     *  父id 
     */
    private Long parent;

    /**
     *  名称
     */
    private String name;

    /**
     *  顺序
     */
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