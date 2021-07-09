package cn.bugio.wiki.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ContentReq {
    /**
     * 文档id
     */
    private Long id;

    /**
     * 内容
     */
    private String content;
}