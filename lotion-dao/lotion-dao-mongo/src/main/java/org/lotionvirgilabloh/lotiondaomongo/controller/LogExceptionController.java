package org.lotionvirgilabloh.lotiondaomongo.controller;

import org.lotionVirgilAbloh.lotionbase.dto.FormattedException;
import org.lotionvirgilabloh.lotiondaomongo.entity.MongoFormattedException;
import org.lotionvirgilabloh.lotiondaomongo.repository.FERepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RequestMapping("fmtexception")
@RestController
public class LogExceptionController {

    @Autowired
    public FERepository feRepository;

    @RequestMapping("findAll")
    public List<MongoFormattedException> findAll(){
       return feRepository.findAll();
    }


    @RequestMapping("findById")
    public MongoFormattedException findById(String id){
        Optional<MongoFormattedException> optional= feRepository.findById(id);
        return  optional.get();
    }

    //返回持久态文件
    @RequestMapping(value = "insert",method = RequestMethod.POST)
    public MongoFormattedException insert(FormattedException fe){
        if(feRepository.existsById(String.valueOf(fe.getExceptionID()))){
            System.out.print("id已存在覆盖");
        }
        MongoFormattedException mongoFormattedException =feRepository.insert(new MongoFormattedException(fe));
        return  mongoFormattedException;
    }

    @RequestMapping(value ="deleteById/{id}",method = RequestMethod.DELETE)
    public boolean deleteById(@PathVariable String id){
        boolean flag =false;
        try {
            feRepository.deleteById(id);
            flag=true;
        }catch(Exception e){
            //@TODO
        }
        return  flag;
    }
}
