package com.authine.lhz.qingming_exam.dto;

import com.authine.lhz.qingming_exam.entity.Department;
import com.authine.lhz.qingming_exam.entity.Worker;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class DepartmentDto {
    private String id;


    private String name;


    private List<String> workers;

    public static DepartmentDto fromDepartment(Department department) {
        DepartmentDto dto = new DepartmentDto();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setWorkers(department.getWorkers().stream().map(Worker::getId).collect(Collectors.toList()));
        return dto;
    }

    public Department toDepartment() {
        Department department = new Department();
        department.setId(getId());
        department.setName(getName());
        department.setWorkers(getWorkers().stream().map((e) -> {
            Worker worker = new Worker();
            worker.setId(e);
            return worker;
        }).collect(Collectors.toList()));
        return department;
    }
}
