package com.authine.lhz.qingming_exam.service.impl;

import com.authine.lhz.qingming_exam.dao.WorkerDao;
import com.authine.lhz.qingming_exam.entity.Worker;
import com.authine.lhz.qingming_exam.dto.WorkerDto;
import com.authine.lhz.qingming_exam.service.WorkerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkerServiceImpl implements WorkerService {

    @Resource
    WorkerDao dao;

    @Override
    public List<WorkerDto> list() {
        return dao.findAll().stream().map(WorkerDto::fromWorker).collect(Collectors.toList());
    }

    @Override
    public WorkerDto getOneById(String id) {
        Optional<Worker> result = dao.findById(id);
        if (result.orElse(null) != null) {
            return WorkerDto.fromWorker(result.orElse(null));
        }
        return null;
    }

    @Override
    public String create(Worker worker) {
        return dao.save(worker).getId();
    }

    @Override
    public boolean update(Worker worker) {
        dao.save(worker);
        return true;
    }

    @Override
    public boolean delete(String id) {
        dao.deleteById(id);
        return true;
    }

    @Override
    public boolean createList(List<Worker> workers) {
        dao.saveAll(workers);
        return true;
    }

    @Override
    public boolean updateList(List<Worker> workers) {
        dao.saveAll(workers);
        return true;
    }

    @Override
    public boolean deleteList(List<String> ids) {
        dao.deleteAllById(ids);
        return true;
    }
}
