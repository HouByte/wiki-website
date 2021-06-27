package cn.bugio.wiki.common;

/**
 * @author Vincent Vic
 * @version 1.0
 * @Description 统一数据返回规范
 * @since 2021/2/16
 */


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CommonResult<T> {

    //成功
    public static final int SUCCESS_CODE = 0;
    //错误
    public static final int ERROR_CODE = 1;
    //成功但是没有数据
    public static final int SUCCESS_DATA_CODE = 2;
    //异常
    public static final int EXCEPTION_CODE = 3;
    //未授权、未登入
    public static final int AUTH_CODE = 4;

    // 响应业务状态
    private Integer code;

    // 响应消息
    private String msg;

    // 响应中的数据
    private T data;

    public static <T> CommonResult<T> build(Integer status, String msg, T data) {
        return new CommonResult<T>(status, msg, data);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult(data);
    }

    public static <T> CommonResult<T> success() {
        return new CommonResult(null);
    }

    public static <T> CommonResult<T> success(String msg) {
        return new CommonResult(CommonResult.SUCCESS_CODE, msg, null);
    }

    public static <T> CommonResult<T> success(String msg, T data) {
        return new CommonResult(CommonResult.SUCCESS_CODE, msg, data);
    }

    public static <T> CommonResult<T> success(int code, String msg) {
        return new CommonResult(code, msg, null);
    }

    public static <T> CommonResult<T> success(int code, String msg, T data) {
        return new CommonResult(CommonResult.SUCCESS_CODE, msg, data);
    }

    public static <T> CommonResult<T> errorMsg(String msg) {
        return new CommonResult(CommonResult.ERROR_CODE, msg, null);
    }

    public static <T> CommonResult<T> error() {
        return new CommonResult(CommonResult.ERROR_CODE, "error", null);
    }

    public static <T> CommonResult<T> error(String msg) {
        return new CommonResult(CommonResult.ERROR_CODE, msg, null);
    }

    public static <T> CommonResult<T> error(String msg, T data) {
        return new CommonResult(CommonResult.ERROR_CODE, msg, data);
    }
    public static <T> CommonResult<T> error(int code, String msg) {
        return new CommonResult(CommonResult.ERROR_CODE, msg, null);
    }

    public static <T> CommonResult<T> error(int code, String msg, T data) {
        return new CommonResult(code, msg, data);
    }

    public static <T> CommonResult<T> error(T data) {
        return new CommonResult(CommonResult.ERROR_CODE, "error", data);
    }

    public static <T> CommonResult<T> errorAuthMsg(String msg) {
        return new CommonResult(CommonResult.AUTH_CODE, msg, null);
    }

    public static <T> CommonResult<T> errorException(String msg) {
        return new CommonResult(CommonResult.EXCEPTION_CODE, msg, null);
    }

    public static <T> CommonResult<T> errorException(T data) {
        return new CommonResult(CommonResult.EXCEPTION_CODE, "exception", data);
    }

    public static <T> CommonResult<T> errorException(String msg, T data) {
        return new CommonResult(CommonResult.EXCEPTION_CODE, msg, data);
    }

    public static <T> CommonResult<T> errorException(Integer code, String msg, T data) {
        return new CommonResult(code, msg, data);
    }

    public static <T> CommonResult<T> errorException(Integer code, String msg) {
        return new CommonResult(code, msg, null);
    }

    public CommonResult() {

    }

    public CommonResult(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public CommonResult(T data) {
        this.code = CommonResult.SUCCESS_CODE;
        this.msg = "Success";
        this.data = data;
    }

    @JsonIgnore
    public Boolean isSuccess() {
        return this.code == SUCCESS_CODE;
    }

}
