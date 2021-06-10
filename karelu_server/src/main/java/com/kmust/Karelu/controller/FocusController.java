package com.kmust.Karelu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kmust.Karelu.entity.Focus;
import com.kmust.Karelu.entity.FocusParam;
import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.service.IFocusService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Karelu
 * @since 2021-05-19
 */
@RestController
@RequestMapping("/focus")
public class FocusController {

    @Autowired
    private IFocusService focusService;

    @ApiOperation(value = "关注")
    @PostMapping("/addFocus")
    public RespBean addFocus(@RequestBody FocusParam focusParam){
        return focusService.addFocus(focusParam);
    }

    @ApiOperation(value = "取关")
    @PostMapping("/delFocus")
    public RespBean delFocus(@RequestBody FocusParam focusParam){
        focusService.remove(new QueryWrapper<Focus>().eq("userid",focusParam.getUserid()).eq("focusedid",focusParam.getFocusedid()));
        return RespBean.sucess("取关成功~");
    }

}
