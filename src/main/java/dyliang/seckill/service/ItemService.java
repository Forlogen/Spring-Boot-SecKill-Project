package dyliang.seckill.service;

import dyliang.seckill.error.BusinessException;
import dyliang.seckill.service.model.ItemModel;

import java.util.List;

/**
 * @Author dyliang
 * @Date 2020/8/6 15:01
 * @Version 1.0
 */
public interface ItemService {

    ItemModel createItem(ItemModel itemModel) throws BusinessException;

    List<ItemModel> listItem();

    ItemModel getItemById(Integer id);

    boolean decreaseStock(Integer itemId, Integer amount);

    void increaseSales(Integer itemId, Integer amount);
}
