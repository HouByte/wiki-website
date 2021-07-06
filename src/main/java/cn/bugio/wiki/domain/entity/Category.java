package cn.bugio.wiki.domain.entity;

import javax.persistence.*;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "category")
public class Category {
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    private Long id;

    /**
     * 父id
     */
    private Long parent;

    /**
     * 名称
     */
    @Column(name = "`name`")
    private String name;

    /**
     *  顺序
     */
    private Integer sort;
}