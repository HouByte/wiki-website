package cn.bugio.wiki.service.impl;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.dao.CategoryMapper;
import cn.bugio.wiki.domain.dto.CategoryReq;
import cn.bugio.wiki.domain.dto.CategoryResp;
import cn.bugio.wiki.domain.entity.Category;
import cn.bugio.wiki.service.CategoryService;
import cn.bugio.wiki.service.CategoryService;
import cn.bugio.wiki.util.CopyUtil;
import cn.bugio.wiki.util.SnowFlake;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/06/27
 */
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    private final SnowFlake snowFlake;

    @Autowired
    public CategoryServiceImpl(CategoryMapper categoryMapper, SnowFlake snowFlake) {
        this.categoryMapper = categoryMapper;
        this.snowFlake = snowFlake;
    }

    /**
     * <h2>查询列表</h2>
     *
     * @param keyword 查询关键字 为空全部查询
     * @return
     */
    @Override
    public CommonResult<List<CategoryResp>> list(String keyword) {
        List<Category> categorys = null;
        if (StringUtils.isEmpty(keyword)){
            categorys = categoryMapper.selectAll();
        } else {
            Example example = new Example(Category.class);
            keyword = "%"+keyword+"%";
            example.createCriteria().andLike("name",keyword);
            categorys = categoryMapper.selectByExample(example);
        }
        if (categorys == null){
            return CommonResult.success("没有查询到分类");
        }

        List<CategoryResp> categoryResps = CopyUtil.copyList(categorys, CategoryResp.class);
        return CommonResult.success(categoryResps);
    }

    /**
     * 保存电子书
     *
     * @param categoryReq 保存数据
     * @return
     */
    @Override
    public CommonResult save(CategoryReq categoryReq) {
        if (categoryReq == null){
            return CommonResult.error("参数不能为空");
        }
        Category category = CopyUtil.copy(categoryReq, Category.class);
        int op = 0;
        //id为空为新增
        if (category.getId() == null){
            category.setId(snowFlake.nextId());
            op = categoryMapper.insertSelective(category);
        } else{
            op = categoryMapper.updateByPrimaryKeySelective(category);
        }
        if (op == 0){
            return CommonResult.error("保存失败");
        }
        return CommonResult.success("保存成功");
    }

    /**
     * 删除电子书
     *
     * @param id 书籍id
     * @return
     */
    @Override
    public CommonResult delete(Long id) {
        int delete = categoryMapper.deleteByPrimaryKey(id);
        if (delete == 0){
            return CommonResult.error("删除失败");
        }
        return CommonResult.success("删除成功");
    }

}
