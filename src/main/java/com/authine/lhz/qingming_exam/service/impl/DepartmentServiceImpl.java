package com.authine.lhz.qingming_exam.service.impl;

import com.authine.lhz.qingming_exam.dao.DepartmentDao;
import com.authine.lhz.qingming_exam.entity.Department;
import com.authine.lhz.qingming_exam.dto.DepartmentDto;
import com.authine.lhz.qingming_exam.service.DepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    DepartmentDao dao;

    @Override
    public List<DepartmentDto> list() {
        return dao.findAll().stream().map(DepartmentDto::fromDepartment).collect(Collectors.toList());
    }

    @Override
    public DepartmentDto getOneById(String id) {
        Optional<Department> result = dao.findById(id);
        if (result.orElse(null) != null) {
            return DepartmentDto.fromDepartment(result.orElse(null));
        }
        return null;
    }

    @Override
    public String create(Department department) {
        return dao.save(department).getId();
    }

    @Override
    public boolean update(Department department) {
        dao.save(department);
        return true;
    }

    @Override
    public boolean delete(String id) {
        dao.deleteById(id);
        return true;
    }

    @Override
    public boolean createList(List<Department> departments) {
        dao.saveAll(departments);
        return true;
    }

    @Override
    public boolean updateList(List<Department> departments) {
        dao.saveAll(departments);
        return true;
    }

    @Override
    public boolean deleteList(List<String> ids) {
        dao.deleteAllById(ids);
        return true;
    }
}
