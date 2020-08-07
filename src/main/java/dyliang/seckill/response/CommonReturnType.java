package dyliang.seckill.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 表现层统一返回数据格式
 *
 * @Author dyliang
 * @Date 2020/8/4 14:42
 * @Version 1.0
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommonReturnType {

    private String status;
    private Object data;

    public static CommonReturnType create(Object result){
        return CommonReturnType.create(result, "success");
    }

    public static CommonReturnType create(Object result, String status){
        return  new CommonReturnType().builder().status(status).data(result).build();
    }
}
