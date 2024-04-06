package com.authine.lhz.qingming_exam.dto;

import com.authine.lhz.qingming_exam.entity.Department;
import com.authine.lhz.qingming_exam.entity.Role;
import com.authine.lhz.qingming_exam.entity.Worker;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class WorkerDto {
    private String id;

    private String name;

    private List<String> roles;

    private List<String> departments;

    public static WorkerDto fromWorker(Worker worker) {
        WorkerDto dto = new WorkerDto();
        dto.setId(worker.getId());
        dto.setName(worker.getName());
        dto.setRoles(worker.getRoles().stream().map(Role::getId).collect(Collectors.toList()));
        dto.setDepartments(worker.getDepartments().stream().map(Department::getId).collect(Collectors.toList()));
        return dto;
    }

    public Worker toWorker() {
        Worker worker = new Worker();
        worker.setId(getId());
        worker.setName(getName());
        worker.setDepartments(getDepartments().stream().map((e) -> {
            Department department = new Department();
            department.setId(e);
            return department;
        }).collect(Collectors.toList()));
        worker.setRoles(getRoles().stream().map((e) -> {
            Role role = new Role();
            role.setId(e);
            return role;
        }).collect(Collectors.toList()));
        return worker;
    }
}
