package com.kmust.Karelu.controller;


import com.kmust.Karelu.entity.Danmu;
import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.service.IDanmuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Karelu
 * @since 2021-05-21
 */
@RestController
@RequestMapping("/danmu")
public class DanmuController {

    @Autowired
    private IDanmuService danmuService;

    @ApiOperation(value = "发弹幕")
    @PostMapping("/writeDanmu")
    public RespBean writeDanmu(@RequestBody Danmu danmu){
        if (danmu.getDanmu().contains("傻逼") || danmu.getDanmu().contains("死妈") || danmu.getDanmu().contains("狗日") || danmu.getDanmu().contains("妈逼")) return RespBean.error("弹幕包含违禁内容");
        danmuService.save(danmu);
        return RespBean.sucess("发送成功");
    }

    @ApiOperation(value = "获取弹幕")
    @GetMapping("/getDanmu")
    public RespBean getDanmu(Principal principal){
        return danmuService.getDanmu(principal);
    }

}
