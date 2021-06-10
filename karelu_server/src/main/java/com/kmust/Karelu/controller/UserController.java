package com.kmust.Karelu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kmust.Karelu.entity.*;
import com.kmust.Karelu.mapper.UserMapper;
import com.kmust.Karelu.service.ISignatureService;
import com.kmust.Karelu.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Karelu
 * @since 2021-04-21
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Value("${file.upload.path}")
    private String path;

    @Autowired
    private IUserService userService;

    @Autowired
    private ISignatureService signatureService;

    @Autowired
    private UserMapper userMapper;

    @ApiOperation(value = "获取个人卡片信息")
    @GetMapping("/getCardInfo")
    public RespBean getCardInfo(@RequestParam String username,Principal principal){
        return userService.getCardInfo(username,principal);
    }

    @ApiOperation(value = "登陆")
    @PostMapping("/logIn")
    public RespBean logIn(@RequestBody UserLoginParam userLoginParam, HttpServletRequest request){
        return userService.login(userLoginParam.getUsername(),userLoginParam.getPassword(),userLoginParam.getCode(),request);
    }


    @ApiOperation(value = "注册")
    @PostMapping("/signUp")
    public RespBean signUp(@RequestBody UserSignUpParam userSignUpParam,HttpServletRequest request){
        if(userSignUpParam.getNickname().equals("")) return RespBean.error("昵称不能为空");
        return userService.signUp(userSignUpParam,request);
    }


    @ApiOperation(value = "登出")
    @GetMapping("/logOut")
    public RespBean logOut() {
        return RespBean.sucess("注销成功！");
    }

    @ApiOperation(value = "获取昵称")
    @GetMapping("/getUserName")
    public RespBean getUserName(Principal principal){
        String username = principal.getName();
        User user = userService.getAdminByUsername(username);
        return RespBean.sucess(null,user.getNickName());
    }

    @GetMapping(value = "/getIcon")
    @ApiOperation(value = "获取头像")
    public RespBean getIcon(Principal principal) {
        User user = userService.getAdminByUsername(principal.getName());
        String url = user.getIcon();
        return RespBean.sucess("",url);
    }

    @GetMapping(value = "/getUser")
    @ApiOperation(value = "获取用户信息")
    public RespBean getUser(Principal principal){
        return RespBean.sucess("",userService.getAdminByUsername(principal.getName()));
    }

    @PutMapping(value = "/updateUser")
    @ApiOperation(value = "更新用户信息")
    public RespBean updateUser(@RequestBody User user){
        userService.updateById(user);
        return RespBean.sucess("更新成功");
    }

    @ResponseBody
    @PostMapping("/uploadPicture")
    public RespBean uploadPicture(@RequestParam("file") MultipartFile file, HttpServletRequest request ,Principal principal){
        //获取文件在服务器的储存位置
        File filePath = new File(path);
        if(!filePath.exists() && !filePath.isDirectory()){
            filePath.mkdir();
        }
        //获取原始文件名称（包括格式）
        String originalFileName = file.getOriginalFilename();

        //获取文件类型，以最后一个‘.’为标识
        String type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);

        //获取文件名称（不包含格式）
        String name = originalFileName.substring(0,originalFileName.lastIndexOf("."));
        //设置文件新名称：当前事件+文件名称（不包含格式）
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String date = sdf.format(d);
        int a = (int)(Math.random()*100000);
        String fileName = date + a + "." +type;

        //在指定路径下创建文件
        File targetFile = new File(path,fileName);
        //将文件保存到服务器指定位置
        try{
            file.transferTo(targetFile);
            return RespBean.sucess("上传头像成功",fileName);

        }catch (IOException e){
            e.printStackTrace();
        }
        return RespBean.error("上传头像失败");
    }

    @ApiOperation(value = "换头像")
    @PostMapping("/changeicon")
    public RespBean changeicon(@RequestBody IconUpload iconUpload){

        User user = userService.getById(iconUpload.getUid());
        user.setIcon(iconUpload.getUrl());
        userService.updateById(user);
        return RespBean.sucess("更换头像成功~");
    }

    @ApiOperation(value = "换头像")
    @PostMapping("/changeic")
    public RespBean changeic(@RequestBody IconUpload iconUpload){

        User user = userService.getById(iconUpload.getUid());
        user.setIcon(iconUpload.getUrl());
        userService.updateById(user);
        return RespBean.sucess("初始化头像成功");
    }

    @ApiOperation(value = "换昵称")
    @PostMapping("/changenickname")
    public RespBean changeNickname(@RequestBody NickNameUpload nickNameUpload){
        if (nickNameUpload.getName().contains("傻逼") || nickNameUpload.getName().contains("死妈") || nickNameUpload.getName().contains("狗日") || nickNameUpload.getName().contains("妈逼")) return RespBean.error("昵称包含违禁内容");
        if(null != userMapper.selectOne(new QueryWrapper<User>().eq("nickname",nickNameUpload.getName()))){
            return RespBean.error("昵称重复");
        }else{
            User user = userService.getById(nickNameUpload.getUid());
            user.setNickName(nickNameUpload.getName());
            userService.updateById(user);
            return RespBean.sucess("更换昵称成功~");
        }
    }

    @ApiOperation(value = "换昵称")
    @PostMapping("/changenick")
    public RespBean changeNick(@RequestBody NickNameUpload nickNameUpload){
        if(null != userMapper.selectOne(new QueryWrapper<User>().eq("nickname",nickNameUpload.getName()))){
            return RespBean.error("已是初始昵称");
        }else{
            User user = userService.getById(nickNameUpload.getUid());
            user.setNickName(nickNameUpload.getName());
            userService.updateById(user);
            return RespBean.sucess("初始化成功");
        }
    }

    @ApiOperation(value = "改签名")
    @PostMapping("/changeSignature")
    public RespBean changeSignature(@RequestBody SignatureUpdate signatureUpdate) {
        if (signatureUpdate.getSignature().contains("傻逼") || signatureUpdate.getSignature().contains("死妈") || signatureUpdate.getSignature().contains("狗日") || signatureUpdate.getSignature().contains("妈逼")) return RespBean.error("签名包含违禁内容");
        if (signatureUpdate.getSignature().length() > 50) return RespBean.error("签名内容过长");
        if (null != signatureService.getOne(new QueryWrapper<Signature>().eq("uid", signatureUpdate.getUid()))) {
            Signature signature = signatureService.getOne(new QueryWrapper<Signature>().eq("uid", signatureUpdate.getUid()));
            signature.setSignature(signatureUpdate.getSignature());
            signatureService.updateById(signature);
        } else {
            Signature signature = new Signature();
            signature.setUid(signatureUpdate.getUid());
            signature.setSignature(signatureUpdate.getSignature());
            signatureService.save(signature);
        }
        return RespBean.sucess("更新签名成功");
    }

    @ApiOperation(value = "改签名")
    @PostMapping("/changeSign")
    public RespBean changeSign(@RequestBody SignatureUpdate signatureUpdate){
        if (null != signatureService.getOne(new QueryWrapper<Signature>().eq("uid", signatureUpdate.getUid()))) {
            Signature signature = signatureService.getOne(new QueryWrapper<Signature>().eq("uid", signatureUpdate.getUid()));
            signature.setSignature(signatureUpdate.getSignature());
            signatureService.updateById(signature);
        } else {
            Signature signature = new Signature();
            signature.setUid(signatureUpdate.getUid());
            signature.setSignature(signatureUpdate.getSignature());
            signatureService.save(signature);
        }
        return RespBean.sucess("重置签名成功");

    }

    @ApiOperation(value = "改密码")
    @PostMapping("/changePassword")
    public RespBean changePassword(@RequestBody ChangePassword changePassword){
        return userService.changePassword(changePassword);
    }

    @ApiOperation(value = "我关注的")
    @GetMapping("/getMyFocus")
    public RespBean getMyFocus(Principal principal){
        return userService.getMyFocus(principal);
    }

    @ApiOperation(value = "关注我的")
    @GetMapping("/getMyFans")
    private RespBean getMyFans(Principal principal){
        return userService.getMyFans(principal);
    }

    @ApiOperation(value = "封号")
    @PutMapping("/fenghao")
    private RespBean fenghao(@RequestParam String targetUid){
        User user = userService.getById(targetUid);
        if(user.getState() == 0){
            return RespBean.error("该账号已封禁");
        }
        user.setState(0);
        userService.updateById(user);
        return RespBean.sucess("封禁成功");
    }

    @ApiOperation(value = "解封")
    @PutMapping("/jiefeng")
    private RespBean jiefeng(@RequestParam String targetUid){
        User user = userService.getById(targetUid);
        if(user.getState() == 1){
            return RespBean.error("该账号未被封禁");
        }
        user.setState(1);
        userService.updateById(user);
        return RespBean.sucess("已解封");
    }

}
