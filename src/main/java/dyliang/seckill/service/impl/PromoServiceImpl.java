package dyliang.seckill.service.impl;

import dyliang.seckill.dao.PromoDOMapper;
import dyliang.seckill.domain.PromoDO;
import dyliang.seckill.service.PromoService;
import dyliang.seckill.service.model.PromoModel;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


/**
 * @Author dyliang
 * @Date 2020/8/7 15:52
 * @Version 1.0
 */
@Service
public class PromoServiceImpl implements PromoService {

    @Autowired
    private PromoDOMapper promoDOMapper;

    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        PromoDO promoDO = this.promoDOMapper.selectByItemId(itemId);
        PromoModel promoModel = this.convertFromEntity(promoDO);
        if(promoModel == null){
            return null;
        }

        // 判断秒杀活动状态
        if (promoModel.getStartTime().isAfterNow()){
            promoModel.setStatus(1);
        } else if(promoModel.getEndTime().isBeforeNow()){
            promoModel.setStatus(3);
        } else {
            promoModel.setStatus(2);
        }

        return promoModel;
    }

    private PromoModel convertFromEntity(PromoDO promo) {
        if (promo == null) {
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promo, promoModel);
        promoModel.setPromoItemPrice(new BigDecimal(promo.getPromoItemPrice()));
        promoModel.setStartTime(new DateTime(promo.getStartTime()));
        promoModel.setEndTime(new DateTime(promo.getEndTime()));
        return promoModel;
    }
}
