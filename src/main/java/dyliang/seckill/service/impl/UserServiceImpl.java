package dyliang.seckill.service.impl;

import dyliang.seckill.dao.UserDOMapper;
import dyliang.seckill.dao.UserPasswordDOMapper;
import dyliang.seckill.domain.UserDO;
import dyliang.seckill.domain.UserPasswordDO;
import dyliang.seckill.error.BusinessException;
import dyliang.seckill.error.EmBusinessError;
import dyliang.seckill.service.UserService;
import dyliang.seckill.service.model.UserModel;
import dyliang.seckill.utils.DOUtils;
import dyliang.seckill.validator.ValidationResult;
import dyliang.seckill.validator.ValidatorImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author dyliang
 * @Date 2020/8/4 10:28
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDOMapper userDOMapper;

    @Autowired
    private UserPasswordDOMapper passwordMapper;

    @Autowired
    private ValidatorImpl validator;


    private DOUtils utils = new DOUtils();

    @Override
    public UserModel findUserById(Integer id) {

        // 根据id获取对应的用于实体对象
        UserDO userDo = userDOMapper.selectByPrimaryKey(id);
        if (userDo == null){
            return null;
        }

        UserPasswordDO userPassword = passwordMapper.selectByPrimaryKey(id);
        return utils.convertFromDO(userDo, userPassword);
    }

    /**
     *  用户注册的业务层实现
     *
     * @param userModel
     * @throws Exception
     */
    @Override
    @Transactional
    public void register(UserModel userModel) throws Exception {
        // 如果注册用户为空，抛出异常
        if(userModel == null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //  如果注册信息不合法，无法完成注册，抛出异常
        ValidationResult result = validator.validate(userModel);
        if (result.isHasErrors()) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, result.getErrMsg());
        }

        UserDO user = this.utils.convertFromUserModel(userModel);
        //使用insertSelective方法时，如果待插入字段值为null，则不插入该字段，使用数据库的默认值
        userDOMapper.insertSelective(user);
        System.out.println("user is " + user);
        userModel.setId(user.getId());

        UserPasswordDO userPassword = this.utils.convertPasswordFromModel(userModel);
        System.out.println("userPassword is -- " + userPassword);
        passwordMapper.insertSelective(userPassword);

        return;
    }

    @Override
    public UserModel validateLogin(String telephone, String encrptPassword) throws Exception {
        //通过用户手机号获取用户信息
        UserDO user = userDOMapper.selectByTelephone(telephone);
        System.out.println("1 ---------" + user);
        if (user == null) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }

        UserPasswordDO userPassword = passwordMapper.selectByUserId(user.getId());
        UserModel userModel = this.utils.convertFromEntity(user,userPassword);
        System.out.println("2 ---------" + user);
        //将数据库中的密码与输入的密码进行比对
        if (!StringUtils.equals(encrptPassword,userModel.getEncrptPassword())) {
            throw new BusinessException(EmBusinessError.USER_LOGIN_FAIL);
        }
        return userModel;
    }


}
