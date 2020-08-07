package dyliang.seckill.domain;

import lombok.Data;

import java.util.Date;


@Data
public class PromoDO {

    private Integer id;

    private String promoName;

    private Date startTime;

    private Integer itemId;

    private Double promoItemPrice;

    private Date endTime;

}