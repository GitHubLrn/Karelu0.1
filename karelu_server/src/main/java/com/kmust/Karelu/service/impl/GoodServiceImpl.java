package com.kmust.Karelu.service.impl;

import com.kmust.Karelu.entity.DoGoodParam;
import com.kmust.Karelu.entity.Good;
import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.mapper.GoodMapper;
import com.kmust.Karelu.service.IGoodService;
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
public class GoodServiceImpl extends ServiceImpl<GoodMapper, Good> implements IGoodService {

    @Autowired
    private GoodMapper goodMapper;

    @Override
    public RespBean doGood(DoGoodParam doGoodParam) {
        Good good = new Good();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(d);
        good.setCreateDate(date);
        good.setNoteid(doGoodParam.getNoteid());
        good.setUserid(doGoodParam.getUserid());
        goodMapper.insert(good);
        return RespBean.sucess("点赞成功");
    }
}
