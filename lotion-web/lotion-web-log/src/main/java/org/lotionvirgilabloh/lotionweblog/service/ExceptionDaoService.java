package org.lotionvirgilabloh.lotionweblog.service;

import org.lotionvirgilabloh.lotionbase.dto.DatatablesReturn;
import org.lotionvirgilabloh.lotionbase.dto.FormattedException;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "lotion-dao-mongo")
@RequestMapping("fmtexception")
public interface ExceptionDaoService {

    @GetMapping("findAll")
    List<FormattedException> findAll();

    @GetMapping("findAllPage")
    DatatablesReturn<FormattedException> findAllPage(@RequestParam("whichPage") int whichPage, @RequestParam("pageLimit") int pageLimit);

    @GetMapping("findById")
    FormattedException findById(@RequestParam("id") String id);

    @PostMapping(value = "insert", consumes = "application/json")
    FormattedException insert(@RequestBody FormattedException fe);

    @DeleteMapping("deleteById/{id}")
    boolean deleteById(@PathVariable("id") String id);
}