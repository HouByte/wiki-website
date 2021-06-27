package cn.bugio.wiki.service.impl;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.dao.EbookMapper;
import cn.bugio.wiki.domain.dto.EbookResp;
import cn.bugio.wiki.domain.entity.Ebook;
import cn.bugio.wiki.service.EbookService;
import cn.bugio.wiki.util.CopyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/06/27
 */
@Service("ebookService")
public class EbookServiceImpl implements EbookService {

    private final EbookMapper ebookMapper;

    @Autowired
    public EbookServiceImpl(EbookMapper ebookMapper) {
        this.ebookMapper = ebookMapper;
    }

    /**
     * <h2>查询列表</h2>
     *
     * @param keyword 查询关键字 为空全部查询
     * @return
     */
    @Override
    public CommonResult<List<EbookResp>> list(String keyword) {
        List<Ebook> ebooks = null;
        if (StringUtils.isEmpty(keyword)){
            ebooks = ebookMapper.selectAll();
        } else {
            Example example = new Example(Ebook.class);
            keyword = "%"+keyword+"%";
            example.createCriteria().andEqualTo("name",keyword).orEqualTo("desc",keyword);
            ebooks = ebookMapper.selectByExample(example);
        }
        if (ebooks == null){
            return CommonResult.success("没有书籍");
        }
        List<EbookResp> ebookResps = new LinkedList<>();
        for (Ebook ebook : ebooks) {
            EbookResp ebookResp = CopyUtil.copy(ebook, EbookResp.class);
            String categoryIds = ebook.getCategoryIds();
            if (StringUtils.isNotEmpty(categoryIds)){
                String[] split = categoryIds.split(",");
                List<String> categoryIdList = Arrays.asList(split);
                ebookResp.setCategoryIdList(categoryIdList);
                ebookResps.add(ebookResp);
            }
        }
         ebookResps = CopyUtil.copyList(ebooks, EbookResp.class);
        return CommonResult.success(ebookResps);
    }

}
