package dyliang.seckill.error;

/**
 * 自定义错误类型
 *
 * @Author dyliang
 * @Date 2020/8/4 14:55
 * @Version 1.0
 */

public enum EmBusinessError implements CommonError{

    // 通用错误类型10001
    PARAMETER_VALIDATION_ERROR(10001,"参数不合法"),
    UNKNOWN_ERROR(10002,"未知错误"),

    // 20000开头为用户信息相关错误定义
    USER_NOT_EXIST(20001,"用户不存在"),
    USER_LOGIN_FAIL(20002,"用户手机号或密码不正确"),
    USER_NOT_LOGIN(20003, "用户还未登录"),

    // 30000开头为交易信息错误
    STOCK_NOT_ENOUGH(30001, "库存不足")
    ;

    private int errorCode;
    private String errorMessage;

    EmBusinessError(int erCode, String errMsg){
        this.errorCode = erCode;
        this.errorMessage = errMsg;
    }

    @Override
    public int getErrorCode() {
        return this.errorCode;
    }

    @Override
    public String getErrorMessage() {
        return this.errorMessage;
    }

    @Override
    public CommonError setErrorMessage(String errMsg) {
        this.errorMessage = errMsg;
        return this;
    }
}
