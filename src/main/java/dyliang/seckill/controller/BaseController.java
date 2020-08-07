package dyliang.seckill.controller;

import dyliang.seckill.error.BusinessException;
import dyliang.seckill.error.EmBusinessError;
import dyliang.seckill.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 统一处理自定义异常
 *
 * @Author dyliang
 * @Date 2020/8/4 15:15
 * @Version 1.0
 */
public class BaseController {

    public static final String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex){

        Map<String, Object> responseData = new HashMap<>();

        if(ex instanceof BusinessException){
            BusinessException exception = (BusinessException)ex;

            responseData.put("errCode", exception.getErrorCode());
            responseData.put("errMsg", exception.getErrorMessage());
        } else {
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrorCode());
            responseData.put("errMsg",  EmBusinessError.UNKNOWN_ERROR.getErrorMessage());
        }

        return CommonReturnType.create(responseData,"fail");
    }
}
