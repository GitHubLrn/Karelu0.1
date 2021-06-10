package com.kmust.Karelu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kmust.Karelu.entity.*;
import com.kmust.Karelu.service.IChatService;
import com.kmust.Karelu.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Karelu
 * @since 2021-05-24
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private IChatService chatService;
    @Autowired
    private IUserService userService;

    @ApiOperation("发消息")
    @PostMapping("/doChat")
    public RespBean doChat(@RequestBody DoChatParam doChatParam,Principal principal){
        User user = userService.getAdminByUsername(principal.getName());
        Chat chat = new Chat();
        chat.setChatthing(doChatParam.getChatthing());
        chat.setUsersentid(new Integer(user.getId().toString()));
        chat.setUsergetid(new Integer(doChatParam.getUid()));
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(d);
        chat.setCreateTime(date);
        chatService.save(chat);
        Chat chat1 = chatService.getOne(new QueryWrapper<Chat>().eq("usersentid",chat.getUsersentid()).eq("usergetid",chat.getUsergetid()).eq("createTime",chat.getCreateTime()).eq("chatthing",chat.getChatthing()));
        return RespBean.sucess("",chat1);

    }

    @ApiOperation("获取未接收的新消息提醒")
    @GetMapping("/getnewchat")
    public RespBean getNewChat(Principal principal){
        List<Chat> chatList = chatService.list(new QueryWrapper<Chat>().eq("usergetid",userService.getAdminByUsername(principal.getName()).getId()));
        List<Chat> chats = chatService.list(new QueryWrapper<Chat>().eq("usersentid",userService.getAdminByUsername(principal.getName()).getId()));
        HashMap<String, NewChat> res = new HashMap<>();
        for (Chat a: chatList
             ) {
            User user = userService.getById(a.getUsersentid());
            NewChat chat = new NewChat();
            if(!res.containsKey(user.getNickName())){
                chat.setIcon(user.getIcon());
                chat.setNickname(user.getNickName());
                if(a.getBedone() == 0){
                    chat.setSize(1);
                }
                chat.setUid(user.getId().toString());
                res.put(user.getNickName(),chat);
            }else{
                chat = res.get(user.getNickName());
                int size = chat.getSize();
                if(a.getBedone() == 0){
                    chat.setSize(size+1);
                }
            }
        }
        for (Chat a: chats
        ) {
            User user = userService.getById(a.getUsergetid());
            NewChat chat = new NewChat();
            if(!res.containsKey(user.getNickName())){
                chat.setIcon(user.getIcon());
                chat.setNickname(user.getNickName());
                chat.setSize(0);
                chat.setUid(user.getId().toString());
                res.put(user.getNickName(),chat);
            }else{
                    continue;
                }
            }
        List<NewChat> newChats = new ArrayList<>();
        for (Map.Entry<String, NewChat> entry : res.entrySet()) {
            newChats.add(entry.getValue());
        }
        return RespBean.sucess("",newChats);
    }

    @ApiOperation("获取聊天记录")
    @GetMapping("/getChatRemember")
    public RespBean getChatRemember(@RequestParam String uid,Principal principal){
        List<Chat> chats1 = chatService.list(new QueryWrapper<Chat>().eq("usersentid",uid).eq("usergetid",userService.getAdminByUsername(principal.getName()).getId()));
        List<Chat> chats2 = chatService.list(new QueryWrapper<Chat>().eq("usersentid",userService.getAdminByUsername(principal.getName()).getId()).eq("usergetid",uid));
        for (Chat c:chats1
             ) {
            chats2.add(c);
        }
        Collections.sort(chats2, new Comparator<Chat>() {
            @Override
            public int compare(Chat o1, Chat o2) {
                String temp = o1.getCreateTime();
                String target1 = temp.replace("-","").replace(" ","").replace(":","");
                temp = o2.getCreateTime();
                String target2 = temp.replace("-","").replace(" ","").replace(":","");
                long l1 = Long.parseLong(target1);
                long l2 = Long.parseLong(target2);;
                if (l1 < l2) return -1;
                else return 1;
            }
        });
        List<Chat> chats3 = new ArrayList<>();
        for (Chat c: chats1
             ) {
            if (c.getBedone() == 0){
                c.setBedone(1);
                chats3.add(c);
            }
        }
        chatService.updateBatchById(chats3);
        return RespBean.sucess("",chats2);
    }

    @ApiOperation("获取未接收的消息数")
    @GetMapping("/getNotBeDoneChatCount")
    public RespBean getNotBeDoneChatCount(Principal principal){
        User user = userService.getAdminByUsername(principal.getName());
        List<Chat> chats = chatService.list(new QueryWrapper<Chat>().eq("usergetid",user.getId()).eq("bedone",0));
        return RespBean.sucess("",chats.size());
    }

    @ApiOperation("获取所有聊天记录")
    @GetMapping("/getAllMemory")
    public RespBean getAllMemory(Principal principal){
        List<Chat> chats1 = chatService.list(new QueryWrapper<Chat>().eq("usergetid",userService.getAdminByUsername(principal.getName()).getId()));
        List<Chat> chats2 = chatService.list(new QueryWrapper<Chat>().eq("usersentid",userService.getAdminByUsername(principal.getName()).getId()));
        HashMap<String,List<Chat>> res = new HashMap<>();
        for(int i = 0 ; i < 200 ; i ++){
            String temp = String.valueOf(i);
            res.put(temp,new ArrayList<Chat>());
        }
        for (Chat c: chats1){
            res.get(c.getUsersentid().toString()).add(c);
        }
        for (Chat c: chats2
        ) {
            try{
                res.get(c.getUsergetid().toString()).add(c);
            }catch (Exception e){
                ArrayList<Chat> arr = new ArrayList<>();
                arr.add(c);
                res.put(c.getUsergetid().toString(),arr);
            }
        }
        for (Map.Entry<String,List<Chat>> temp:res.entrySet()
             ) {
            Collections.sort(temp.getValue(), new Comparator<Chat>() {
                @Override
                public int compare(Chat o1, Chat o2) {
                    String temp = o1.getCreateTime();
                    String target1 = temp.replace("-","").replace(" ","").replace(":","");
                    temp = o2.getCreateTime();
                    String target2 = temp.replace("-","").replace(" ","").replace(":","");
                    long l1 = Long.parseLong(target1);
                    long l2 = Long.parseLong(target2);;
                    if (l1 < l2) return -1;
                    else return 1;
                }
            });
        }
        return RespBean.sucess("",res);
    }

    @ApiOperation(value = "获取未接收的消息")
    @GetMapping("/getNeedToDone")
    public RespBean getNeedToDone(Principal principal){
        User user = userService.getAdminByUsername(principal.getName());
        List<Chat> chats = chatService.list(new QueryWrapper<Chat>().eq("usergetid",user.getId()));
        List<Integer> res = new ArrayList<>();
        for(int i = 0 ; i < 200 ; i ++){
            int temp = i;
            res.add(0);
        }
        for (Chat c: chats
             ) {
            if (c.getBedone() == 0) {
                res.set(c.getUsersentid(),res.get(c.getUsersentid()) + 1);
            }
        }
        return RespBean.sucess("",res);
    }

    @ApiOperation(value = "已阅")
    @PutMapping("/done/{userid}")
    public RespBean done(@PathVariable String userid,Principal principal){
        User user = userService.getAdminByUsername(principal.getName());
        List<Chat> chats = chatService.list(new QueryWrapper<Chat>().eq("usersentid",userid).eq("usergetid",user.getId()));
        for (Chat c:chats
             ) {
            if (c.getBedone() == 0){
                c.setBedone(1);
                chatService.updateById(c);
            }
        }
        return RespBean.sucess("");
    }


}
