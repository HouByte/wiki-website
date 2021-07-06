package cn.bugio.wiki.service;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.domain.dto.EbookReq;
import cn.bugio.wiki.domain.dto.EbookResp;
import cn.bugio.wiki.domain.entity.Ebook;

import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/06/27
 */
public interface EbookService {

    /**
     * <h2>查询列表</h2>
     * @param keyword 查询关键字 为空全部查询
     * @return
     */
    CommonResult<List<EbookResp>> list(String keyword);

    /**
     * 保存电子书
     * @param ebookReq 保存数据
     * @return
     */
    CommonResult save(EbookReq ebookReq);

    /**
     * 删除电子书
     * @param id 书籍id
     * @return
     */
    CommonResult delete(Long id);
}
