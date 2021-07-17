package cn.bugio.wiki.service;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.domain.dto.DocReq;
import cn.bugio.wiki.domain.dto.DocResp;

import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/06/27
 */
public interface DocService {

    /**
     *  <h2>查询列表</h2>
     * @param ebookId id
     * @param keyword 查询关键字 为空全部查询
     * @return
     */
    CommonResult<List<DocResp>> list(Long ebookId,String keyword);


    /**
     * <h2>按id查询内容</h2>
     * @param id id
     * @return
     */
    CommonResult<String> queryContent(Long id);


    /**
     * 保存电子书
     * @param docReq 保存数据
     * @return
     */
    CommonResult save(DocReq docReq);

    /**
     * 删除文档
     * @param id 文档id
     * @return
     */
    CommonResult deletes(List<Long> id);

    /**
     * 文档点赞
     * @param id 文档id
     * @return
     */
    CommonResult<String> voteDoc(Long id);
}
