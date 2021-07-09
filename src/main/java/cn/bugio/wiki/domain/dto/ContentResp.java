package cn.bugio.wiki.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ContentResp {
    /**
     * 文档id
     */
    private Long id;

    /**
     * 内容
     */
    private String content;
}