package com.authine.lhz.qingming_exam.service.impl;

import com.authine.lhz.qingming_exam.dao.DepartmentDao;
import com.authine.lhz.qingming_exam.entity.Department;
import com.authine.lhz.qingming_exam.dto.DepartmentDto;
import com.authine.lhz.qingming_exam.entity.Worker;
import com.authine.lhz.qingming_exam.service.DepartmentService;
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
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    DepartmentDao dao;

    @Resource
    RedisUtil redisUtil;

    @Override
    public List<DepartmentDto> list() {
        List<DepartmentDto> departments = redisUtil.getList("departments", DepartmentDto.class);
        if (departments == null) {
            //悲观锁，只需要让一个线程来重建缓存
            synchronized ("departmentList") {
                //再次判断
                departments = redisUtil.getList("departments", DepartmentDto.class);
                if (departments == null) {
                    departments = dao.findAll().stream().map(DepartmentDto::fromDepartment).collect(Collectors.toList());
                    redisUtil.setList("departments", departments);
                }
            }
        }
        return departments;
    }

    @Override
    public DepartmentDto getOneById(String id) {
        String result = redisUtil.getIdWithObject(id, String.class);
        //重建缓存
        if (result == null) {
            synchronized (id.intern()) {
                //再次判断
                result = redisUtil.getIdWithObject(id, String.class);
                if (result == null) {
                    Optional<Department> selected = dao.findById(id);
                    //数据库是否存在，不存在，直接缓存逻辑空值
                    if (selected.isPresent()) {
                        redisUtil.setIdWithObject(id, DepartmentDto.fromDepartment(selected.get()));
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
        return redisUtil.getIdWithObject(id, DepartmentDto.class);
    }

    @Async
    protected void deleteCacheForId(String id, List<String> ids) {
        redisUtil.deleteList("departments");
        redisUtil.deleteList("workers");
        redisUtil.deleteIdWithObject(id);
        ids.forEach((e) -> redisUtil.deleteIdWithObject(e));
    }

    @Async
    protected void deleteCacheForIds(List<String> ids) {
        redisUtil.deleteList("departments");
        redisUtil.deleteList("workers");
        ids.forEach((e) -> redisUtil.deleteIdWithObject(e));
    }

    @Override
    public String create(Department department) {
        String id = dao.save(department).getId();
        deleteCacheForId(id, department.getWorkers().stream().map(Worker::getId).collect(Collectors.toList()));
        return id;
    }

    @Override
    public boolean update(Department department) {
        DepartmentDto before = getOneById(department.getId());
        dao.save(department);
        before.getWorkers().addAll(department.getWorkers().stream().map(Worker::getId).collect(Collectors.toList()));
        deleteCacheForId(department.getId(), before.getWorkers());
        return true;
    }

    @Override
    public boolean delete(String id) {
        DepartmentDto before = getOneById(id);
        dao.deleteById(id);
        deleteCacheForId(id, before.getWorkers());
        return true;
    }

    @Override
    public boolean createList(List<Department> departments) {
        List<Department> selected = dao.saveAll(departments);
        List<String> ids = selected.stream().map(Department::getId).collect(Collectors.toList());
        ids.addAll(selected.stream().flatMap((e) -> e.getWorkers().stream().map(Worker::getId)).collect(Collectors.toList()));
        deleteCacheForIds(ids);
        return true;
    }

    @Override
    public boolean updateList(List<Department> departments) {
        List<String> ids = departments.stream().map(Department::getId).collect(Collectors.toList());
        ids.addAll(departments.stream().flatMap((e) -> getOneById(e.getId()).getWorkers().stream()).collect(Collectors.toList()));
        dao.saveAll(departments);
        ids.addAll(departments.stream().flatMap((e) -> e.getWorkers().stream().map(Worker::getId)).collect(Collectors.toList()));
        deleteCacheForIds(ids);
        return true;
    }

    @Override
    public boolean deleteList(List<String> ids) {
        List<String> strings = ids.stream().flatMap((e) -> getOneById(e).getWorkers().stream()).collect(Collectors.toList());
        dao.deleteAllById(ids);
        ids.addAll(strings);
        deleteCacheForIds(ids);
        return true;
    }
}
