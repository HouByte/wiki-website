package cn.bugio.wiki.domain.entity;

import javax.persistence.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "content")
public class Content {
    /**
     * 文档id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 内容
     */
    private String content;
}