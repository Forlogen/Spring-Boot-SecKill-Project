package dyliang.seckill.service;

import dyliang.seckill.service.model.UserModel;

/**
 * @Author dyliang
 * @Date 2020/8/4 10:28
 * @Version 1.0
 */
public interface UserService {

    UserModel findUserById(Integer id);

    void register(UserModel userModel) throws Exception;

    UserModel validateLogin(String telephone, String password) throws Exception;
}
