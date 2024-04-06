package com.authine.lhz.qingming_exam.service.impl;

import com.authine.lhz.qingming_exam.dao.DepartmentDao;
import com.authine.lhz.qingming_exam.entity.Department;
import com.authine.lhz.qingming_exam.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    DepartmentDao dao;

    @Override
    @Transactional
    public List<Department> list() {
        return dao.findAll();
    }

    @Override
    @Transactional
    public Department getOneById(String id) {
        Optional<Department> result = dao.findById(id);
        return result.orElse(null);
    }

    @Override
    @Transactional
    public String create(Department department) {
        return dao.save(department).getId();
    }

    @Override
    @Transactional
    public boolean update(Department department) {
        dao.save(department);
        return true;
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        dao.deleteById(id);
        return true;
    }
}
