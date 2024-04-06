package com.authine.lhz.qingming_exam.controller;

import com.authine.lhz.qingming_exam.entity.Department;
import com.authine.lhz.qingming_exam.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/department")
@CrossOrigin
public class DepartmentController {
    @Resource
    DepartmentService service;

    @GetMapping("/list")
    public List<Department> list() {
        return service.list();
    }

    @GetMapping
    public Department getOneById(@RequestParam(value = "id") String id) {
        return service.getOneById(id);
    }

    @PostMapping("/create")
    public String create(@RequestBody Department department) {
        return service.create(department);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Department department) {
        return service.update(department);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestParam(value = "id") String id) {
        return service.delete(id);
    }
}
