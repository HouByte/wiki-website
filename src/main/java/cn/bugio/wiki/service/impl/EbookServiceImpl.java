package cn.bugio.wiki.service.impl;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.dao.EbookMapper;
import cn.bugio.wiki.domain.dto.EbookReq;
import cn.bugio.wiki.domain.dto.EbookResp;
import cn.bugio.wiki.domain.dto.EbookSearchReq;
import cn.bugio.wiki.domain.entity.Ebook;
import cn.bugio.wiki.service.EbookService;
import cn.bugio.wiki.util.CopyUtil;
import cn.bugio.wiki.util.SnowFlake;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

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
     * @param searchReq 查询关键字 为空全部查询
     * @return
     */
    @Override
    public CommonResult<List<EbookResp>> list(EbookSearchReq searchReq) {
        List<Ebook> ebooks = null;
        if (searchReq == null){
            ebooks = ebookMapper.selectAll();
        } else {
            Example example = new Example(Ebook.class);
            if (StringUtils.isNotEmpty(searchReq.getKeyword())){
                String keyword = searchReq.getKeyword();
                keyword = "%"+keyword+"%";
                example.createCriteria().andLike("name",keyword).orLike("desc",keyword);
            }
            if (searchReq.getCategoryId() != null && searchReq.getCategoryId() != 0){
                Long categoryId = searchReq.getCategoryId();
                example.createCriteria().andLike("categoryIds","%"+categoryId+"%");
            }
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
//                String[] split = categoryIds.split(",");
//                List<String> categoryIdStrList = Arrays.asList(split);
                List<Long> categoryIdList = JSONObject.parseArray(categoryIds,Long.class);
                ebookResp.setCategoryIdList(categoryIdList);

            }
            ebookResps.add(ebookResp);
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
        String categoryids = JSONObject.toJSONString(ebookReq.getCategoryIdList());//ebookReq.getCategoryIdList().toString().replace("[","").replace("]","");
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

    @Override
    public int updateEbookStatistics() {
        return ebookMapper.updateEbookStatisticsData();
    }

}
