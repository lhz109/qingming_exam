package com.authine.lhz.qingming_exam.service;
import com.authine.lhz.qingming_exam.entity.Worker;
import com.authine.lhz.qingming_exam.dto.WorkerDto;

import java.util.List;

public interface WorkerService {
    List<WorkerDto> list();

    WorkerDto getOneById(String id);

    String create(Worker worker);

    boolean update(Worker worker);

    boolean delete(String id);

    boolean createList(List<Worker> workers);

    boolean updateList(List<Worker> workers);

    boolean deleteList(List<String> ids);
}
