package org.lotionvirgilabloh.lotionwebcontrol.controller;

import org.lotionVirgilAbloh.lotionbase.dto.OfflineJob;
import org.lotionVirgilAbloh.lotionbase.dto.RealtimeJob;
import org.lotionvirgilabloh.lotionwebcontrol.configuration.LotionJsCHProperties;
import org.lotionvirgilabloh.lotionwebcontrol.entity.PagerModel;
import org.lotionvirgilabloh.lotionwebcontrol.service.JobDaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/jobdao/")
public class JobDaoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private JobDaoService jobDaoService;

    @Autowired
    private LotionJsCHProperties lotionJsCHProperties;

    @RequestMapping("{type}/getById")
    public <T> T getById(@PathVariable("type") String type, Long id) {
        logger.info("JobDaoController获取请求:/jobdao/" + type + "/getById?id=" + id.toString());
        List<T> t = jobDaoService.getById(type, id);
        if (t == null || t.size() == 0)
            return null;
        return t.get(0);
    }

    @RequestMapping("{type}/getByJobname")
    public <T> T getByJobname(@PathVariable("type") String type, String jobname) {
        logger.info("JobDaoController获取请求:/jobdao/" + type + "/getByJobname?id=" + jobname);
        List<T> t = jobDaoService.getByJobname(type, jobname);
        if (t == null || t.size() == 0)
            return null;
        return t.get(0);
    }

    @RequestMapping("{type}/getAll")
    public <T> PagerModel<T> getAll(@PathVariable("type") String type) {
        logger.info("JobDaoController获取请求:/jobdao/" + type + "/getAll");
        List<T> jobs = jobDaoService.getAll(type);
        if (jobs == null) {
            jobs = new ArrayList<>();
        }
        PagerModel<T> pagerModel = new PagerModel<>();
        pagerModel.setData(jobs);
        pagerModel.setRecordsFiltered(jobs.size());
        pagerModel.setRecordsTotal(jobs.size());
        return pagerModel;
    }

    @RequestMapping("{type}/deleteByJobname/{jobname}")
    public boolean deleteByJobname(@PathVariable("type") String type, @PathVariable("jobname") String jobname) {
        logger.info("JobDaoController获取请求:/jobdao/" + type + "/deleteByJobname/" + jobname);
        return jobDaoService.deleteByJobname(type, jobname);
    }

    @RequestMapping("{type}/save")
    public boolean save(@PathVariable("type") String type, HttpServletRequest request) {
        logger.info("JobDaoController获取请求:/jobdao/" + type + "/save");
        boolean flag;
        switch (type) {
            case "rt":
                //由于表单提交过来的对象不明确，需要利用HttpServletRequest.getParameter的方式获取属性以重建实体
                RealtimeJob realtimeJob = new RealtimeJob();
                realtimeJob.setJobname(request.getParameter("jobname"));
                realtimeJob.setDestination(request.getParameter("destination"));
                realtimeJob.setType(Integer.parseInt(request.getParameter("type")));
                //默认新创建的对象状态为"停止"即status=0
                realtimeJob.setStatus(0);
                realtimeJob.setUsername(request.getParameter("username"));
                realtimeJob.setPassword(request.getParameter("password"));
                realtimeJob.setStartsh(request.getParameter("startsh"));
                realtimeJob.setStopsh(request.getParameter("stopsh"));
                if (jobDaoService.getByJobname(type, (realtimeJob).getJobname()) == null) {
                    flag = jobDaoService.save(type, realtimeJob);
                } else {
                    flag = jobDaoService.updateByJobname(type, realtimeJob);
                }
                break;
            case "of":
                OfflineJob offlineJob = new OfflineJob();
                offlineJob.setJobname(request.getParameter("jobname"));
                offlineJob.setType(Integer.parseInt(request.getParameter("type")));
                //无上次提交时间
                offlineJob.setLastrun(null);
                offlineJob.setDestination(request.getParameter("destination"));
                offlineJob.setUsername(request.getParameter("username"));
                offlineJob.setPassword(request.getParameter("password"));
                offlineJob.setStartsh(request.getParameter("startsh"));
                if (jobDaoService.getByJobname(type, (offlineJob).getJobname()) == null) {
                    flag = jobDaoService.save(type, offlineJob);
                } else {
                    flag = jobDaoService.updateByJobname(type, offlineJob);
                }
                break;
            default:
                return false;
        }
        return flag;
    }
}
