package com.authine.lhz.qingming_exam.controller;
import com.authine.lhz.qingming_exam.dto.DepartmentDto;
import com.authine.lhz.qingming_exam.service.DepartmentService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/department")
@CrossOrigin
public class DepartmentController {
    @Resource
    DepartmentService service;

    @GetMapping("/list")
    public List<DepartmentDto> list() {
        return service.list();
    }

    @GetMapping
    public DepartmentDto getOneById(@RequestParam(value = "id") String id) {
        return service.getOneById(id);
    }

    @PostMapping("/create")
    public String create(@RequestBody DepartmentDto department) {
        return service.create(department.toDepartment());
    }

    @PostMapping("/update")
    public boolean update(@RequestBody DepartmentDto department) {
        return service.update(department.toDepartment());
    }

    @PostMapping("/delete")
    public boolean delete(@RequestParam(value = "id") String id) {
        return service.delete(id);
    }

    @PostMapping("/create/list")
    public boolean createList(@RequestBody List<DepartmentDto> departments) {
        return service.createList(departments.stream().map(DepartmentDto::toDepartment).collect(Collectors.toList()));
    }

    @PostMapping("/update/list")
    public boolean updateList(@RequestBody List<DepartmentDto> departments) {
        return service.updateList(departments.stream().map(DepartmentDto::toDepartment).collect(Collectors.toList()));
    }

    @PostMapping("/delete/list")
    public boolean deleteList(@RequestBody List<String> ids) {
        return service.deleteList(ids);
    }
}
