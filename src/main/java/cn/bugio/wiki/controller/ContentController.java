package cn.bugio.wiki.controller;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.domain.dto.ContentDeleteDto;
import cn.bugio.wiki.domain.dto.ContentReq;
import cn.bugio.wiki.domain.dto.ContentResp;
import cn.bugio.wiki.domain.dto.ContentSearchReq;
import cn.bugio.wiki.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("content")
public class ContentController {

    private final ContentService contentService;

    @Autowired
    public ContentController(ContentService contentService) {
        this.contentService = contentService;
    }

    @PostMapping("list")
    public CommonResult<List<ContentResp>> list(@RequestBody(required = false) ContentSearchReq searchReq){
        String keyword = null;
        if (searchReq !=null){
            keyword = searchReq.getKeyword();
        }
        return contentService.list(keyword);
    }

    @PostMapping("save")
    public CommonResult save(@Valid @RequestBody ContentReq contentReq){
        return contentService.save(contentReq);
    }

    @PostMapping("deletes")
    public CommonResult deletes(@RequestBody ContentDeleteDto contentDelete){
        return contentService.deletes(contentDelete.getIds());
    }
}
