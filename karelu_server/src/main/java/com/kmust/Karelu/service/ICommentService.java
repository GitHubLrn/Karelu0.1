package com.kmust.Karelu.service;

import com.kmust.Karelu.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kmust.Karelu.entity.DoCommentParam;
import com.kmust.Karelu.entity.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Karelu
 * @since 2021-05-20
 */
public interface ICommentService extends IService<Comment> {

    RespBean doComment(DoCommentParam doCommentParam);

}
