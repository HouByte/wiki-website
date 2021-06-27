package cn.bugio.wiki.domain.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Table(name = "ebook")
public class Ebook {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 分类列表，逗号隔开
     */
    @Column(name = "category_ids")
    private String categoryIds;

    /**
     * 描述
     */
    private String desc;

    /**
     * 封面
     */
    private String cover;

    /**
     * 文档数
     */
    @Column(name = "doc_count")
    private Integer docCount;

    /**
     * 页面数
     */
    @Column(name = "view_count")
    private Integer viewCount;

    /**
     * 点赞数
     */
    @Column(name = "vote_count")
    private Integer voteCount;

    /**
     * 创建时间
     */
    private Date created;

    /**
     * 更新时间
     */
    private Date updated;
}