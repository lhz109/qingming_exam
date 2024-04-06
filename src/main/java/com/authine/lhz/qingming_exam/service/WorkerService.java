package com.authine.lhz.qingming_exam.service;

import com.authine.lhz.qingming_exam.entity.Worker;

import java.util.List;

public interface WorkerService {
    List<Worker> list();

    Worker getOneById(String id);

    String create(Worker worker);

    boolean update(Worker worker);

    boolean delete(String id);
}
