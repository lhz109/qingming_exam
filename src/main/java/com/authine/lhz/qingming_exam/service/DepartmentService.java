package com.authine.lhz.qingming_exam.service;

import com.authine.lhz.qingming_exam.entity.Department;
import com.authine.lhz.qingming_exam.dto.DepartmentDto;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDto> list();

    DepartmentDto getOneById(String id);

    String create(Department department);

    boolean update(Department department);

    boolean delete(String id);

    boolean createList(List<Department> departments);

    boolean updateList(List<Department> departments);

    boolean deleteList(List<String> ids);
}
