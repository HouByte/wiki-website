package cn.bugio.wiki.controller;

import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.domain.dto.*;
import cn.bugio.wiki.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * <h1></h1>
 *
 * @version 1.0
 * @author: Vincent Vic
 * @since: 2021/06/27
 */
@RestController
@RequestMapping("user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("list")
    public CommonResult<List<UserResp>> list(@Valid @RequestBody(required = false) UserSearchReq searchReq){
        return userService.list(searchReq);
    }

    @PostMapping("save")
    public CommonResult save(@Valid @RequestBody UserSaveReq userSaveReq){
        return userService.save(userSaveReq);
    }

    @PostMapping("reset-password")
    public CommonResult resetPassword(@Valid @RequestBody UserResetPasswordReq userResetPasswordReq){
        return userService.resetPassword(userResetPasswordReq);
    }

    @PostMapping("login")
    public CommonResult login(@Valid @RequestBody UserLoginReq userLoginReq){
        UserLoginResp login = userService.login(userLoginReq);
        return CommonResult.success(login);
    }

    @DeleteMapping("delete/{id}")
    public CommonResult delete(@PathVariable("id") Long id){
        return userService.delete(id);
    }
}
