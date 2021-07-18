package cn.bugio.wiki.aspect;



import cn.bugio.wiki.common.CommonResult;
import cn.bugio.wiki.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Vincent Vic
 * @Date 2020/12/6 18:16
 * @Version 1.0
 * @Description 统一异常处理
 */

@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {
    @ExceptionHandler(value = Exception.class)
    public CommonResult<String> handlerException(HttpServletRequest req,
                                                 Exception ex) {
        log.error("{} 捕获到异常 ==> {}",req,ex);
        CommonResult<String> response = CommonResult.errorException("系统出现故障，请联系管理员");
        return response;
    }

    @ExceptionHandler(value = BusinessException.class)
    public CommonResult<String> handlerBindException(HttpServletRequest req,
                                                     BusinessException ex) {
        log.error("{}  业务异常 ==> {}",req,ex);
        CommonResult<String> response = CommonResult.errorException(ex.getCode().getDesc());
        return response;
    }

    @ExceptionHandler(value = BindException.class)
    public CommonResult<String> handlerBindException(HttpServletRequest req,
                                                     BindException ex) {
        log.error("{}  参数校验异常 ==> {}",req,ex);
        CommonResult<String> response = CommonResult.errorException(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        return response;
    }
}
