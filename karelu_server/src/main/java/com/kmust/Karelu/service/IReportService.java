package com.kmust.Karelu.service;

import com.kmust.Karelu.entity.Report;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kmust.Karelu.entity.ReportParam;
import com.kmust.Karelu.entity.RespBean;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Karelu
 * @since 2021-05-20
 */
public interface IReportService extends IService<Report> {

    RespBean doReport(ReportParam reportParam);
}
