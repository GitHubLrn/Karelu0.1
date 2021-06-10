package com.kmust.Karelu.service;

import com.kmust.Karelu.entity.DoGoodParam;
import com.kmust.Karelu.entity.Good;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kmust.Karelu.entity.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Karelu
 * @since 2021-05-18
 */
public interface IGoodService extends IService<Good> {

    RespBean doGood(DoGoodParam doGoodParam);
}
