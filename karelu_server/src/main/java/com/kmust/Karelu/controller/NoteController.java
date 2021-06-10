package com.kmust.Karelu.controller;


import com.kmust.Karelu.entity.NoteParam;
import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.entity.RespPageBean;
import com.kmust.Karelu.entity.User;
import com.kmust.Karelu.service.INoteService;
import com.kmust.Karelu.service.IUserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Karelu
 * @since 2021-05-18
 */
@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private INoteService noteService;
    @Autowired
    private IUserService userService;

    @ApiOperation(value = "分页获取动态")
    @GetMapping("/getNotesByPage")
    public RespPageBean getNotes(@RequestParam(defaultValue = "1")Integer currentPage,
                                 @RequestParam(defaultValue = "10")Integer size,
                                 Principal principal){
        String name = principal.getName();
        User user = userService.getAdminByUsername(name);
        return noteService.getNotesByPage(currentPage,size,user.getId());
    }

    @ApiOperation(value = "分页获取近期最热动态")
    @GetMapping("/getNotesByPageByHot")
    public RespPageBean getNotesByHot(@RequestParam(defaultValue = "1")Integer currentPage,
                                 @RequestParam(defaultValue = "10")Integer size,
                                 Principal principal){
        String name = principal.getName();
        User user = userService.getAdminByUsername(name);
        return noteService.getNotesByPageByHot(currentPage,size,user.getId());
    }

    @ApiOperation(value = "获取所有动态")
    @GetMapping("/getAllNotes")
    public RespBean getAllNotes(){
        return RespBean.sucess("",noteService.list());
    }

    @ApiOperation(value = "写动态")
    @PostMapping("/writeNote")
    public RespBean writeNote(@RequestBody NoteParam noteParam){

        if (noteParam.getNote().contains("傻逼") || noteParam.getNote().contains("死妈") || noteParam.getNote().contains("狗日") || noteParam.getNote().contains("妈逼")) return RespBean.error("动态包含违禁内容");

        return noteService.doOutput(noteParam);
    }

    @ApiOperation(value = "删动态")
    @DeleteMapping("/delNote")
    public RespBean delNote(@RequestParam Integer id){
        if(null != noteService.getById(id)){
            noteService.removeById(id);
            return RespBean.sucess("删除动态成功~");
        }
        return RespBean.error("动态已经不存在了~");
    }

    @ApiOperation(value = "根据关注获取动态")
    @GetMapping("/getFocusNotesByPage")
    public RespPageBean getFocusNotesByPage(@RequestParam(defaultValue = "1")Integer currentPage,
                                        @RequestParam(defaultValue = "10")Integer size,
                                        Principal principal){
        String name = principal.getName();
        User user = userService.getAdminByUsername(name);
        return noteService.getFocusNotesByPage(currentPage,size,user.getId());
    }

    @ApiOperation(value = "查询包含内容的最新动态")
    @GetMapping("/getTargetNewNotesByPage")
    public RespPageBean getTargetNewNotesByPage(@RequestParam(defaultValue = "1")Integer currentPage,
                                                @RequestParam(defaultValue = "10")Integer size,
                                                @RequestParam(defaultValue = "") String target,
                                                Principal principal){
        String name = principal.getName();
        User user = userService.getAdminByUsername(name);
        return noteService.getTargetNewNotesByPage(currentPage,size,user.getId(),target);
    }

    @ApiOperation(value = "查询包含内容的热动态")
    @GetMapping("/getTargetHotNotesByPage")
    public RespPageBean getTargetHotNotesByPage(@RequestParam(defaultValue = "1")Integer currentPage,
                                                @RequestParam(defaultValue = "10")Integer size,
                                                @RequestParam(defaultValue = "") String target,
                                                Principal principal){
        String name = principal.getName();
        User user = userService.getAdminByUsername(name);
        return noteService.getTargetHotNotesByPage(currentPage,size,user.getId(),target);
    }

    @ApiOperation(value = "获取自己的动态")
    @GetMapping("/getMyNotes")
    public RespPageBean getMyNotes(@RequestParam(defaultValue = "1")Integer currentPage,
                                   @RequestParam(defaultValue = "10")Integer size,
                                   @RequestParam(defaultValue = "") String target,
                                   Principal principal){
        String name = principal.getName();
        User user = userService.getAdminByUsername(name);
        return noteService.getMyNotes(currentPage,size,user.getId(),target);
    }

}
