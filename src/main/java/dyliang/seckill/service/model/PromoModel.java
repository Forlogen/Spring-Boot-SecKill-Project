package dyliang.seckill.service.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

import java.math.BigDecimal;

/**
 * @Author dyliang
 * @Date 2020/8/7 15:50
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromoModel {

    private Integer id;

    private String promoName;

    /**
     * 活动状态:
     *  1表示未开始，
     *  2表示进行中，
     *  3表示已结束
     */
    private Integer status;

    private DateTime startTime;

    private DateTime endTime;

    private Integer itemId;

    private BigDecimal promoItemPrice;

}
