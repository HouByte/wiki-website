package cn.bugio.wiki.controller;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.domain.dto.*;
import cn.bugio.wiki.service.CategoryService;
import cn.bugio.wiki.service.CategoryService;
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
@RequestMapping("category")
public class CategoryController {

    private final CategoryService categoryService;

    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("list")
    public CommonResult<List<CategoryResp>> list(@RequestBody(required = false) CategorySearchReq searchReq){
        String keyword = null;
        if (searchReq !=null){
            keyword = searchReq.getKeyword();
        }
        System.out.println(keyword);
        return categoryService.list(keyword);
    }

    @PostMapping("save")
    public CommonResult save(@Valid @RequestBody CategoryReq categoryReq){
        return categoryService.save(categoryReq);
    }

    @DeleteMapping("delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id){
        return categoryService.delete(id);
    }
}
