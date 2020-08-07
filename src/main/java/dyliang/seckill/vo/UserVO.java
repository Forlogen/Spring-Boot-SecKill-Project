package dyliang.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 定义前端展示所有的user信息的包装类
 *
 * @Author dyliang
 * @Date 2020/8/4 10:59
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {

    private Integer id;

    private String name;

    private Byte gender;

    private Integer age;

    private String telephone;

}
