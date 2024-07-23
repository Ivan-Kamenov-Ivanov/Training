package com.springbootproject.api;

import com.springbootproject.model.Task;
import jakarta.validation.Valid;
import lombok.NonNull;

import java.util.List;

public interface TaskService {

    List<Task> findAll();

    Task findTaskById(long id);

    Task addTask(@Valid Task theTask);

    Task updateTask(@Valid Task theTask);

    boolean deleteTaskById(@NonNull long id);

}
