package com.kmust.Karelu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kmust.Karelu.entity.DoGoodParam;
import com.kmust.Karelu.entity.Good;
import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.service.IGoodService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Karelu
 * @since 2021-05-18
 */
@RestController
@RequestMapping("/good")
public class GoodController {

    @Autowired
    private IGoodService goodService;

    @ApiOperation(value = "点赞")
    @PostMapping("/dogood")
    public RespBean doGood(@RequestBody DoGoodParam doGoodParam){
        return goodService.doGood(doGoodParam);
    }

    @ApiOperation(value = "取消点赞")
    @DeleteMapping("/canclegood")
    public RespBean cancleGood(@RequestBody DoGoodParam doGoodParam){
        goodService.remove(new QueryWrapper<Good>().eq("noteid",doGoodParam.getNoteid()).eq("userid",doGoodParam.getUserid()));

        return RespBean.sucess("取消成功");
    }

}
