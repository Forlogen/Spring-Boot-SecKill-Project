package dyliang.seckill.utils;

import dyliang.seckill.domain.*;
import dyliang.seckill.service.model.ItemModel;
import dyliang.seckill.service.model.OrderModel;
import dyliang.seckill.service.model.UserModel;
import dyliang.seckill.vo.ItemVO;
import dyliang.seckill.vo.UserVO;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

/**
 * @Author dyliang
 * @Date 2020/8/4 10:53
 * @Version 1.0
 */
public class DOUtils {

    public UserModel convertFromDO(UserDO userDO, UserPasswordDO userPassword){
        if (userDO == null){
            return null;
        }

        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userDO, userModel);
        if(userPassword != null) {
            userModel.setEncrptPassword(userPassword.getEncrptPassword());
        }

        return userModel;

    }

    public UserVO convertFromModel(UserModel userModel){
        if (userModel == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }

    public UserDO convertFromUserModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserDO user = new UserDO();
        BeanUtils.copyProperties(userModel, user);
        return user;
    }

    public UserModel convertFromEntity(UserDO user, UserPasswordDO userPassword) {
        if (user == null) {
            return null;
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(user, userModel);
        if (userPassword != null) {
            userModel.setEncrptPassword(userPassword.getEncrptPassword());
        }
        return userModel;
    }

    public UserPasswordDO convertPasswordFromModel(UserModel userModel) {
        if (userModel == null) {
            return null;
        }
        UserPasswordDO userPassword = new UserPasswordDO();
        userPassword.setEncrptPassword(userModel.getEncrptPassword());
        userPassword.setUserId(userModel.getId());
        return userPassword;
    }

    public ItemDO convertItemFromItemModel(ItemModel itemModel){
        if (itemModel == null){
            return null;
        }

        ItemDO itemDO = new ItemDO();
        BeanUtils.copyProperties(itemModel, itemDO);
        itemDO.setPrice(itemModel.getPrice().doubleValue());

        return itemDO;
    }

    public ItemStockDO convertItemStockFromItemModel(ItemModel itemModel){
        if (itemModel == null){
            return null;
        }

        System.out.println(itemModel.getId());
        ItemStockDO build = new ItemStockDO().builder().itemId(itemModel.getId()).stock(itemModel.getStock()).build();
        return build;
    }

    public ItemModel convertItemFromEntity(ItemDO item, ItemStockDO itemStockDO) {
        if(item == null){
            return null;
        }

        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(item, itemModel);

        itemModel.setPrice(new BigDecimal(item.getPrice()));
        itemModel.setStock(itemStockDO.getStock());

        System.out.println(itemModel + "-----");
        return itemModel;
    }

    public ItemVO convertVOFromModel(ItemModel itemModel) {
        if (itemModel == null){
            return null;
        }

        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);
        if (itemModel.getPromoModel() != null) {
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoItemPrice());
            itemVO.setStartTime(itemModel.getPromoModel().getStartTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        }else {
            itemVO.setPromoStatus(0);
        }

        return itemVO;
    }

    public OrderDO convertFromOrderModel(OrderModel orderModel){
        if(orderModel == null){
            return null;
        }

        OrderDO order = new OrderDO();
        BeanUtils.copyProperties(orderModel, order);
        order.setItemPrice(orderModel.getItemPrice().doubleValue());
        order.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return order;
    }
}
