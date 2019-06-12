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

    public List  find(String sql, Class entityClass,Object... args) {
        Query query = this.entityManager.createNativeQuery(sql);
        for (int i = 0; i < args.length; i++) {
            query.setParameter(i + 1, args[i]);
        }
        return query.getResultList ();
    }
    @RequestMapping("/excute")
     List excute(String sql, LotionQueryParam queryParam){
        logger.info (sql);
        logger.info (queryParam.toString ());
        long a =System.currentTimeMillis ();
        List<Role> objects = this.find (sql, Role.class);
        logger.info (System.currentTimeMillis ()-a+"");
        System.err.println (JSON.toJSONString (objects));
        return  objects;
    }

}
