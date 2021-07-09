package cn.bugio.wiki.controller;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.domain.dto.DocReq;
import cn.bugio.wiki.domain.dto.DocResp;
import cn.bugio.wiki.domain.dto.DocSearchReq;
import cn.bugio.wiki.service.DocService;
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
@RequestMapping("doc")
public class DocController {

    private final DocService docService;

    @Autowired
    public DocController(DocService docService) {
        this.docService = docService;
    }

    @PostMapping("list")
    public CommonResult<List<DocResp>> list(@RequestBody(required = false) DocSearchReq searchReq){
        String keyword = null;
        if (searchReq !=null){
            keyword = searchReq.getKeyword();
        }
        return docService.list(keyword);
    }

    @PostMapping("save")
    public CommonResult save(@Valid @RequestBody DocReq docReq){
        return docService.save(docReq);
    }

    @DeleteMapping("delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id){
        return docService.delete(id);
    }
}
