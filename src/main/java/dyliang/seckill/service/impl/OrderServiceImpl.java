package dyliang.seckill.service.impl;

import dyliang.seckill.dao.OrderDOMapper;
import dyliang.seckill.dao.SequenceDOMapper;
import dyliang.seckill.domain.OrderDO;
import dyliang.seckill.domain.SequenceDO;
import dyliang.seckill.error.BusinessException;
import dyliang.seckill.error.EmBusinessError;
import dyliang.seckill.service.ItemService;
import dyliang.seckill.service.OrderService;
import dyliang.seckill.service.UserService;
import dyliang.seckill.service.model.ItemModel;
import dyliang.seckill.service.model.OrderModel;
import dyliang.seckill.service.model.UserModel;
import dyliang.seckill.utils.DOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Author dyliang
 * @Date 2020/8/7 9:53
 * @Version 1.0
 */

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDOMapper orderDOMapper;

    @Autowired
    private SequenceDOMapper sequenceMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    private DOUtils utils = new DOUtils();

    @Override
    @Transactional
    public OrderModel createOrder(Integer userID, Integer itemId, Integer promoId, Integer amount) throws BusinessException {
        // 下单信息验证
        ItemModel item = this.itemService.getItemById(itemId);
        if(item == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "该商品不存在，请重新选择");
        }

        UserModel user = this.userService.findUserById(userID);
        if(user == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "该用户不存在");
        }

        if(amount <= 0 || amount > 99){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "购买数量不正确");
        }

        // 验证是否存在秒杀活动
        if (promoId != null) {
            //校验活动是否存在适用商品
            if (promoId.intValue() != item.getPromoModel().getId()) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动信息不正确");
                //校验秒杀活动是否正在进行
            }else if (item.getPromoModel().getStatus().intValue() != 2) {
                throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "活动还未开始");
            }
        }

        // 下单减库存
        boolean result = itemService.decreaseStock(itemId, amount);
        if(!result){
            throw new BusinessException(EmBusinessError.STOCK_NOT_ENOUGH);
        }

        // 保存订单
        OrderModel orderModel = new OrderModel().builder().
                                    userId(userID).
                                    itemId(itemId).
                                    amount(amount).build();

        if (promoId != null){
            orderModel.setItemPrice(item.getPromoModel().getPromoItemPrice());
        } else {
            orderModel.setItemPrice(item.getPrice());
        }
        orderModel.setPromoId(promoId);
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(new BigDecimal(amount)));

        // 生成交易流水号
        orderModel.setId(this.generateOrderNo());
        OrderDO orderDO = this.utils.convertFromOrderModel(orderModel);
        orderDOMapper.insertSelective(orderDO);

        itemService.increaseSales(itemId, amount);

        return orderModel;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    String generateOrderNo() {
        //订单号16位
        //前8位为时间信息
        StringBuilder stringBuilder = new StringBuilder();
        LocalDateTime now = LocalDateTime.now();
        String nowDate = now.format(DateTimeFormatter.ISO_DATE).replace("-","");
        stringBuilder.append(nowDate);

        //中间6位为自增序列
        int seq = 0;
        SequenceDO sequence = sequenceMapper.getSequenceByName("order_info");
        seq = sequence.getCurrentValue();
        sequence.setCurrentValue(sequence.getCurrentValue() + sequence.getStep());
        sequenceMapper.updateByPrimaryKeySelective(sequence);
        String seqStr = String.valueOf(seq);
        for(int i = 0; i < 6-seqStr.length(); i++) {
            stringBuilder.append(0);
        }
        stringBuilder.append(seqStr);

        //最后2位为分库分表位，暂时写死
        stringBuilder.append("00");

        return stringBuilder.toString();
    }
}

