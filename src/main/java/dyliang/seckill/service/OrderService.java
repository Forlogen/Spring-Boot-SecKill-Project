package dyliang.seckill.service;

import dyliang.seckill.error.BusinessException;
import dyliang.seckill.service.model.OrderModel;

/**
 * @Author dyliang
 * @Date 2020/8/7 9:50
 * @Version 1.0
 */
public interface OrderService {

    // 下单
    OrderModel createOrder(Integer userID, Integer itemId, Integer promoId, Integer amount) throws BusinessException;
}
