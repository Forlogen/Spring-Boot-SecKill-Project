package dyliang.seckill.error;

/**
 * 定义错误信息的规范，主要包含错误码和错误信息
 *
 * @Author dyliang
 * @Date 2020/8/4 14:53
 * @Version 1.0
 */
public interface CommonError {

    int getErrorCode();

    String getErrorMessage();

    CommonError setErrorMessage(String errorMessage);
}
