package dyliang.seckill.service;

import dyliang.seckill.service.model.PromoModel;

/**
 * @Author dyliang
 * @Date 2020/8/7 15:51
 * @Version 1.0
 */
public interface PromoService {

    PromoModel getPromoByItemId(Integer itemId);
}
