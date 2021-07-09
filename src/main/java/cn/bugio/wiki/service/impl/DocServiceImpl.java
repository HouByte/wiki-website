package cn.bugio.wiki.service.impl;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.dao.DocMapper;
import cn.bugio.wiki.domain.dto.DocReq;
import cn.bugio.wiki.domain.dto.DocResp;
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

    private final SnowFlake snowFlake;

    @Autowired
    public DocServiceImpl(DocMapper docMapper, SnowFlake snowFlake) {
        this.docMapper = docMapper;
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
        int op = 0;
        //id为空为新增
        if (doc.getId() == null){
            doc.setId(snowFlake.nextId());
            op = docMapper.insertSelective(doc);
        } else{
            op = docMapper.updateByPrimaryKeySelective(doc);
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
        int delete = docMapper.deleteByPrimaryKey(id);
        if (delete == 0){
            return CommonResult.error("删除失败");
        }
        return CommonResult.success("删除成功");
    }

}
