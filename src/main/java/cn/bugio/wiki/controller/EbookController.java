package cn.bugio.wiki.controller;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.domain.dto.EbookReq;
import cn.bugio.wiki.domain.dto.EbookResp;
import cn.bugio.wiki.domain.dto.EbookSearchReq;
import cn.bugio.wiki.service.EbookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public CommonResult<List<EbookResp>> list(@RequestBody(required = false) EbookSearchReq searchReq){
        String keyword = null;
        if (searchReq !=null){
            keyword = searchReq.getKeyword();
        }
        System.out.println(keyword);
        return ebookService.list(keyword);
    }

    @PostMapping("save")
    public CommonResult save(@Valid @RequestBody EbookReq ebookReq){
        return ebookService.save(ebookReq);
    }

    @DeleteMapping("delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id){
        return ebookService.delete(id);
    }
}
