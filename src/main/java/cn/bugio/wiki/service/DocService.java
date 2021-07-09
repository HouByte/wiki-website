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
     * <h2>查询列表</h2>
     * @param keyword 查询关键字 为空全部查询
     * @return
     */
    CommonResult<List<DocResp>> list(String keyword);

    /**
     * 保存电子书
     * @param docReq 保存数据
     * @return
     */
    CommonResult save(DocReq docReq);

    /**
     * 删除电子书
     * @param id 书籍id
     * @return
     */
    CommonResult deletes(List<Long> id);
}
