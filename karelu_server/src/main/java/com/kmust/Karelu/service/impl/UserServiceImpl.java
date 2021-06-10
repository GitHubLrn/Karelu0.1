package com.kmust.Karelu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kmust.Karelu.entity.*;
import com.kmust.Karelu.mapper.UserMapper;
import com.kmust.Karelu.mapper.UserRoleMapper;
import com.kmust.Karelu.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kmust.Karelu.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Karelu
 * @since 2021-04-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserRoleMapper userRoleMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private IFocusService focusService;
    @Autowired
    private ISignatureService signatureService;
    @Autowired
    UserDetailsService userDetailsService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtils;
    @Autowired
    private INoteService noteService;
    @Autowired
    private IAccountService accountService;

    @Override
    public User getAdminByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
    }

    @Override
    public RespBean signUp(UserSignUpParam userSignUpParam,HttpServletRequest request) {
        String kaptcha = (String)request.getSession().getAttribute("kaptcha");
        if (userSignUpParam.getCode() == null||!kaptcha.equals(userSignUpParam.getCode())){
            return RespBean.error("验证码输入错误，请重新输入");
        }if(null != userMapper.selectOne(new QueryWrapper<User>().eq("nickname",userSignUpParam.getNickname()))){
            return RespBean.error("昵称重复");
        }
        if(null != userMapper.selectOne(new QueryWrapper<User>().eq("username",userSignUpParam.getUsername()))){
            return RespBean.error("账号已存在");
        }else{
            User user = new User();
            //将密码进行加密操作
            String encodePassword = passwordEncoder.encode(userSignUpParam.getPassword());
            user.setNickName(userSignUpParam.getNickname());
            user.setUsername(userSignUpParam.getUsername());
            user.setIcon(userSignUpParam.getIcon());
            user.setPassword(encodePassword);
            user.setState(1);
            if(userSignUpParam.getIcon().length()>5){
                user.setIcon(userSignUpParam.getIcon());
            }else{
                user.setIcon("image/1.jpg");
            }
            UserRole userRole = new UserRole();
            userMapper.insert(user);
            userRole.setRoleId(1L);
            userRole.setUserId(user.getId());
            userRoleMapper.insert(userRole);
            Account account = new Account();
            account.setUid(new Integer(user.getId().toString()));
            account.setShengyu("100");
            accountService.save(account);
            return RespBean.sucess("注册成功");
        }

    }

    @Override
    public RespBean login(String userName, String password, String code, HttpServletRequest request) {
        if(null == userService.getAdminByUsername(userName)){
            return RespBean.error("账号不存在");
        }
        String kaptcha = (String)request.getSession().getAttribute("kaptcha");
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        if (code == null||!kaptcha.equals(code)){
            return RespBean.error("验证码输入错误，请重新输入");
        }else if (userDetails == null){
            return RespBean.error("该用户不存在");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            return RespBean.error("密码不正确");
        }
        User user1 = userService.getAdminByUsername(userName);
        if (user1.getState() == 0){
            return RespBean.error("该账号已被封禁");
        }
        if(!userDetails.isEnabled()){
            return RespBean.error("帐号已被禁用");
        }
        if (!userDetails.isEnabled()){
            return RespBean.error("账号被禁用，请联系管理员！");
        }
//        更新登录用户对象
        UsernamePasswordAuthenticationToken authenticationToken = new
                UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//        生成token
        String token = jwtTokenUtils.generateToken(userDetails);
        User user = userService.getAdminByUsername(userName);
        UserRole role = userRoleMapper.selectOne(new QueryWrapper<UserRole>().eq("userid",user.getId()));
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);
        tokenMap.put("role",role.getRoleId().toString());
        return RespBean.sucess("登录成功",tokenMap);
    }

    @Override
    public RespBean getCardInfo(String username, Principal principal) {
        CardInfo cardInfo = new CardInfo();
        User user = userService.getOne(new QueryWrapper<User>().eq("nickname",username));
        User nowuser = userService.getAdminByUsername(principal.getName());
        Long id = user.getId();
        List<Focus> list = focusService.list(new QueryWrapper<Focus>().eq("focusedid",id));
        if(null != signatureService.getOne(new QueryWrapper<Signature>().eq("uid",id))){
            cardInfo.setSignature(signatureService.getOne(new QueryWrapper<Signature>().eq("uid",id)).getSignature());
        }
        cardInfo.setBefocusedcount(list.size());
        cardInfo.setFocuscount(focusService.list(new QueryWrapper<Focus>().eq("userid",id)).size());
        cardInfo.setUn(user.getNickName());
        cardInfo.setUid(user.getId());
        String[] arr = new String[2];
        arr[0] = "";
        arr[1] = "关注";
        for (Focus f:list
             ) {
            if (f.getUserid().equals(new Integer(nowuser.getId().toString()))){
                arr[0] = "danger";
                arr[1] = "已关注";
            }
        }
        cardInfo.setIsfocused(arr);
        return RespBean.sucess("",cardInfo);
    }

    @Override
    public RespBean changePassword(ChangePassword changePassword) {
        User user = userService.getById(changePassword.getUid());
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        if(!passwordEncoder.matches(changePassword.getOldpassword(),userDetails.getPassword())){
            return RespBean.error("旧密码不正确");
        }else{
            String encodePassword = passwordEncoder.encode(changePassword.getNewpassword());
            user.setPassword(encodePassword);
            userService.updateById(user);
            return RespBean.sucess("更新密码成功");
        }
    }

    @Override
    public RespBean getMyFocus(Principal principal) {
        List<MyFocusShow> myFocusShows = new ArrayList<>();
        User user = userService.getAdminByUsername(principal.getName());
        List<Focus> foci = focusService.list(new QueryWrapper<Focus>().eq("userid",user.getId()));
        for (Focus f: foci
             ) {
            MyFocusShow myFocusShow = new MyFocusShow();
            myFocusShow.setUid(f.getFocusedid().toString());
            User user1 = userService.getById(f.getFocusedid());
            myFocusShow.setIcon(user1.getIcon());
            myFocusShow.setNickname(user1.getNickName());
            List<Focus> foci1 = focusService.list(new QueryWrapper<Focus>().eq("focusedid",user1.getId()));
            myFocusShow.setFans(foci1.size());
            List<Focus> foci2 = focusService.list(new QueryWrapper<Focus>().eq("userid",user1.getId()));
            myFocusShow.setFocus(foci2.size());
            List<Note> foci3 = noteService.list(new QueryWrapper<Note>().eq("userid",user1.getId()));
            myFocusShow.setNotes(foci3.size());
            if(null != signatureService.getOne(new QueryWrapper<Signature>().eq("uid",user1.getId())))
            {
                myFocusShow.setSignature(signatureService.getOne(new QueryWrapper<Signature>().eq("uid",user1.getId())).getSignature());
            }else{
                myFocusShow.setSignature("暂未设置签名");
            }
            myFocusShows.add(myFocusShow);
        }
        return RespBean.sucess("",myFocusShows);
    }

    @Override
    public RespBean getMyFans(Principal principal) {
        List<MyFansShow> myFansShows = new ArrayList<>();
        User user = userService.getAdminByUsername(principal.getName());
        List<Focus> foci = focusService.list(new QueryWrapper<Focus>().eq("focusedid",user.getId()));
        for (Focus f: foci
             ) {
            MyFansShow myFansShow = new MyFansShow();
            myFansShow.setUid(f.getUserid().toString());
            User user1 = userService.getById(f.getUserid());
            myFansShow.setNickname(user1.getNickName());
            myFansShow.setIcon(user1.getIcon());
            if(null != signatureService.getOne(new QueryWrapper<Signature>().eq("uid",user1.getId())))
            {
                myFansShow.setSignature(signatureService.getOne(new QueryWrapper<Signature>().eq("uid",user1.getId())).getSignature());
            }else{
                myFansShow.setSignature("暂未设置签名");
            }
            List<Focus> foci1 = focusService.list(new QueryWrapper<Focus>().eq("focusedid",user1.getId()));
            myFansShow.setFans(foci1.size());
            List<Focus> foci2 = focusService.list(new QueryWrapper<Focus>().eq("userid",user1.getId()));
            myFansShow.setFocus(foci2.size());
            List<Note> foci3 = noteService.list(new QueryWrapper<Note>().eq("userid",user1.getId()));
            myFansShow.setNotes(foci3.size());
            if(null != focusService.getOne(new QueryWrapper<Focus>().eq("userid",user.getId()).eq("focusedid",user1.getId()))){
                myFansShow.setFocustoo("点击取关");
            }else{
                myFansShow.setFocustoo("点击回关");
            }
            myFansShows.add(myFansShow);
        }
        return RespBean.sucess("",myFansShows);
    }

}
