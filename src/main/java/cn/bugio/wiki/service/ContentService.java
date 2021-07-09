package cn.bugio.wiki.service;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.domain.dto.ContentReq;
import cn.bugio.wiki.domain.dto.ContentResp;

import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/06/27
 */
public interface ContentService {

    /**
     * <h2>查询列表</h2>
     * @param keyword 查询关键字 为空全部查询
     * @return
     */
    CommonResult<List<ContentResp>> list(String keyword);

    /**
     * 保存电子书
     * @param contentReq 保存数据
     * @return
     */
    CommonResult save(ContentReq contentReq);

    /**
     * 删除电子书
     * @param id 书籍id
     * @return
     */
    CommonResult deletes(List<Long> id);
}
