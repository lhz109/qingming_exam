package com.authine.lhz.qingming_exam.controller;


import com.authine.lhz.qingming_exam.entity.Role;
import com.authine.lhz.qingming_exam.service.RoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/role")
@CrossOrigin
public class RoleController {
    @Resource
    RoleService service;

    @GetMapping("/list")
    public List<Role> list() {
        return service.list();
    }

    @GetMapping
    public Role getOneById(@RequestParam(value = "id") String id) {
        return service.getOneById(id);
    }

    @PostMapping("/create")
    public String create(@RequestBody Role role) {
        return service.create(role);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Role role) {
        return service.update(role);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestParam(value = "id") String id) {
        return service.delete(id);
    }
}
