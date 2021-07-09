package cn.bugio.wiki.service.impl;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.dao.ContentMapper;
import cn.bugio.wiki.dao.DocMapper;
import cn.bugio.wiki.domain.dto.DocReq;
import cn.bugio.wiki.domain.dto.DocResp;
import cn.bugio.wiki.domain.entity.Content;
import cn.bugio.wiki.domain.entity.Doc;
import cn.bugio.wiki.service.DocService;
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
@Service("docService")
public class DocServiceImpl implements DocService {

    private final DocMapper docMapper;

    private final ContentMapper contentMapper;

    private final SnowFlake snowFlake;

    @Autowired
    public DocServiceImpl(DocMapper docMapper, ContentMapper contentMapper, SnowFlake snowFlake) {
        this.docMapper = docMapper;
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
    public CommonResult<List<DocResp>> list(String keyword) {
        List<Doc> docs = null;
        if (StringUtils.isEmpty(keyword)){
            docs = docMapper.selectAll();
        } else {
            Example example = new Example(Doc.class);
            keyword = "%"+keyword+"%";
            example.createCriteria().andLike("name",keyword);
            docs = docMapper.selectByExample(example);
        }
        if (docs == null){
            return CommonResult.success("没有查询到分类");
        }

        List<DocResp> docResps = CopyUtil.copyList(docs, DocResp.class);
        return CommonResult.success(docResps);
    }

    /**
     * <h2>按id查询文档</h2>
     *
     * @param id id
     * @return
     */
    @Override
    public CommonResult<String> queryContent(Long id) {
        Content content = contentMapper.selectByPrimaryKey(id);
        if (content == null){
            return CommonResult.success();
        }
        return CommonResult.success("成功",content.getContent());
    }

    /**
     * 保存电子书
     *
     * @param docReq 保存数据
     * @return
     */
    @Override
    public CommonResult save(DocReq docReq) {
        if (docReq == null){
            return CommonResult.error("参数不能为空");
        }
        Doc doc = CopyUtil.copy(docReq, Doc.class);
        Content content = Content.builder().id(docReq.getId()).content(docReq.getContent()).build();
        int op = 0;
        //id为空为新增
        if (doc.getId() == null){
            doc.setId(snowFlake.nextId());
            op = docMapper.insertSelective(doc);
            //如果没有内容就不存
            if (op > 0 && content.getContent() != null){
                content.setId(doc.getId());
                op = contentMapper.insertSelective(content);
            }
        } else{
            op = docMapper.updateByPrimaryKeySelective(doc);
            if (op == 0){
                return CommonResult.error("保存失败");
            }
            op = contentMapper.updateByPrimaryKeySelective(content);
            if (op == 0){
                op = contentMapper.insertSelective(content);
            }
        }
        if (op == 0 ){
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
        Example example = new Example(Doc.class);
        example.createCriteria().andIn("id",ids);
        int delete = docMapper.deleteByExample(example);
        if (delete == 0){
            return CommonResult.error("删除失败");
        }
        Example contentExample = new Example(Content.class);
        contentExample.createCriteria().andIn("id",ids);
        delete = contentMapper.deleteByExample(contentExample);
        if (delete == 0){
            return CommonResult.error("删除内容失败");
        }
        return CommonResult.success("删除成功");
    }

}
