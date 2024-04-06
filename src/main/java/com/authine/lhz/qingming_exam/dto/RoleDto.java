package com.authine.lhz.qingming_exam.dto;
import com.authine.lhz.qingming_exam.entity.Role;
import com.authine.lhz.qingming_exam.entity.Worker;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class RoleDto {
    private String id;


    private String name;


    private List<String> workers;

    public static RoleDto fromRole(Role role) {
        RoleDto dto = new RoleDto();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setWorkers(role.getWorkers().stream().map(Worker::getId).collect(Collectors.toList()));
        return dto;
    }

    public Role toRole() {
        Role role = new Role();
        role.setId(getId());
        role.setName(getName());
        role.setWorkers(getWorkers().stream().map((e) -> {
            Worker worker = new Worker();
            worker.setId(e);
            return worker;
        }).collect(Collectors.toList()));
        return role;
    }
}
