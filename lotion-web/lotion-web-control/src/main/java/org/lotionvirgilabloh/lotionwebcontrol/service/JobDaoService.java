package org.lotionvirgilabloh.lotionwebcontrol.service;

import org.lotionvirgilabloh.lotionwebcontrol.configuration.FeignMappingDefaultConfiguration;
import org.lotionvirgilabloh.lotionwebcontrol.service.impl.JobDaoServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@FeignClient(name = "lotion-dao-mysql", fallback = JobDaoServiceFallback.class, configuration = FeignMappingDefaultConfiguration.class)
@RequestMapping("job/")
public interface JobDaoService {

    @GetMapping(value = "{type}/getById")
    <T> List<T> getById(@PathVariable("type") String type, @RequestParam("id") Long id);

    @GetMapping(value = "{type}/getByJobname")
    <T> List<T> getByJobname(@PathVariable("type") String type, @RequestParam("jobname") String jobname);

    @GetMapping(value = "{type}/getAll")
    <T> List<T> getAll(@PathVariable("type") String type);

    @DeleteMapping("{type}/deleteByJobname/{jobname}")
    boolean deleteByJobname(@PathVariable("type") String type, @PathVariable("jobname") String jobname);

    @PostMapping(value = "{type}/save", consumes = "application/json")
    <T> boolean save(@PathVariable("type") String type, @RequestBody T job);

    @PostMapping(value = "{type}/updateByJobname", consumes = "application/json")
    <T> boolean updateByJobname(@PathVariable("type") String type, @RequestBody T job);
}
