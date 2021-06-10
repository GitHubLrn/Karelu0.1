package com.kmust.Karelu.service;

import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.entity.Signature;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Karelu
 * @since 2021-05-19
 */
public interface ISignatureService extends IService<Signature> {

    RespBean getSignature(Integer uid);
}
