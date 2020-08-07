package dyliang.seckill.service.impl;


import dyliang.seckill.dao.ItemDOMapper;
import dyliang.seckill.dao.ItemStockDOMapper;
import dyliang.seckill.domain.ItemDO;
import dyliang.seckill.domain.ItemStockDO;
import dyliang.seckill.error.BusinessException;
import dyliang.seckill.error.EmBusinessError;
import dyliang.seckill.service.ItemService;
import dyliang.seckill.service.PromoService;
import dyliang.seckill.service.model.ItemModel;
import dyliang.seckill.service.model.PromoModel;
import dyliang.seckill.utils.DOUtils;
import dyliang.seckill.validator.ValidationResult;
import dyliang.seckill.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author dyliang
 * @Date 2020/8/6 15:02
 * @Version 1.0
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemDOMapper itemDOMapper;

    @Autowired
    private ItemStockDOMapper itemStockDOMapper;

    @Autowired
    private PromoService promoService;

    @Autowired
    private ValidatorImpl validator;

    private DOUtils utils = new DOUtils();

    /**
     *  创建商品
     *
     * @param itemModel
     * @return
     * @throws BusinessException
     */
    @Override
    @Transactional
    public ItemModel createItem(ItemModel itemModel) throws BusinessException {
        // 首先验证传入的bean的各个字段是否合法
        ValidationResult results = this.validator.validate(itemModel);
        // 如果某个字段输入有误，则抛出异常，提示错误信息
        if(results.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, results.getErrMsg());
        }

        // 获取数据库保存的商品实体类形式
        ItemDO item = this.utils.convertItemFromItemModel(itemModel);
        // 执行数据库保存操作
        this.itemDOMapper.insertSelective(item);
        // 获取item表中的id字段信息，设置itemModel的id字段，便于同步保存item_stock表
        itemModel.setId(item.getId());

        // 根据itemModel获取ItemStockDO实体类，保存库存信息
        ItemStockDO itemStockDO = this.utils.convertItemStockFromItemModel(itemModel);
        this.itemStockDOMapper.insertSelective(itemStockDO);

        // 最后返回成功创建的商品信息
        return this.getItemById(itemModel.getId());

    }

    /**
     * 获取商品列表
     *
     * @return
     */
    @Override
    public List<ItemModel> listItem() {
        // 查询所有
        List<ItemDO> itemModels = this.itemDOMapper.listItem();
        // 将每一个itemModel对象转换得到对应的ItemModel对象，得到转换后的List
        List<ItemModel> itemModelList = itemModels.stream().map(item -> {
            ItemStockDO itemStockDO = this.itemStockDOMapper.selectByItemId(item.getId());
            ItemModel itemModel = this.utils.convertItemFromEntity(item, itemStockDO);

            return itemModel;
        }).collect(Collectors.toList());

        // 返回商品列表的List
        return itemModelList;
    }

    /**
     * 根据id获取对应的商品
     *
     * @param id
     * @return
     */
    @Override
    public ItemModel getItemById(Integer id) {
        // 根据id获取item表中信息
        ItemDO item = this.itemDOMapper.selectByPrimaryKey(id);

        if(item == null){
            return null;
        }

        // 根据item_id获取item_stock表中信息
        ItemStockDO itemStockDO = this.itemStockDOMapper.selectByItemId(item.getId());
        // 构建返回的ItemModel
        ItemModel itemModel = this.utils.convertItemFromEntity(item, itemStockDO);

        PromoModel promoModel = this.promoService.getPromoByItemId(itemModel.getId());
        if(promoModel != null && promoModel.getStatus().intValue() != 3){
            itemModel.setPromoModel(promoModel);
        }

        return itemModel;
    }


    @Override
    @Transactional
    public boolean decreaseStock(Integer itemId, Integer amount){
        int affectedRows = this.itemStockDOMapper.decreaseStock(itemId, amount);
        if (affectedRows > 0){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void increaseSales(Integer itemId, Integer amount) {
        this.itemDOMapper.increaseSales(itemId, amount);

    }
}
