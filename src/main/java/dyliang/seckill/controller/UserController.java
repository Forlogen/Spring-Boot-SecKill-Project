package dyliang.seckill.controller;

import com.alibaba.druid.util.StringUtils;
import dyliang.seckill.error.BusinessException;
import dyliang.seckill.error.EmBusinessError;
import dyliang.seckill.response.CommonReturnType;
import dyliang.seckill.service.UserService;
import dyliang.seckill.service.model.UserModel;
import dyliang.seckill.utils.DOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 用户管理表现层
 *
 * @Author dyliang
 * @Date 2020/8/5 16:39
 * @Version 1.0
 */
@Controller
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    private DOUtils utils = new DOUtils();

    /**
     * 根据id查询用户
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ResponseBody
    public CommonReturnType getUser(@PathVariable(name = "id")Integer id){
        UserModel userById = userService.findUserById(id);
        if(userById == null){
            return CommonReturnType.create(null);
        }

        return CommonReturnType.create(utils.convertFromModel(userById));
    }

    /**
     * 获取验证码
     *
     * @param telephone
     * @return
     */
    @PostMapping(value = "/getotp", consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType getOtp(@RequestParam(name = "telephone")String telephone) {

        Random random = new Random();
        int randomCode = random.nextInt(99999) + 10000;
        String otpCode = String.valueOf(randomCode);

        httpServletRequest.getSession().setAttribute(telephone,otpCode);

        System.out.println("telephone = "+telephone+"::otpCode = "+otpCode);
        return CommonReturnType.create(null);
    }


    /**
     * 注册
     *
     * @param telephone
     * @param otpCode
     * @param name
     * @param age
     * @param gender
     * @param password
     * @return
     * @throws Exception
     */
    @Transactional
    @PostMapping(value = "/register", consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType register(@RequestParam(name = "telephone") String telephone,
                                     @RequestParam(name = "otpCode") String otpCode,
                                     @RequestParam(name = "name") String name,
                                     @RequestParam(name = "age") Integer age,
                                     @RequestParam(name = "gender") Integer gender,
                                     @RequestParam(name = "password") String password) throws Exception {

        // 校验验证码
        String OTPCODE = (String) this.httpServletRequest.getSession().getAttribute(telephone);
        if(!StringUtils.equals(otpCode, OTPCODE)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "验证码输入错误~");
        }

        // 保存注册用户
        UserModel userModel = new UserModel().builder().name(name).
                age(age).
                gender(new Byte(String.valueOf(gender.intValue()))).
                telephone(telephone).
                registerMode("byPhone").
                encrptPassword(this.encodeByMD5(password)).
                build();

        userService.register(userModel);
        return CommonReturnType.create(null);
    }

    private String encodeByMD5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        // MD5加密
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        // 加密字符串
        String newStr = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newStr;
    }

    /**
     * 登录
     *
     * @param telephone
     * @param password
     * @return
     * @throws Exception
     */
    @PostMapping(value = "login", consumes = {CONTENT_TYPE_FORMED})
    @ResponseBody
    public CommonReturnType login(@RequestParam(name = "telephone") String telephone,
                                  @RequestParam(name = "password") String password) throws Exception{

        //入参校验
        if (StringUtils.isEmpty(telephone)||StringUtils.isEmpty(password)) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //调用登录服务
        UserModel userModel = userService.validateLogin(telephone,this.encodeByMD5(password));
        System.out.println("3 ------------" + userModel);
        //将登录凭证加入到用户登录成功的session内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN",true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER",userModel);
//        System.out.println("user is: " + this.httpServletRequest.getSession().getAttribute("LOGIN_USER"));
        return CommonReturnType.create(null);
    }
}