package com.kmust.Karelu.service;

import com.kmust.Karelu.entity.Danmu;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kmust.Karelu.entity.RespBean;

import java.security.Principal;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Karelu
 * @since 2021-05-21
 */
public interface IDanmuService extends IService<Danmu> {

    RespBean getDanmu(Principal principal);
}
