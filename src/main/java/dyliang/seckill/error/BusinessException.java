package dyliang.seckill.error;

/**
 * 自定义异常
 *
 * @Author dyliang
 * @Date 2020/8/4 15:21
 * @Version 1.0
 */
public class BusinessException extends Exception implements CommonError{

    private CommonError commonError;

    /**
     *
     * @param commonError
     */
    public BusinessException(CommonError commonError){
        super();
        this.commonError = commonError;
    }

    /**
     *
     * @param commonError
     * @param errMsg
     */
    public BusinessException(CommonError commonError, String errMsg){
        super();
        this.commonError = commonError;
        this.commonError.setErrorMessage(errMsg);
    }

    @Override
    public int getErrorCode() {
        return this.commonError.getErrorCode();
    }

    @Override
    public String getErrorMessage() {
        return this.commonError.getErrorMessage();
    }

    @Override
    public CommonError setErrorMessage(String errorMessage) {
        this.commonError.setErrorMessage(errorMessage);
        return this;
    }
}
