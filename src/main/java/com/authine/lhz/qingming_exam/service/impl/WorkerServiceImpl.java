package com.authine.lhz.qingming_exam.service.impl;

import com.authine.lhz.qingming_exam.dao.WorkerDao;
import com.authine.lhz.qingming_exam.entity.Department;
import com.authine.lhz.qingming_exam.entity.Role;
import com.authine.lhz.qingming_exam.entity.Worker;
import com.authine.lhz.qingming_exam.dto.WorkerDto;
import com.authine.lhz.qingming_exam.service.WorkerService;
import com.authine.lhz.qingming_exam.util.RedisUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkerServiceImpl implements WorkerService {

    @Resource
    WorkerDao dao;

    @Resource
    RedisUtil redisUtil;

    @Override
    public List<WorkerDto> list() {
        List<WorkerDto> workers = redisUtil.getList("workers", WorkerDto.class);
        if (workers == null) {
            //悲观锁，只需要让一个线程来重建缓存
            synchronized ("workerList") {
                //再次判断
                workers = redisUtil.getList("workers", WorkerDto.class);
                if (workers == null) {
                    workers = dao.findAll().stream().map(WorkerDto::fromWorker).collect(Collectors.toList());
                    redisUtil.setList("workers", workers);
                }
            }
        }
        return workers;
    }

    @Override
    public WorkerDto getOneById(String id) {
        //判断空、逻辑空
        String result = redisUtil.getIdWithObject(id, String.class);
        //重建缓存
        if (result == null) {
            synchronized (id.intern()) {
                //再次判断
                result = redisUtil.getIdWithObject(id, String.class);
                if (result == null) {
                    Optional<Worker> selected = dao.findById(id);
                    //数据库是否存在，不存在，直接缓存逻辑空值
                    if (selected.isPresent()) {
                        redisUtil.setIdWithObject(id, WorkerDto.fromWorker(selected.get()));
                        result = redisUtil.getIdWithObject(id, String.class);
                    } else {
                        redisUtil.setIdWithObject(id, "null");
                        result = "null";
                    }
                }
            }
        }
        //判断是否为逻辑空值
        if (Objects.equals(result, "null")) {
            return null;
        }
        return redisUtil.getIdWithObject(id, WorkerDto.class);
    }

    @Override
    public String create(Worker worker) {
        String id = dao.save(worker).getId();
        List<String> strings = worker.getDepartments().stream().map(Department::getId).collect(Collectors.toList());
        strings.addAll(worker.getRoles().stream().map(Role::getId).collect(Collectors.toList()));
        deleteCacheForId(id, strings);
        return id;
    }

    @Override
    public boolean update(Worker worker) {
        WorkerDto before = getOneById(worker.getId());
        dao.save(worker);
        before.getDepartments().addAll(worker.getDepartments().stream().map(Department::getId).collect(Collectors.toList()));
        before.getRoles().addAll(worker.getRoles().stream().map(Role::getId).collect(Collectors.toList()));
        before.getRoles().addAll(before.getDepartments());
        deleteCacheForId(worker.getId(), before.getRoles());
        return true;
    }

    @Override
    public boolean delete(String id) {
        WorkerDto before = getOneById(id);
        dao.deleteById(id);
        before.getRoles().addAll(before.getDepartments());
        deleteCacheForId(id, before.getRoles());
        return true;
    }

    @Override
    public boolean createList(List<Worker> workers) {
        List<Worker> list = dao.saveAll(workers);
        List<String> ids = list.stream().map(Worker::getId).collect(Collectors.toList());
        ids.addAll(list.stream().flatMap((e) -> e.getDepartments().stream().map(Department::getId)).collect(Collectors.toList()));
        ids.addAll(list.stream().flatMap((e) -> e.getRoles().stream().map(Role::getId)).collect(Collectors.toList()));
        deleteCacheForIds(ids);
        return true;
    }

    @Override
    public boolean updateList(List<Worker> workers) {
        List<String> strings = workers.stream().map(Worker::getId).collect(Collectors.toList());
        List<WorkerDto> olds = strings.stream().map(this::getOneById).collect(Collectors.toList());
        dao.saveAll(workers);
        strings.addAll(workers.stream().flatMap((e) -> e.getRoles().stream().map(Role::getId)).collect(Collectors.toList()));
        strings.addAll(workers.stream().flatMap((e) -> e.getDepartments().stream().map(Department::getId)).collect(Collectors.toList()));
        strings.addAll(olds.stream().flatMap((e) -> e.getRoles().stream()).collect(Collectors.toList()));
        strings.addAll(olds.stream().flatMap((e) -> e.getDepartments().stream()).collect(Collectors.toList()));
        deleteCacheForIds(strings);
        return true;
    }

    @Override
    public boolean deleteList(List<String> ids) {
        List<WorkerDto> before = ids.stream().map(this::getOneById).collect(Collectors.toList());
        dao.deleteAllById(ids);
        ids.addAll(before.stream().flatMap((e) -> e.getRoles().stream()).collect(Collectors.toList()));
        ids.addAll(before.stream().flatMap((e) -> e.getDepartments().stream()).collect(Collectors.toList()));
        deleteCacheForIds(ids);
        return true;
    }

    @Async
    protected void deleteCacheForId(String id, List<String> ids) {
        redisUtil.deleteList("workers");
        redisUtil.deleteList("roles");
        redisUtil.deleteList("departments");
        redisUtil.deleteIdWithObject(id);
        ids.forEach((e) -> redisUtil.deleteIdWithObject(e));
    }

    @Async
    protected void deleteCacheForIds(List<String> ids) {
        redisUtil.deleteList("workers");
        redisUtil.deleteList("roles");
        redisUtil.deleteList("departments");
        ids.forEach((e) -> redisUtil.deleteIdWithObject(e));
    }
}
