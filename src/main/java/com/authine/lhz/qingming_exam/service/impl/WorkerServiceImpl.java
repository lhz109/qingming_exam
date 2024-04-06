package com.authine.lhz.qingming_exam.service.impl;

import com.authine.lhz.qingming_exam.dao.WorkerDao;
import com.authine.lhz.qingming_exam.entity.Worker;
import com.authine.lhz.qingming_exam.service.WorkerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class WorkerServiceImpl implements WorkerService {

    @Resource
    WorkerDao dao;

    @Override
    @Transactional
    public List<Worker> list() {
        return dao.findAll();
    }

    @Override
    @Transactional
    public Worker getOneById(String id) {
        Optional<Worker> result = dao.findById(id);
        return result.orElse(null);
    }

    @Override
    @Transactional
    public String create(Worker worker) {
        return dao.save(worker).getId();
    }

    @Override
    @Transactional
    public boolean update(Worker worker) {
        dao.save(worker);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        dao.deleteById(id);
        return true;
    }
}
