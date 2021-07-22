package cn.bugio.wiki.service.impl;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.dao.UserMapper;
import cn.bugio.wiki.domain.dto.*;
import cn.bugio.wiki.domain.entity.User;
import cn.bugio.wiki.exception.BusinessException;
import cn.bugio.wiki.exception.BusinessExceptionCode;
import cn.bugio.wiki.service.UserService;
import cn.bugio.wiki.util.CopyUtil;
import cn.bugio.wiki.util.SnowFlake;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/06/27
 */
@Slf4j
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final SnowFlake snowFlake;

    private final RedisTemplate redisTemplate;
    @Autowired
    public UserServiceImpl(UserMapper userMapper, SnowFlake snowFlake, RedisTemplate redisTemplate) {
        this.userMapper = userMapper;
        this.snowFlake = snowFlake;
        this.redisTemplate = redisTemplate;
    }

    /**
     * <h2>查询列表</h2>
     *
     * @param searchReq 查询关键字 为空全部查询
     * @return
     */
    @Override
    public CommonResult<List<UserResp>> list(UserSearchReq searchReq) {
        List<User> users = null;
        if (searchReq == null){
            users = userMapper.selectAll();
        } else {
            Example example = new Example(User.class);
            if (StringUtils.isNotEmpty(searchReq.getKeyword())){
                String keyword = searchReq.getKeyword();
                keyword = "%"+keyword+"%";
                example.createCriteria().andLike("name",keyword).orLike("loginName",keyword);
            }
            if (searchReq.getCategoryId() != null && searchReq.getCategoryId() != 0){
                Long categoryId = searchReq.getCategoryId();
                example.createCriteria().andLike("categoryIds","%"+categoryId+"%");
            }
            users = userMapper.selectByExample(example);
        }
        if (users == null){
            return CommonResult.success("没有书籍");
        }

        List<UserResp> userResps = CopyUtil.copyList(users, UserResp.class);

        return CommonResult.success(userResps);
    }

    /**
     * 保存电子书
     *
     * @param userSaveReq 保存数据
     * @return
     */
    @Override
    public CommonResult save(UserSaveReq userSaveReq) {
        if (userSaveReq == null){
            return CommonResult.error("参数不能为空");
        }
        //登入名称和邮箱必须唯一
        checkUserUnique(userSaveReq);
        User user = CopyUtil.copy(userSaveReq, User.class);
        String md5Password = DigestUtils.md5Hex(userSaveReq.getPassword());
        user.setPassword(md5Password);
        int op = 0;
        //id为空为新增
        if (user.getId() == null){
            user.setId(snowFlake.nextId());
            user.setCreated(new Date());
            user.setUpdated(new Date());
            op = userMapper.insertSelective(user);
        } else{ //修改
            user.setLoginName(null);
            user.setPassword(null);
            user.setUpdated(new Date());
            op = userMapper.updateByPrimaryKeySelective(user);
        }
        if (op == 0){
            return CommonResult.error("保存失败");
        }
        return CommonResult.success("保存成功");
    }

    /**
     * 登入名称不存在检查
     * @param userSaveReq
     * @return
     */
    private void checkUserUnique(UserSaveReq userSaveReq) {
        if (userSaveReq.getId() == null && null != selectUserByLoginName(userSaveReq.getLoginName())){
            throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);
        }
        if (userSaveReq.getId() == null && null != selectUserByEmail(userSaveReq.getEmail())){
            throw new BusinessException(BusinessExceptionCode.USER_EMAIL_EXIST);
        }
    }

    /**
     * 根据登入名查询用户
     * @param loginName 登入名
     * @return
     */
    @Override
    public User selectUserByLoginName(String loginName) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("loginName", loginName);
        return userMapper.selectOneByExample(example);
    }

    /**
     * 根据邮箱查询用户
     * @param email 邮箱
     * @return
     */
    @Override
    public User selectUserByEmail(String email) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("email", email);
        return userMapper.selectOneByExample(example);
    }

    /**
     * 删除电子书
     *
     * @param id 书籍id
     * @return
     */
    @Override
    public CommonResult delete(Long id) {
        int delete = userMapper.deleteByPrimaryKey(id);
        if (delete == 0){
            return CommonResult.error("删除失败");
        }
        return CommonResult.success("删除成功");
    }

    /**
     * 修改密码
     *
     * @param userResetPasswordReq
     * @return
     */
    @Override
    public CommonResult resetPassword(UserResetPasswordReq userResetPasswordReq) {
        User user = userMapper.selectByPrimaryKey(userResetPasswordReq.getId());
        if (user == null){
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_NOT_FOUND);
        }
        String newPassword = DigestUtils.md5Hex(userResetPasswordReq.getPassword());
        int update = userMapper.updateByPrimaryKeySelective(User.builder().id(user.getId()).password(newPassword).build());
        if (update == 0){
            return CommonResult.error("密码更新失败");
        }
        return CommonResult.success("密码更新成功");
    }

    /**
     * 用户登入
     *
     * @param userLoginReq 用户登入请求参数
     * @return
     */
    @Override
    public CommonResult login(UserLoginReq userLoginReq) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("loginName",userLoginReq.getLoginName());
        User user = userMapper.selectOneByExample(example);
        if (ObjectUtils.isEmpty(user)){
            log.info("{} 用户不存在",userLoginReq.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        }
        String password = DigestUtils.md5Hex(userLoginReq.getPassword());
        if (!user.getPassword().equals(password)){
            log.info("{} 用户密码错误",userLoginReq.getLoginName());
            throw new BusinessException(BusinessExceptionCode.LOGIN_USER_ERROR);
        }
        UserLoginResp loginResp = CopyUtil.copy(user, UserLoginResp.class);
        String token = UUID.randomUUID().toString().replace("-", "");
        loginResp.setToken(token);
        String tokenKey = String.format("USER_TOKEN_%s",token);
        redisTemplate.opsForValue().set(tokenKey,loginResp,3600 * 12, TimeUnit.SECONDS);

        return CommonResult.success(loginResp);
    }

    /**
     * 用户登出
     *
     * @param token 口令
     * @return
     */
    @Override
    public CommonResult logout(String token) {
        String tokenKey = String.format("USER_TOKEN_%s",token);
        redisTemplate.delete(tokenKey);
        return CommonResult.success("退出成功");
    }

    /**
     * 修改用户状态
     *
     * @param id     用户id
     * @param enable 状态
     * @return
     */
    @Override
    public CommonResult updateEnable(Long id, Boolean enable) {
        int update = userMapper.updateByPrimaryKeySelective(User.builder().id(id).enable(enable).build());
        if (update == 0){
            return CommonResult.error("更新失败");
        }
        return CommonResult.success("更新成功");
    }


}
