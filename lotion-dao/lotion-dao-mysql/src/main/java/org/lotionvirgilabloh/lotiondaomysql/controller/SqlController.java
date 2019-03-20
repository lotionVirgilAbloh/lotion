package org.lotionvirgilabloh.lotiondaomysql.controller;

import com.alibaba.fastjson.JSON;
import org.lotionvirgilabloh.lotionbase.dto.DatatablesReturn;
import org.lotionvirgilabloh.lotionbase.query.LotionQueryParam;
import org.lotionvirgilabloh.lotiondaomysql.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@RequestMapping("sql")
@RestController
public class SqlController {


    @Autowired
    private EntityManager entityManager;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public<T> List<T> find(String sql, Class entityClass,Object... args) {
        TypedQuery<T> tq = this.entityManager.createQuery(sql, entityClass);
        for(int i=0;i<args.length;i++){
            tq.setParameter(i, args[i]);
        }
        return tq.getResultList();
    }
    @RequestMapping("/excute")
    <T> DatatablesReturn<T> excute(String sql, LotionQueryParam queryParam){
        logger.info (sql);
        logger.info (queryParam.toString ());
        List<Object> objects = this.find (sql, Role.class);
        System.err.println (JSON.toJSONString (objects));
        return  new DatatablesReturn<> ();
    }

}
