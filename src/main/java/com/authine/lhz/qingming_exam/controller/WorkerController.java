package com.authine.lhz.qingming_exam.controller;

import com.authine.lhz.qingming_exam.entity.Worker;
import com.authine.lhz.qingming_exam.service.WorkerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/worker")
@CrossOrigin
public class WorkerController {
    @Resource
    WorkerService service;

    @GetMapping("/list")
    public List<Worker> list() {
        return service.list();
    }

    @GetMapping
    public Worker getOneById(@RequestParam(value = "id") String id) {
        return service.getOneById(id);
    }

    @PostMapping("/create")
    public String create(@RequestBody Worker worker) {
        return service.create(worker);
    }

    @PostMapping("/update")
    public boolean update(@RequestBody Worker worker) {
        return service.update(worker);
    }

    @PostMapping("/delete")
    public boolean delete(@RequestParam(value = "id") String id) {
        return service.delete(id);
    }
}
