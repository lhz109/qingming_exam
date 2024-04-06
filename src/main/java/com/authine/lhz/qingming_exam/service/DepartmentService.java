package com.authine.lhz.qingming_exam.service;

import com.authine.lhz.qingming_exam.entity.Department;

import java.util.List;

public interface DepartmentService {
    List<Department> list();

    Department getOneById(String id);

    String create(Department department);

    boolean update(Department department);

    boolean delete(String id);
}
