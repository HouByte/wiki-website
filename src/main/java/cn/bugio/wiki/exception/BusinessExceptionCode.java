package cn.bugio.wiki.exception;

public enum BusinessExceptionCode {

    /**
     * 业务错误状态
     */
    USER_LOGIN_NAME_EXIST("登录名已存在"),
    USER_EMAIL_EXIST("邮箱已被其他账号绑定"),
    USER_DISABLED("用户被禁用，请联系管理员"),
    LOGIN_USER_ERROR("用户名不存在或密码错误"),
    LOGIN_USER_NOT_FOUND("用户名不存在"),
    VOTE_REPEAT("您已点赞过"),
    ;

    private String desc;

    BusinessExceptionCode(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
