package cn.bugio.wiki.controller;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.domain.dto.EbookResp;
import cn.bugio.wiki.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/06/27
 */
@RestController
@RequestMapping("ebook")
public class EbookController {

    private final EbookService ebookService;

    @Autowired
    public EbookController(EbookService ebookService) {
        this.ebookService = ebookService;
    }

    @PostMapping("list")
    public CommonResult<List<EbookResp>> list(String keyword){
        return ebookService.list(keyword);
    }
}
