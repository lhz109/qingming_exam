package com.authine.lhz.qingming_exam.controller;
import com.authine.lhz.qingming_exam.dto.RoleDto;
import com.authine.lhz.qingming_exam.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/role")
@CrossOrigin
public class RoleController {
    @Resource
    RoleService service;

    @GetMapping("/list")
    public List<RoleDto> list() {
        return service.list();
    }

    @GetMapping
    public RoleDto getOneById(@RequestParam(value = "id") String id) {
        return service.getOneById(id);
    }

    @PostMapping("/create")
    public String create(@RequestBody RoleDto role) {
        return service.create(role.toRole());
    }

    @PostMapping("/update")
    public boolean update(@RequestBody RoleDto role) {
        return service.update(role.toRole());
    }

    @PostMapping("/delete")
    public boolean delete(@RequestParam(value = "id") String id) {
        return service.delete(id);
    }

    @PostMapping("/create/list")
    public boolean createList(@RequestBody List<RoleDto> roles) {
        return service.createList(roles.stream().map(RoleDto::toRole).collect(Collectors.toList()));
    }

    @PostMapping("/update/list")
    public boolean updateList(@RequestBody List<RoleDto> roles) {
        return service.updateList(roles.stream().map(RoleDto::toRole).collect(Collectors.toList()));
    }

    @PostMapping("/delete/list")
    public boolean deleteList(@RequestBody List<String> ids) {
        return service.deleteList(ids);
    }
}
