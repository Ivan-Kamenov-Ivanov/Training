package com.springbootproject.controller;

import com.springbootproject.api.TaskService;
import com.springbootproject.model.Task;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/training")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/tasks")
    public Task createTask(@Valid @RequestBody Task theTask) {
        return taskService.addTask(theTask);
    }

    @GetMapping("/tasks")
    public List<Task> findAll() {

        return taskService.findAll();
    }

    @GetMapping("/tasks/{id}")
    public Task findById(@PathVariable long id) {

        return taskService.findTaskById(id);
    }

    @PutMapping("/tasks")
    public Task updateTask(@Valid @RequestBody Task theTask) {

        return taskService.updateTask(theTask);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTaskById(@PathVariable long id) {
        taskService.deleteTaskById(id);
    }

}
