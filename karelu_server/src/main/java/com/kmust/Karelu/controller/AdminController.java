package com.kmust.Karelu.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kmust.Karelu.entity.*;
import com.kmust.Karelu.service.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ISignatureService signatureService;
    @Autowired
    private IChatService chatService;
    @Autowired
    private ICommentService commentService;
    @Autowired
    private IGoodService goodService;
    @Autowired
    private INoteService noteService;
    @Autowired
    private IReportService reportService;
    @Autowired
    private ITousuService tousuService;
    @Autowired
    private IDanmuService danmuService;


    @ApiOperation("获取所有用户")
    @GetMapping("/getAllUser")
    public RespBean getAllUser(){
        List<User> userList = userService.list();
        List<AdminUser> adminUsers = new ArrayList<>();
        for (User u: userList
             ) {
            if (u.getId().toString().equals("55")) continue;;
            AdminUser a = new AdminUser();
            a.setId(u.getId().toString());
            a.setNickName(u.getNickName());
            if (u.getState() == 1){
                a.setState("已启用");
            }else{
                a.setState("已禁用");
            }
            a.setUsername(u.getUsername());
            a.setIcon(u.getIcon());
            if (null != signatureService.getOne(new QueryWrapper<Signature>().eq("uid",u.getId()))){
                a.setSignature(signatureService.getOne(new QueryWrapper<Signature>().eq("uid",u.getId())).getSignature());
            }
            adminUsers.add(a);
        }
        return RespBean.sucess("",adminUsers);
    }

    @ApiOperation("获取未处理的举报")
    @GetMapping("/getReport")
    public RespBean getReport(){
        List<ReportNotBeDone> res = new ArrayList<>();
        List<Report> reports = reportService.list(new QueryWrapper<Report>().eq("bedone",0));
        for (Report r:reports
             ) {
            ReportNotBeDone reportNotBeDone = new ReportNotBeDone();
            reportNotBeDone.setId(r.getReportid().toString());
            reportNotBeDone.setUid(r.getUid().toString());
            reportNotBeDone.setNoteid(r.getNoteid().toString());
            reportNotBeDone.setNuid(r.getNoteuserid().toString());
            if(null != noteService.getById(r.getNoteid())){
                reportNotBeDone.setNote(noteService.getById(r.getNoteid()).getNote());
                reportNotBeDone.setImg(noteService.getById(r.getNoteid()).getImages().split(";"));
            }else{
                continue;
            }
            res.add(reportNotBeDone);
        }
        return RespBean.sucess("",res);
    }

    @ApiOperation(value = "删除并封号")
    @PutMapping("/deleteAndFenghao")
    public RespBean deleteAndFenghao(@RequestBody fenghao fenghao){
        if(null != noteService.getById(fenghao.getNoteid())){
            noteService.removeById(fenghao.getNoteid());
        }
        User user = userService.getById(fenghao.getNuid());
        user.setState(0);
        userService.updateById(user);
        return RespBean.sucess("处理成功~");
    }

    @ApiOperation("/模块活跃")
    @GetMapping("/getModelActive")
    public RespBean getModelActive(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(d);
        Integer compare = new Integer(date.substring(8,10));
        List<Chat> chats = chatService.list();
        List<echartsEntity> list = new ArrayList<>();
        int temp = 0;
        for (Chat c:chats
             ) {
            Integer i = new Integer(c.getCreateTime().substring(8,10));
            if (i == 0 || i >= 26){
                temp++;
            }
        }
        echartsEntity echartsEntity = new echartsEntity();
        echartsEntity.setName("聊天");
        echartsEntity.setValue(temp);
        list.add(echartsEntity);
        temp = 0;
        List<Comment> comments = commentService.list();
        for (Comment c:comments
        ) {
            Integer i = new Integer(c.getCreatedate().substring(8,10));
            if (i == 0 || i >= 26){
                temp++;
            }
        }
        echartsEntity = new echartsEntity();
        echartsEntity.setName("评论");
        echartsEntity.setValue(temp);
        list.add(echartsEntity);
        temp = 0;
        List<Good> goods = goodService.list();
        for (Good c:goods
        ) {
            Integer i = new Integer(c.getCreateDate().substring(8,10));
            if (i == 0 || i >= 26){
                temp++;
            }
        }
        echartsEntity = new echartsEntity();
        echartsEntity.setName("点赞");
        echartsEntity.setValue(temp);
        list.add(echartsEntity);
        temp = 0;
        List<Note> notes = noteService.list();
        for (Note c:notes
        ) {
            Integer i = new Integer(c.getCreateDate().substring(8,10));
            if (i == 0 || i >= 26){
                temp++;
            }
        }
        echartsEntity = new echartsEntity();
        echartsEntity.setName("动态");
        echartsEntity.setValue(temp);
        list.add(echartsEntity);
        temp = 0;
        List<Report> reports = reportService.list();
        for (Report c:reports
        ) {
            Integer i = new Integer(c.getCreateDate().substring(8,10));
            if (i == 0 || i >= 26){
                temp++;
            }
        }
        echartsEntity = new echartsEntity();
        echartsEntity.setName("举报");
        echartsEntity.setValue(temp);
        list.add(echartsEntity);
        temp = 0;
        List<Tousu> tousus = tousuService.list();
        for (Tousu c:tousus
        ) {
            Integer i = new Integer(c.getCreatedate().substring(8,10));
            if (i == 0 || i >= 26){
                temp++;
            }
        }
        echartsEntity = new echartsEntity();
        echartsEntity.setName("投诉");
        echartsEntity.setValue(temp);
        list.add(echartsEntity);
        return RespBean.sucess("",list);
    }

    @ApiOperation("七日活跃")
    @GetMapping("/getNowActive")
    public RespBean getNowActive(){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(d);
        Integer compare = new Integer(date.substring(8,10)) - 6;
        List<Integer> res = new ArrayList<>();
        List<Chat> chats = chatService.list();
        res.add(0);
        res.add(0);
        res.add(0);
        res.add(0);
        res.add(0);
        res.add(0);
        res.add(0);
        for (Chat c:chats
        ) {
            Integer i = new Integer(c.getCreateTime().substring(8,10));
            if( i == 1){
                res.set(6,res.get(6)+1);
            }
            if (i == 26){
                res.set(0,res.get(0)+1);
            }
            if (i == 27){
                res.set(1,res.get(1)+1);
            }
            if (i == 28){
                res.set(2,res.get(2)+1);
            }
            if (i == 29){
                res.set(3,res.get(3)+1);
            }
            if (i == 30){
                res.set(4,res.get(4)+1);
            }
            if (i == 31){
                res.set(5,res.get(5)+1);
            }
        }
        List<Comment> comments = commentService.list();
        for (Comment c:comments
        ) {
            Integer i = new Integer(c.getCreatedate().substring(8,10));
            if( i == 1){
                res.set(6,res.get(6)+1);
            }
            if (i == 26){
                res.set(0,res.get(0)+1);
            }
            if (i == 27){
                res.set(1,res.get(1)+1);
            }
            if (i == 28){
                res.set(2,res.get(2)+1);
            }
            if (i == 29){
                res.set(3,res.get(3)+1);
            }
            if (i == 30){
                res.set(4,res.get(4)+1);
            }
            if (i == 31){
                res.set(5,res.get(5)+1);
            }
        }
        List<Good> goods = goodService.list();
        for (Good c:goods
        ) {
            Integer i = new Integer(c.getCreateDate().substring(8,10));
            if( i == 1){
                res.set(6,res.get(6)+1);
            }
            if (i == 26){
                res.set(0,res.get(0)+1);
            }
            if (i == 27){
                res.set(1,res.get(1)+1);
            }
            if (i == 28){
                res.set(2,res.get(2)+1);
            }
            if (i == 29){
                res.set(3,res.get(3)+1);
            }
            if (i == 30){
                res.set(4,res.get(4)+1);
            }
            if (i == 31){
                res.set(5,res.get(5)+1);
            }
        }
        List<Note> notes = noteService.list();
        for (Note c:notes
        ) {
            Integer i = new Integer(c.getCreateDate().substring(8,10));
            if( i == 1){
                res.set(6,res.get(6)+1);
            }
            if (i == 26){
                res.set(0,res.get(0)+1);
            }
            if (i == 27){
                res.set(1,res.get(1)+1);
            }
            if (i == 28){
                res.set(2,res.get(2)+1);
            }
            if (i == 29){
                res.set(3,res.get(3)+1);
            }
            if (i == 30){
                res.set(4,res.get(4)+1);
            }
            if (i == 31){
                res.set(5,res.get(5)+1);
            }
        }
        List<Report> reports = reportService.list();
        for (Report c:reports
        ) {
            Integer i = new Integer(c.getCreateDate().substring(8,10));
            if( i == 1){
                res.set(6,res.get(6)+1);
            }
            if (i == 26){
                res.set(0,res.get(0)+1);
            }
            if (i == 27){
                res.set(1,res.get(1)+1);
            }
            if (i == 28){
                res.set(2,res.get(2)+1);
            }
            if (i == 29){
                res.set(3,res.get(3)+1);
            }
            if (i == 30){
                res.set(4,res.get(4)+1);
            }
            if (i == 31){
                res.set(5,res.get(5)+1);
            }
        }
        List<Tousu> tousus = tousuService.list();
        for (Tousu c:tousus
        ) {
            Integer i = new Integer(c.getCreatedate().substring(8,10));
            if( i == 1){
                res.set(6,res.get(6)+1);
            }
            if (i == 26){
                res.set(0,res.get(0)+1);
            }
            if (i == 27){
                res.set(1,res.get(1)+1);
            }
            if (i == 28){
                res.set(2,res.get(2)+1);
            }
            if (i == 29){
                res.set(3,res.get(3)+1);
            }
            if (i == 30){
                res.set(4,res.get(4)+1);
            }
            if (i == 31){
                res.set(5,res.get(5)+1);
            }
        }
        return RespBean.sucess("",res);
    }

    @ApiOperation(value = "获取未处理的投诉")
    @GetMapping("/getNotBeDoneTousu")
    public RespBean getNotBeDoneTousu(){
        List<Tousu> tousus = tousuService.list(new QueryWrapper<Tousu>().eq("bedone",0));
        return RespBean.sucess("",tousus);
    }

    @ApiOperation(value = "无视投诉")
    @PutMapping("/setTousuDone")
    public RespBean setTousuDone(@RequestParam String tousuid){
        if(null != tousuService.getById(tousuid)){
            Tousu tousu = tousuService.getById(tousuid);
            tousu.setBedone("1");
            tousuService.updateById(tousu);
        }
        return RespBean.sucess("");
    }

    @ApiOperation(value = "搜索弹幕")
    @GetMapping("/getTargetDanmu")
    public RespBean getTargetDanmu(@RequestParam String target){
        List<Danmu> danmus = danmuService.list();
        List<Danmu> res = new ArrayList<>();
        for (Danmu d:danmus
             ) {
            if (d.getDanmu().contains(target)) res.add(d);
        }
        return RespBean.sucess("",res);
    }

    @ApiOperation(value = "封号")
    @PutMapping("/fengdanmuhao")
    private RespBean fengdanmuhao(@RequestParam String targetUid){
        User user = userService.getById(targetUid);
        if(user.getState() == 0){
            return RespBean.error("该账号已封禁");
        }
        user.setState(0);
        userService.updateById(user);
        return RespBean.sucess("封禁成功");
    }
}
