package com.authine.lhz.qingming_exam.controller;
import com.authine.lhz.qingming_exam.dto.WorkerDto;
import com.authine.lhz.qingming_exam.service.WorkerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/worker")
@CrossOrigin
public class WorkerController {
    @Resource
    WorkerService service;

    @GetMapping("/list")
    public List<WorkerDto> list() {
        return service.list();
    }

    @GetMapping
    public WorkerDto getOneById(@RequestParam(value = "id") String id) {
        return service.getOneById(id);
    }

    @PostMapping("/create")
    public String create(@RequestBody WorkerDto worker) {
        return service.create(worker.toWorker());
    }

    @PostMapping("/update")
    public boolean update(@RequestBody WorkerDto worker) {
        return service.update(worker.toWorker());
    }

    @PostMapping("/delete")
    public boolean delete(@RequestParam(value = "id") String id) {
        return service.delete(id);
    }

    @PostMapping("/create/list")
    public boolean createList(@RequestBody List<WorkerDto> workers) {
        return service.createList(workers.stream().map(WorkerDto::toWorker).collect(Collectors.toList()));
    }

    @PostMapping("/update/list")
    public boolean updateList(@RequestBody List<WorkerDto> workers) {
        return service.updateList(workers.stream().map(WorkerDto::toWorker).collect(Collectors.toList()));
    }

    @PostMapping("/delete/list")
    public boolean deleteList(@RequestBody List<String> ids) {
        return service.deleteList(ids);
    }
}
