package com.springbootproject.service;

import com.springbootproject.api.TaskService;
import com.springbootproject.exceptions.TaskAlreadyExistsException;
import com.springbootproject.exceptions.TaskNotFoundException;
import com.springbootproject.model.Task;
import com.springbootproject.repositories.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Task findTaskById(long id) {
        Optional<Task> tempResult = taskRepository.findById(id);
        if (tempResult.isPresent()) {
            return tempResult.get();
        }
        throw new TaskNotFoundException(String.format("Task with id %d does not exist!", id));
    }

    @Override
    @Transactional
    public Task addTask(Task theTask) throws TaskAlreadyExistsException {

        if (taskRepository.existsByTitle(theTask.getTitle())) {
            throw new TaskAlreadyExistsException("Task name already exists");
        }
        return taskRepository.save(theTask);
    }

    @Override
    public Task updateTask(Task updatedTask) {
        Task existingTask = taskRepository.findById(updatedTask.getId())
                .orElseThrow(() -> new TaskNotFoundException("Task not found with id " + updatedTask.getId()));

        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setDueDate(updatedTask.getDueDate());
        existingTask.setStatusActive(updatedTask.isStatusActive());

        return taskRepository.save(existingTask);
    }

    @Override
    @Transactional
    public boolean deleteTaskById(long id) throws TaskNotFoundException {
        if (!taskRepository.existsById(id)) {
            throw new TaskNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
        return true;
    }
}

