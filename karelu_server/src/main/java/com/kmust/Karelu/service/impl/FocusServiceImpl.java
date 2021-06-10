package com.kmust.Karelu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kmust.Karelu.entity.Focus;
import com.kmust.Karelu.entity.FocusParam;
import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.mapper.FocusMapper;
import com.kmust.Karelu.service.IFocusService;
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
public class FocusServiceImpl extends ServiceImpl<FocusMapper, Focus> implements IFocusService {

    @Autowired
    private FocusMapper focusMapper;

    @Override
    public RespBean addFocus(FocusParam focusParam) {
        Focus focus = new Focus();
        focus.setUserid(focusParam.getUserid());
        focus.setFocusedid(focusParam.getFocusedid());
        if(focusMapper.selectOne(new QueryWrapper<Focus>().eq("userid",focusParam.getUserid()).eq("focusedid",focusParam.getFocusedid())) == null){
            focusMapper.insert(focus);
            return RespBean.sucess("关注成功");
        }
        return RespBean.error("已经关注过了~");
    }
}
