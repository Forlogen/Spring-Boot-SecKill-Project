package dyliang.seckill;

import dyliang.seckill.dao.ItemDOMapper;
import dyliang.seckill.dao.ItemStockDOMapper;
import dyliang.seckill.dao.UserDOMapper;
import dyliang.seckill.dao.UserPasswordDOMapper;
import dyliang.seckill.domain.ItemDO;
import dyliang.seckill.domain.ItemStockDO;
import dyliang.seckill.domain.UserDO;
import dyliang.seckill.domain.UserPasswordDO;
import dyliang.seckill.service.ItemService;
import dyliang.seckill.service.UserService;
import dyliang.seckill.service.impl.ItemServiceImpl;
import dyliang.seckill.service.model.ItemModel;
import dyliang.seckill.service.model.UserModel;
import dyliang.seckill.utils.DOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author dyliang
 * @Date 2020/8/4 10:28
 * @Version 1.0
 */
@SpringBootTest
class SeckillApplicationTests {

    @Autowired
    UserPasswordDOMapper userPasswordMapper;

    @Autowired
    ItemDOMapper itemDOMapper;

    @Autowired
    ItemService itemService;

    @Autowired
    UserDOMapper userDOMapper;

    @Autowired
    ItemStockDOMapper itemStockDOMapper;

    @Autowired
    UserService userService;

    private DOUtils utils = new DOUtils();

    @Test
    void test() {
//        UserDO userById = userDOMapper.selectByPrimaryKey(13);
//        System.out.println(userById);
//        UserModel userById = userService.findUserById(13);
//        System.out.println(userById);
        UserDO userDO = userDOMapper.selectByTelephone("13260674915");
        System.out.println(userDO);
    }

    @Test
    void testItem() {
        ItemModel itemModel = new ItemModel().builder().title("aaa").price(BigDecimal.valueOf(800)).stock(200).description("a").imgUrl("a").build();
        // 获取保存商品信息的
        ItemDO item = this.utils.convertItemFromItemModel(itemModel);
        this.itemDOMapper.insertSelective(item);

        ItemDO itemDO = this.itemDOMapper.selectByPrimaryKey(item.getId());
        System.out.println(itemDO);

        itemModel.setId(item.getId());
        System.out.println(itemModel.getId());

        ItemStockDO itemStockDO = new ItemStockDO().builder().itemId(itemModel.getId()).stock(itemModel.getStock()).build();
        System.out.println(itemStockDO);

    }
}
