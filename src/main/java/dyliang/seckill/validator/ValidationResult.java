package dyliang.seckill.validator;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author dyliang
 * @Date 2020/8/6 9:49
 * @Version 1.0
 */
@Data
public class ValidationResult {

    /**
     * 校验结果是否有错
     */
    private boolean hasErrors = false;

    /**
     * 存放错误信息的map
     */
    private Map<String,String> errorMsgMap = new HashMap<>();

    public String getErrMsg() {
        return StringUtils.join(errorMsgMap.values().toArray(),",");
    }

    public boolean isHasErrors() {
        return hasErrors;
    }

}
