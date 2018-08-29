package org.lotionvirgilabloh.lotiondaomongo.controller;

import org.lotionvirgilabloh.lotionbase.dto.FormattedException;
import org.lotionvirgilabloh.lotiondaomongo.entity.MongoFormattedException;
import org.lotionvirgilabloh.lotiondaomongo.repository.FERepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public Page<FormattedException> findAllPage(int whichPage, int pageLimit) {
        logger.info("LogExceptionController获取请求:/fmtexception/findAllPage?whichPage=" + whichPage + "&pageLimit=" + pageLimit);
        Page<MongoFormattedException> mfes = feRepository.findAll(PageRequest.of(whichPage, pageLimit));
        return mfes.map(this::mfe2fe);
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
    public FormattedException insert(FormattedException fe) {
        logger.info("LogExceptionController获取请求:/fmtexception/insert");
        if (feRepository.existsById(String.valueOf(fe.getExceptionID()))) {
            System.out.print("id已存在覆盖");
        }
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
