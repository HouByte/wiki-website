package cn.bugio.wiki.service;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.domain.dto.UserReq;
import cn.bugio.wiki.domain.dto.UserResp;
import cn.bugio.wiki.domain.dto.UserSearchReq;

import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/06/27
 */
public interface UserService {

    /**
     * <h2>查询列表</h2>
     * @param searchReq 查询关键字 为空全部查询
     * @return
     */
    CommonResult<List<UserResp>> list(UserSearchReq searchReq);

    /**
     * 保存电子书
     * @param userReq 保存数据
     * @return
     */
    CommonResult save(UserReq userReq);

    /**
     * 删除电子书
     * @param id 书籍id
     * @return
     */
    CommonResult delete(Long id);

}
