package dyliang.seckill.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Author dyliang
 * @Date 2020/8/6 14:44
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemModel {

    private Integer id;

    private String title;

    @NotNull(message = "商品价格不能为空")
    @Min(value = 0, message = "商品价格必须大于0")
    private BigDecimal price;

    @NotNull(message = "库存必须填写")
    private Integer stock;

    @NotNull(message = "商品描述不能为空")
    private String description;

    private Integer sales;

    @NotNull(message = "商品图片信息不能为空")
    private String imgUrl;

    /**
     * 使用聚合模型，如果promoModel不为空，则表示其拥有还未结束的秒杀活动
     */
    private PromoModel promoModel;

}
