package dyliang.seckill.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import sun.util.resources.cldr.ig.CurrencyNames_ig;

import java.math.BigDecimal;

/**
 * @Author dyliang
 * @Date 2020/8/7 9:51
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderModel {

    private String id;

    private Integer userId;

    private Integer itemId;

    private BigDecimal itemPrice;

    private Integer amount;

    private BigDecimal orderPrice;

    private Integer promoId;
}
