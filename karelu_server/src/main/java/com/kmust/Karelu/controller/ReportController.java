package com.kmust.Karelu.controller;


import com.kmust.Karelu.entity.Report;
import com.kmust.Karelu.entity.ReportParam;
import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.service.IReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Karelu
 * @since 2021-05-20
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private IReportService reportService;

    @ApiOperation(value = "举报")
    @PostMapping("/doReport")
    public RespBean doReport(@RequestBody ReportParam reportParam) {
        return reportService.doReport(reportParam);
    }

    @ApiOperation(value = "处理举报")
    @PutMapping("/setBeDone")
    public RespBean setBeDone(@RequestParam String reportid){
        Report r = reportService.getById(reportid);
        r.setBedone("1");
        reportService.updateById(r);
        return RespBean.sucess("处理完成");
    }



}
