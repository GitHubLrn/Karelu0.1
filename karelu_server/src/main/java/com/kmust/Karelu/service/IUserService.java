package com.kmust.Karelu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kmust.Karelu.entity.ChangePassword;
import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.entity.User;
import com.kmust.Karelu.entity.UserSignUpParam;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Karelu
 * @since 2021-04-23
 */
public interface IUserService extends IService<User> {

    User getAdminByUsername(String username);

    RespBean signUp(UserSignUpParam userSignUpParam,HttpServletRequest request);

    RespBean login(String userName, String password, String code, HttpServletRequest request);

    RespBean getCardInfo(String username, Principal principal);

    RespBean changePassword(ChangePassword changePassword);

    RespBean getMyFocus(Principal principal);

    RespBean getMyFans(Principal principal);
}
