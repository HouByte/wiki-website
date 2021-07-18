package cn.bugio.wiki.service.impl;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.dao.UserMapper;
import cn.bugio.wiki.domain.dto.UserReq;
import cn.bugio.wiki.domain.dto.UserResp;
import cn.bugio.wiki.domain.dto.UserSearchReq;
import cn.bugio.wiki.domain.entity.User;
import cn.bugio.wiki.service.UserService;
import cn.bugio.wiki.util.CopyUtil;
import cn.bugio.wiki.util.SnowFlake;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/06/27
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    private final SnowFlake snowFlake;

    @Autowired
    public UserServiceImpl(UserMapper userMapper, SnowFlake snowFlake) {
        this.userMapper = userMapper;
        this.snowFlake = snowFlake;
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
                example.createCriteria().andLike("name",keyword).orLike("desc",keyword);
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
     * @param userReq 保存数据
     * @return
     */
    @Override
    public CommonResult save(UserReq userReq) {
        if (userReq == null){
            return CommonResult.error("参数不能为空");
        }
        User user = CopyUtil.copy(userReq, User.class);
        String md5Password = DigestUtils.md5Hex(userReq.getPassword());
        user.setPassword(md5Password);
        int op = 0;
        //id为空为新增
        if (user.getId() == null){
            user.setId(snowFlake.nextId());
            user.setCreated(new Date());
            user.setUpdated(new Date());
            op = userMapper.insertSelective(user);
        } else{
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


}
