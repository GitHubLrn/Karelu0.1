package com.kmust.Karelu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.entity.Signature;
import com.kmust.Karelu.mapper.SignatureMapper;
import com.kmust.Karelu.service.ISignatureService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Karelu
 * @since 2021-05-19
 */
@Service
public class SignatureServiceImpl extends ServiceImpl<SignatureMapper, Signature> implements ISignatureService {

    @Autowired
    private SignatureMapper signatureMapper;

    @Override
    public RespBean getSignature(Integer uid) {
        if(null != signatureMapper.selectOne(new QueryWrapper<Signature>().eq("uid",uid))){
            return RespBean.sucess("",signatureMapper.selectOne(new QueryWrapper<Signature>().eq("uid",uid)).getSignature());
        }else{
            return RespBean.sucess("","暂未设置签名");
        }

    }
}
