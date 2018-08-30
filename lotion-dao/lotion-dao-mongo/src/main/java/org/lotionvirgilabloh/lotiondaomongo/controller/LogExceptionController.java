package org.lotionvirgilabloh.lotiondaomongo.controller;

import org.lotionvirgilabloh.lotionbase.dto.DatatablesReturn;
import org.lotionvirgilabloh.lotionbase.dto.FormattedException;
import org.lotionvirgilabloh.lotiondaomongo.entity.MongoFormattedException;
import org.lotionvirgilabloh.lotiondaomongo.repository.FERepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RequestMapping("fmtexception")
@RestController
public class LogExceptionController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public FERepository feRepository;

    @RequestMapping("findAll")
    public List<FormattedException> findAll() {
        logger.info("LogExceptionController获取请求:/fmtexception/findAll");
        List<FormattedException> fes = new LinkedList<>();
        feRepository.findAll().forEach(mfe -> fes.add(mfe2fe(mfe)));
        return fes;
    }

    @RequestMapping("findAllPage")
    public DatatablesReturn<FormattedException> findAllPage(int whichPage, int pageLimit) {
        logger.info("LogExceptionController获取请求:/fmtexception/findAllPage?whichPage=" + whichPage + "&pageLimit=" + pageLimit);
        Page<MongoFormattedException> mfes = feRepository.findAll(PageRequest.of(whichPage, pageLimit));
        List<FormattedException> fes = new ArrayList<>();
        mfes.forEach(mfe -> {
            FormattedException fe = mfe2fe(mfe);
            fes.add(fe);
        });
        DatatablesReturn<FormattedException> datatablesReturn = new DatatablesReturn<>();
        datatablesReturn.setData(fes);
        datatablesReturn.setRecordsFiltered(mfes.getSize());
        datatablesReturn.setRecordsTotal((int) mfes.getTotalElements());
        return datatablesReturn;
    }

    @RequestMapping("findById")
    public FormattedException findById(String id) {
        logger.info("LogExceptionController获取请求:/fmtexception/findById?id=" + id);
        Optional<MongoFormattedException> optional = feRepository.findById(id);
        FormattedException fe = null;
        if (optional.isPresent()) {
            fe = mfe2fe(optional.get());
        }
        return fe;
    }

    @RequestMapping(value = "insert", method = RequestMethod.POST)
    public FormattedException insert(@RequestBody FormattedException fe) {
        logger.info("LogExceptionController获取请求:/fmtexception/insert");
        if (feRepository.existsById(String.valueOf(fe.getExceptionID()))) {
            logger.info("id" + Integer.toString(fe.getExceptionID()) + "已存在覆盖");
        }
        logger.info("id" + Integer.toString(fe.getExceptionID()) + "准备存入");
        MongoFormattedException mfe = feRepository.insert(new MongoFormattedException(fe));
        return mfe2fe(mfe);
    }

    @RequestMapping(value = "deleteById/{id}", method = RequestMethod.DELETE)
    public boolean deleteById(@PathVariable String id) {
        logger.info("LogExceptionController获取请求:/fmtexception/deleteById/" + id);
        boolean flag = false;
        try {
            feRepository.deleteById(id);
            flag = true;
        } catch (Exception e) {
            logger.error("mongodb删除失败,id=" + id, e);
        }
        return flag;
    }

    private FormattedException mfe2fe(MongoFormattedException mfe) {
        return new FormattedException(mfe.getExceptionID(), mfe.getTimeMillis(), mfe.getMessage(), mfe.getProject(), mfe.getAdditionalProperties());
    }
}
