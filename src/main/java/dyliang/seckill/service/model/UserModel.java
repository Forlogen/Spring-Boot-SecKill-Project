package dyliang.seckill.service.model;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author dyliang
 * @Date 2020/8/4 10:44
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserModel {

    private Integer id;

    private String name;

    private Byte gender;

    @NotNull
    @Min(value = 0)
    @Max(value = 150)
    private Integer age;

    private String telephone;

    private String registerMode;

    private String thirdPartyId;

    private String encrptPassword;
}