package dyliang.seckill.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 定义前端展示所需的Item信息的包装类
 *
 * @Author dyliang
 * @Date 2020/8/6 16:25
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemVO {

    private Integer id;

    private String title;

    private BigDecimal price;

    private Integer stock;

    private String description;

    private Integer sales;

    private String imgUrl;

    /**
     * 记录商品是否在秒杀活动中，
     *  0：没有活动
     *  1：待开始
     *  2：正在进行
     */
    private Integer promoStatus;

    private BigDecimal promoPrice;

    private Integer promoId;

    private String startTime;
}
