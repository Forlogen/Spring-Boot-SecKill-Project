package dyliang.seckill.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import dyliang.seckill.error.BusinessException;
import dyliang.seckill.response.CommonReturnType;
import dyliang.seckill.service.ItemService;
import dyliang.seckill.service.model.ItemModel;
import dyliang.seckill.utils.DOUtils;
import dyliang.seckill.vo.ItemVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商品管理表现层
 *
 * @Author dyliang
 * @Date 2020/8/6 16:21
 * @Version 1.0
 */

@Controller
@RequestMapping("/item")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ItemController extends BaseController {

    @Autowired
    private ItemService itemService;

    private DOUtils utils = new DOUtils();

    /**
     *  创建商品
     *
     * @param title
     * @param price
     * @param description
     * @param imgUrl
     * @param stock
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "/new", consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType create(@RequestParam(name = "title") String title,
                                    @RequestParam(name = "price") BigDecimal price,
                                    @RequestParam(name = "description") String description,
                                    @RequestParam(name = "imgUrl") String imgUrl,
                                    @RequestParam(name = "stock") Integer stock) throws BusinessException {

        ItemModel itemModel = new ItemModel().builder().title(title).
                                                        price(price).
                                                        description(description).
                                                        imgUrl(imgUrl).
                                                        stock(stock).build();

        // 数据库中执行保存
        ItemModel item = this.itemService.createItem(itemModel);
        ItemVO itemVO =  this.utils.convertVOFromModel(item);
        return CommonReturnType.create(itemVO);
    }

    /**
     *  根据id查询对应的商品信息
     * @param id
     * @return
     */
    @GetMapping(value = "/get")
    @ResponseBody
    public CommonReturnType getItem(@RequestParam(value = "id") Integer id){
        ItemModel item = this.itemService.getItemById(id);
        ItemVO itemVO = this.utils.convertVOFromModel(item);

        return CommonReturnType.create(itemVO);
    }

    /**
     *  获取商品列表
     *
     * @return
     */
    @GetMapping(value = "/list")
    @ResponseBody
    public CommonReturnType listItem() {
        List<ItemModel> itemModelList = itemService.listItem();

        List<ItemVO> itemVOList = itemModelList.stream().map(itemModel -> {
            ItemVO itemVO = this.utils.convertVOFromModel(itemModel);
            return itemVO;
        }).collect(Collectors.toList());

        return CommonReturnType.create(itemVOList);
    }
}
