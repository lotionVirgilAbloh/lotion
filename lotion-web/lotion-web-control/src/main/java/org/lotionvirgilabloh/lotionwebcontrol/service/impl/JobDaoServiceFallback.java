package org.lotionvirgilabloh.lotionwebcontrol.service.impl;

import org.lotionvirgilabloh.lotionwebcontrol.service.JobDaoService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobDaoServiceFallback implements JobDaoService {

    @Override
    public <T> List<T> getById(String type, Long id) {
        return null;
    }

    @Override
    public <T> List<T> getByJobname(String type, String jobname) {
        return null;
    }

    @Override
    public <T> List<T> getAll(String type) {
        return null;
    }

    @Override
    public boolean deleteByJobname(String type, String jobname) {
        return false;
    }

    @Override
    public <T> boolean save(String type, T job) {
        return false;
    }

    @Override
    public <T> boolean updateByJobname(String type, T job) {
        return false;
    }
}
