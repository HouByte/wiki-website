package cn.bugio.wiki.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CategoryReq {
    /**
     * id
     */
    private Long id;

    /**
     * 父id
     */
    @NotEmpty(message = "【父分类】不能为空")
    private Long parent;

    /**
     * 名称
     */
    @NotEmpty(message = "【分类】不能为空")
    private String name;

    /**
     *  顺序
     */
    private Integer sort;
}