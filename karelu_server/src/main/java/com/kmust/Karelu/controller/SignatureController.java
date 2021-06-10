package com.kmust.Karelu.controller;


import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.service.ISignatureService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/signature")
public class SignatureController {

    @Autowired
    private ISignatureService signatureService;

    @ApiOperation(value = "根据uid获取签名")
    @GetMapping("/getSignature")
    public RespBean getSignature(@RequestParam Integer uid){
        return signatureService.getSignature(uid);
    }

}
