package dyliang.seckill.controller;

import dyliang.seckill.error.BusinessException;
import dyliang.seckill.error.EmBusinessError;
import dyliang.seckill.response.CommonReturnType;
import dyliang.seckill.service.OrderService;
import dyliang.seckill.service.model.OrderModel;
import dyliang.seckill.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


/**
 * 订单管理表现层
 *
 * @Author dyliang
 * @Date 2020/8/7 10:28
 * @Version 1.0
 */

@Controller
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController extends BaseController{

    @Autowired
    private OrderService orderService;

    @Autowired
    private HttpServletRequest httpServletRequest;


    /**
     * 创建订单
     *
     * @param itemId
     * @param amount
     * @param promoId
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/create", method = {RequestMethod.POST}, consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType createOrder(@RequestParam(name = "itemId") Integer itemId,
                                        @RequestParam(name = "amount") Integer amount,
                                        @RequestParam(name = "promoId", required = false) Integer promoId) throws BusinessException {
        // 获取session保存到已经登录的用户
        Boolean is_login = (Boolean) this.httpServletRequest.getSession().getAttribute("IS_LOGIN");
        // 判断是否有登录用户
        if(is_login == null || !is_login.booleanValue()){
            throw new  BusinessException(EmBusinessError.USER_NOT_LOGIN);
        }

        // 获取登录用户
        UserModel login_user = (UserModel) this.httpServletRequest.getSession().getAttribute("LOGIN_USER");
        OrderModel order = orderService.createOrder(login_user.getId(), itemId, promoId, amount);
        // 创建订单
        return CommonReturnType.create(null);

    }
}
