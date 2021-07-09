package cn.bugio.wiki.service.impl;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.dao.ContentMapper;
import cn.bugio.wiki.domain.dto.ContentReq;
import cn.bugio.wiki.domain.dto.ContentResp;
import cn.bugio.wiki.domain.entity.Content;
import cn.bugio.wiki.service.ContentService;
import cn.bugio.wiki.util.CopyUtil;
import cn.bugio.wiki.util.SnowFlake;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/06/27
 */
@Service("contentService")
public class ContentServiceImpl implements ContentService {

    private final ContentMapper contentMapper;

    private final SnowFlake snowFlake;

    @Autowired
    public ContentServiceImpl(ContentMapper contentMapper, SnowFlake snowFlake) {
        this.contentMapper = contentMapper;
        this.snowFlake = snowFlake;
    }

    /**
     * <h2>查询列表</h2>
     *
     * @param keyword 查询关键字 为空全部查询
     * @return
     */
    @Override
    public CommonResult<List<ContentResp>> list(String keyword) {
        List<Content> contents = null;
        if (StringUtils.isEmpty(keyword)){
            contents = contentMapper.selectAll();
        } else {
            Example example = new Example(Content.class);
            keyword = "%"+keyword+"%";
            example.createCriteria().andLike("name",keyword);
            contents = contentMapper.selectByExample(example);
        }
        if (contents == null){
            return CommonResult.success("没有查询到分类");
        }

        List<ContentResp> contentResps = CopyUtil.copyList(contents, ContentResp.class);
        return CommonResult.success(contentResps);
    }

    /**
     * 保存电子书
     *
     * @param contentReq 保存数据
     * @return
     */
    @Override
    public CommonResult save(ContentReq contentReq) {
        if (contentReq == null){
            return CommonResult.error("参数不能为空");
        }
        Content content = CopyUtil.copy(contentReq, Content.class);
        int op = 0;
        //id为空为新增
        if (content.getId() == null){
            content.setId(snowFlake.nextId());
            op = contentMapper.insertSelective(content);
        } else{
            op = contentMapper.updateByPrimaryKeySelective(content);
        }
        if (op == 0){
            return CommonResult.error("保存失败");
        }
        return CommonResult.success("保存成功");
    }

    /**
     * 删除电子书
     *
     * @param ids 书籍id
     * @return
     */
    @Override
    public CommonResult deletes(List<Long> ids) {
        Example example = new Example(Content.class);
        example.createCriteria().andIn("id",ids);
        int delete = contentMapper.deleteByExample(example);
        if (delete == 0){
            return CommonResult.error("删除失败");
        }
        return CommonResult.success("删除成功");
    }

}
