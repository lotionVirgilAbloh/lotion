package org.lotionvirgilabloh.lotiondaomysql.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.lotionvirgilabloh.lotionbase.dto.OfflineJob;
import org.lotionvirgilabloh.lotionbase.dto.RealtimeJob;
import org.lotionvirgilabloh.lotiondaomysql.dao.OfflineJobRepository;
import org.lotionvirgilabloh.lotiondaomysql.dao.RealtimeJobRepository;
import org.lotionvirgilabloh.lotiondaomysql.entity.DBOfflineJob;
import org.lotionvirgilabloh.lotiondaomysql.entity.DBRealtimeJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/job/")
public class JobController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RealtimeJobRepository realtimeJobRepository;

    @Autowired
    private OfflineJobRepository offlineJobRepository;

    @RequestMapping("{type}/getById")
    public <T> List<T> getById(@PathVariable("type") String type, @RequestParam("id") Long id) {
        switch (type) {
            case "rt":
                Optional<DBRealtimeJob> optionalDbRealtimeJob = realtimeJobRepository.findById(id);
                DBRealtimeJob dbRealtimeJob = optionalDbRealtimeJob.get();
                RealtimeJob realtimeJob = new RealtimeJob(dbRealtimeJob.getJobname(), dbRealtimeJob.getType(), dbRealtimeJob.getStatus(), dbRealtimeJob.getDestination(), dbRealtimeJob.getUsername(), dbRealtimeJob.getPassword(), dbRealtimeJob.getStartsh(), dbRealtimeJob.getStopsh());
                List<RealtimeJob> realtimeJobs = new ArrayList<>();
                realtimeJobs.add(realtimeJob);
                return (List<T>) realtimeJobs;
            case "of":
                Optional<DBOfflineJob> optionalDbOfflineJob = offlineJobRepository.findById(id);
                DBOfflineJob dbOfflineJob = optionalDbOfflineJob.get();
                OfflineJob offlineJob = new OfflineJob(dbOfflineJob.getJobname(), dbOfflineJob.getType(), dbOfflineJob.getLastrun(), dbOfflineJob.getDestination(), dbOfflineJob.getUsername(), dbOfflineJob.getPassword(), dbOfflineJob.getStartsh());
                List<OfflineJob> offlineJobs = new ArrayList<>();
                offlineJobs.add(offlineJob);
                return (List<T>) offlineJobs;
            default:
                logger.error("未知请求类型，返回null");
                return null;
        }
    }


    @RequestMapping("{type}/getByJobname")
    public <T> List<T> getByJobname(@PathVariable("type") String type, String jobname) {
        switch (type) {
            case "rt":
                DBRealtimeJob dbRealtimeJob = realtimeJobRepository.getByJobname(jobname);
                //可能查询不到对象，则返回null
                if (dbRealtimeJob == null)
                    return  null;
                RealtimeJob realtimeJob = new RealtimeJob(dbRealtimeJob.getJobname(), dbRealtimeJob.getType(), dbRealtimeJob.getStatus(), dbRealtimeJob.getDestination(), dbRealtimeJob.getUsername(), dbRealtimeJob.getPassword(), dbRealtimeJob.getStartsh(), dbRealtimeJob.getStopsh());
                List<RealtimeJob> realtimeJobs = new ArrayList<>();
                realtimeJobs.add(realtimeJob);
                return (List<T>) realtimeJobs;
            case "of":
                DBOfflineJob dbOfflineJob = offlineJobRepository.getByJobname(jobname);
                if (dbOfflineJob == null)
                    return  null;
                OfflineJob offlineJob = new OfflineJob(dbOfflineJob.getJobname(), dbOfflineJob.getType(), dbOfflineJob.getLastrun(), dbOfflineJob.getDestination(), dbOfflineJob.getUsername(), dbOfflineJob.getPassword(), dbOfflineJob.getStartsh());
                List<OfflineJob> offlineJobs = new ArrayList<>();
                offlineJobs.add(offlineJob);
                return (List<T>) offlineJobs;
            default:
                logger.error("未知请求类型，返回null");
                return null;
        }
    }

    @DeleteMapping("{type}/deleteByJobname/{jobname}")
    public Boolean deleteByJobname(@PathVariable("type") String type, @PathVariable("jobname") String jobname) {
        switch (type) {
            case "rt":
                DBRealtimeJob dbRealtimeJob = realtimeJobRepository.getByJobname(jobname);
                realtimeJobRepository.deleteById(dbRealtimeJob.getId());
                return true;
            case "of":
                DBOfflineJob dbOfflineJob = offlineJobRepository.getByJobname(jobname);
                offlineJobRepository.deleteById(dbOfflineJob.getId());
                return true;
            default:
                logger.error("未知请求类型，返回false");
                return false;
        }
    }

    @RequestMapping(value = "{type}/updateByJobname", method = RequestMethod.POST)
    public <T> Boolean updateByJobname(@PathVariable("type") String type, @RequestBody T job) {
        switch (type) {
            case "rt":
                //由于无法解析泛型，返回的是LinkedHashMap类型，需要利用jackson进行反序列化
                RealtimeJob realtimeJob = (new ObjectMapper()).convertValue(job, RealtimeJob.class);
                DBRealtimeJob dbRealtimeJob = realtimeJobRepository.getByJobname(realtimeJob.getJobname());
                BeanUtils.copyProperties(realtimeJob, dbRealtimeJob);
                realtimeJobRepository.saveAndFlush(dbRealtimeJob);
                return true;
            case "of":
                OfflineJob offlineJob = (new ObjectMapper()).convertValue(job, OfflineJob.class);
                DBOfflineJob dbOfflineJob = offlineJobRepository.getByJobname(offlineJob.getJobname());
                BeanUtils.copyProperties(offlineJob, dbOfflineJob);
                offlineJobRepository.saveAndFlush(dbOfflineJob);
                return true;
            default:
                logger.error("未知请求类型，返回false");
                return false;
        }
    }

    @RequestMapping("{type}/getAll")
    public <T> List<T> getAll(@PathVariable("type") String type) {
        switch (type) {
            case "rt":
                List<DBRealtimeJob> dbRealtimeJobs = realtimeJobRepository.findAll();
                List<RealtimeJob> realtimeJobs = new ArrayList<>(dbRealtimeJobs.size());
                for (DBRealtimeJob dbRealtimeJob : dbRealtimeJobs) {
                    RealtimeJob realtimeJob = new RealtimeJob(dbRealtimeJob.getJobname(), dbRealtimeJob.getType(), dbRealtimeJob.getStatus(), dbRealtimeJob.getDestination(), dbRealtimeJob.getUsername(), dbRealtimeJob.getPassword(), dbRealtimeJob.getStartsh(), dbRealtimeJob.getStopsh());
                    realtimeJobs.add(realtimeJob);
                }
                return (List<T>) realtimeJobs;
            case "of":
                List<DBOfflineJob> dbOfflineJobs = offlineJobRepository.findAll();
                List<OfflineJob> offlineJobs = new ArrayList<>(dbOfflineJobs.size());
                for (DBOfflineJob dbOfflineJob : dbOfflineJobs) {
                    OfflineJob offlineJob = new OfflineJob(dbOfflineJob.getJobname(), dbOfflineJob.getType(), dbOfflineJob.getLastrun(), dbOfflineJob.getDestination(), dbOfflineJob.getUsername(), dbOfflineJob.getPassword(), dbOfflineJob.getStartsh());
                    offlineJobs.add(offlineJob);
                }
                return (List<T>) offlineJobs;
            default:
                logger.error("未知请求类型，返回null");
                return null;
        }
    }

    @RequestMapping(value = "{type}/save", method = RequestMethod.POST)
    public <T> boolean save(@PathVariable("type") String type, @RequestBody T job) {
        switch (type) {
            case "rt":
                DBRealtimeJob dbRealtimeJob = new DBRealtimeJob();
                //由于无法解析泛型，返回的是LinkedHashMap类型，需要利用jackson进行反序列化
                RealtimeJob realtimeJob = (new ObjectMapper()).convertValue(job, RealtimeJob.class);
                dbRealtimeJob.setJobname(realtimeJob.getJobname());
                dbRealtimeJob.setStatus(realtimeJob.getStatus());
                dbRealtimeJob.setUsername(realtimeJob.getUsername());
                dbRealtimeJob.setPassword(realtimeJob.getPassword());
                dbRealtimeJob.setStartsh(realtimeJob.getStartsh());
                dbRealtimeJob.setStopsh(realtimeJob.getStopsh());
                dbRealtimeJob.setType(realtimeJob.getType());
                dbRealtimeJob.setDestination(realtimeJob.getDestination());
                realtimeJobRepository.saveAndFlush(dbRealtimeJob);
                return true;
            case "of":
                DBOfflineJob dbOfflineJob = new DBOfflineJob();
                OfflineJob offlineJob = (new ObjectMapper()).convertValue(job, OfflineJob.class);
                dbOfflineJob.setJobname(offlineJob.getJobname());
                dbOfflineJob.setType(offlineJob.getType());
                dbOfflineJob.setDestination(offlineJob.getDestination());
                dbOfflineJob.setLastrun(offlineJob.getLastrun());
                dbOfflineJob.setUsername(offlineJob.getUsername());
                dbOfflineJob.setPassword(offlineJob.getPassword());
                dbOfflineJob.setStartsh(offlineJob.getStartsh());
                offlineJobRepository.saveAndFlush(dbOfflineJob);
                return true;
            default:
                logger.error("未知请求类型，返回false");
                return false;
        }
    }
}
