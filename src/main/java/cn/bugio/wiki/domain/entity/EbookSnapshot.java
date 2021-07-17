package cn.bugio.wiki.domain.entity;

import java.util.Date;
import javax.persistence.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "ebook_snapshot")
public class EbookSnapshot {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 电子书id
     */
    @Column(name = "ebook_id")
    private Long ebookId;

    /**
     * 快照日期
     */
    private Date date;

    /**
     * 阅读数
     */
    @Column(name = "view_count")
    private Integer viewCount;

    /**
     * 点赞数
     */
    @Column(name = "vote_count")
    private Integer voteCount;

    /**
     * 阅读增长
     */
    @Column(name = "view_increase")
    private Integer viewIncrease;

    /**
     * 点赞增长
     */
    @Column(name = "vote_increase")
    private Integer voteIncrease;
}