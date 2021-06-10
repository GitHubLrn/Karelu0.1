package com.kmust.Karelu.service;

import com.kmust.Karelu.entity.Focus;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kmust.Karelu.entity.FocusParam;
import com.kmust.Karelu.entity.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Karelu
 * @since 2021-05-19
 */
public interface IFocusService extends IService<Focus> {

    RespBean addFocus(FocusParam focusParam);
}
