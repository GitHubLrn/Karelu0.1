package com.kmust.Karelu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kmust.Karelu.entity.Comment;
import com.kmust.Karelu.entity.DoCommentParam;
import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.service.ICommentService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Karelu
 * @since 2021-05-20
 */
@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private ICommentService commentService;

    @ApiOperation(value = "删除评论")
    @PostMapping("/delComment")
    public RespBean delComment(@RequestParam Integer id){
        if(commentService.removeById(id)){
            commentService.remove(new QueryWrapper<Comment>().eq("fatherid",id));
            return RespBean.sucess("删除评论成功");
        }
        return RespBean.error("删除评论失败");
    }

    @ApiOperation(value = "发表评论")
    @PostMapping("/doComment")
    public RespBean doComment(@RequestBody DoCommentParam doCommentParam){
        if (doCommentParam.getCommentthing().contains("傻逼") || doCommentParam.getCommentthing().contains("死妈") || doCommentParam.getCommentthing().contains("狗日") || doCommentParam.getCommentthing().contains("妈逼")) return RespBean.error("评论包含违禁内容");

        return commentService.doComment(doCommentParam);
    }
}
