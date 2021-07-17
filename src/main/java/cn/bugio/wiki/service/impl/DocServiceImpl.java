package cn.bugio.wiki.service.impl;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.dao.ContentMapper;
import cn.bugio.wiki.dao.DocMapper;
import cn.bugio.wiki.domain.dto.DocReq;
import cn.bugio.wiki.domain.dto.DocResp;
import cn.bugio.wiki.domain.entity.Content;
import cn.bugio.wiki.domain.entity.Doc;
import cn.bugio.wiki.exception.BusinessException;
import cn.bugio.wiki.exception.BusinessExceptionCode;
import cn.bugio.wiki.service.DocService;
import cn.bugio.wiki.util.CopyUtil;
import cn.bugio.wiki.util.RedisUtil;
import cn.bugio.wiki.util.RequestContext;
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

    private final RedisUtil redisUtil;

    @Autowired
    public DocServiceImpl(DocMapper docMapper, ContentMapper contentMapper, SnowFlake snowFlake, RedisUtil redisUtil) {
        this.docMapper = docMapper;
        this.contentMapper = contentMapper;
        this.snowFlake = snowFlake;
        this.redisUtil = redisUtil;
    }

    /**
     * <h2>查询列表</h2>
     *
     * @param keyword 查询关键字 为空全部查询
     * @return
     */
    @Override
    public CommonResult<List<DocResp>> list(Long ebookId,String keyword) {
        List<Doc> docs = null;
        Example example = new Example(Doc.class);
        keyword = "%"+keyword+"%";
        example.createCriteria().andEqualTo("ebookId",ebookId);
        example.setOrderByClause("sort asc");
        if (StringUtils.isEmpty(keyword)){
            example.createCriteria().andLike("name",keyword);
        }
        docs = docMapper.selectByExample(example);
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
        if (StringUtils.isNotEmpty(content.getContent())){
            //有内容才更新，todo 迭代计划：一个ip同一个小时访问只算一次阅读
            docMapper.increaseViewCount(id);
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

    /**
     * 文档点赞
     *
     * @param id 文档id
     * @return
     */
    @Override
    public CommonResult<String> voteDoc(Long id) {
        String redisKey =String.format("DOC_VOTE_%s_%s",id, RequestContext.getRemoteAddr());
        //ip+id 作为key,失效12小时
        if (redisUtil.validateRepeat(redisKey,3600 * 12)){
            int i = docMapper.increaseVoteCount(id);
            if (i == 0 ){
                return CommonResult.error("点赞成功");
            }
            return CommonResult.success("点赞成功");
        }
        throw new BusinessException(BusinessExceptionCode.VOTE_REPEAT);
    }

}
