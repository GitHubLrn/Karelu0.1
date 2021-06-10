package com.kmust.Karelu.service.impl;

import com.kmust.Karelu.entity.Comment;
import com.kmust.Karelu.entity.DoCommentParam;
import com.kmust.Karelu.entity.Good;
import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.mapper.CommentMapper;
import com.kmust.Karelu.service.ICommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Karelu
 * @since 2021-05-18
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public RespBean doComment(DoCommentParam doCommentParam) {
        System.out.println(doCommentParam);
        Comment comment = new Comment();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(d);
        comment.setCreatedate(date);
        comment.setNoteid(doCommentParam.getNoteid());
        Integer uid = new Integer(doCommentParam.getUserid());
        comment.setUserid(uid);
        comment.setCommentthing(doCommentParam.getCommentthing());
        comment.setFatherid(doCommentParam.getFatherid());
        commentMapper.insert(comment);
        return RespBean.sucess("评论成功");
    }
}
