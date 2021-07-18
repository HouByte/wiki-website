package cn.bugio.wiki.service;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.domain.dto.*;
import cn.bugio.wiki.domain.entity.User;

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
     * 根据登入名查询用户
     * @param loginName 登入名
     * @return
     */
    public User selectUserByLoginName(String loginName);

    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return
     */
    public User selectUserByEmail(String email);

    /**
     * <h2>查询列表</h2>
     * @param searchReq 查询关键字 为空全部查询
     * @return
     */
    CommonResult<List<UserResp>> list(UserSearchReq searchReq);

    /**
     * 保存电子书
     * @param userSaveReq 保存数据
     * @return
     */
    CommonResult save(UserSaveReq userSaveReq);

    /**
     * 删除电子书
     * @param id 书籍id
     * @return
     */
    CommonResult delete(Long id);

    /**
     * 修改密码
     * @param userResetPasswordReq
     * @return
     */
    CommonResult resetPassword(UserResetPasswordReq userResetPasswordReq);

    /**
     * 用户登入
     * @param userLoginReq 用户登入请求参数
     * @return
     */
    UserLoginResp login(UserLoginReq userLoginReq);
}
