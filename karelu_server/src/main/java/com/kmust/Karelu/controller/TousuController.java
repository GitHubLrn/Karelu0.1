package com.kmust.Karelu.controller;


import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.entity.Tousu;
import com.kmust.Karelu.service.ITousuService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Karelu
 * @since 2021-05-22
 */
@RestController
@RequestMapping("/tousu")
public class TousuController {

    @Autowired
    private ITousuService tousuService;

    @ApiOperation(value = "投诉")
    @PostMapping("/dotousu")
    public RespBean doTousu(@RequestBody Tousu tousu){
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(d);
        tousu.setCreatedate(date);
        tousu.setBedone("0");
        tousuService.save(tousu);
        return RespBean.sucess("投诉成功，请耐心等待处理~");
    }

}
