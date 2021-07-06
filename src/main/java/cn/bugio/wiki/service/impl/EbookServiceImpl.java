package cn.bugio.wiki.service.impl;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.dao.EbookMapper;
import cn.bugio.wiki.domain.dto.EbookReq;
import cn.bugio.wiki.domain.dto.EbookResp;
import cn.bugio.wiki.domain.entity.Ebook;
import cn.bugio.wiki.service.EbookService;
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
@Service("ebookService")
public class EbookServiceImpl implements EbookService {

    private final EbookMapper ebookMapper;

    private final SnowFlake snowFlake;

    @Autowired
    public EbookServiceImpl(EbookMapper ebookMapper, SnowFlake snowFlake) {
        this.ebookMapper = ebookMapper;
        this.snowFlake = snowFlake;
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
            example.createCriteria().andLike("name",keyword).orLike("desc",keyword);
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
        return CommonResult.success(ebookResps);
    }

    /**
     * 保存电子书
     *
     * @param ebookReq 保存数据
     * @return
     */
    @Override
    public CommonResult save(EbookReq ebookReq) {
        if (ebookReq == null){
            return CommonResult.error("参数不能为空");
        }
        Ebook ebook = CopyUtil.copy(ebookReq, Ebook.class);
        String categoryids = ebookReq.getCategoryIdList().toString().replace("[","").replace("]","");
        ebook.setCategoryIds(categoryids);
        int op = 0;
        //id为空为新增
        if (ebook.getId() == null){
            ebook.setId(snowFlake.nextId());
            ebook.setCreated(new Date());
            ebook.setUpdated(new Date());
            op = ebookMapper.insertSelective(ebook);
        } else{
            ebook.setUpdated(new Date());
            op = ebookMapper.updateByPrimaryKeySelective(ebook);
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
        int delete = ebookMapper.deleteByPrimaryKey(id);
        if (delete == 0){
            return CommonResult.error("删除失败");
        }
        return CommonResult.success("删除成功");
    }

}
