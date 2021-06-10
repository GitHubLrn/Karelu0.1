package com.kmust.Karelu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kmust.Karelu.entity.Note;
import com.kmust.Karelu.entity.Report;
import com.kmust.Karelu.entity.ReportParam;
import com.kmust.Karelu.entity.RespBean;
import com.kmust.Karelu.mapper.NoteMapper;
import com.kmust.Karelu.mapper.ReportMapper;
import com.kmust.Karelu.service.IReportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rabbitmq.client.Return;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Karelu
 * @since 2021-05-20
 */
@Service
public class ReportServiceImpl extends ServiceImpl<ReportMapper, Report> implements IReportService {

    @Autowired
    private ReportMapper reportMapper;
    @Autowired
    private NoteMapper noteMapper;

    @Override
    public RespBean doReport(ReportParam reportParam) {
        Report report = new Report();
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(d);
        report.setBedone("0");
        report.setCreateDate(date);
        report.setNoteid(reportParam.getNoteid());
        Note note = noteMapper.selectById(reportParam.getNoteid());
        report.setNoteuserid(note.getUserid());
        report.setUid(reportParam.getUid());
        List<Report> reports = reportMapper.selectList(new QueryWrapper<Report>().eq("uid",reportParam.getUid()).eq("noteid",reportParam.getNoteid()));
        if(reports.size() >= 1){
            return RespBean.error("你已经举报过了，耐心等待处理吧~");
        }
        reportMapper.insert(report);
        return RespBean.sucess("举报成功");
    }
}
