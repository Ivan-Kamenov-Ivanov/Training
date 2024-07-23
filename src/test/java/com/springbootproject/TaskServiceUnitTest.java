package com.springbootproject;

import com.springbootproject.exceptions.TaskAlreadyExistsException;
import com.springbootproject.model.Task;
import com.springbootproject.repositories.TaskRepository;
import com.springbootproject.service.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TaskServiceUnitTest {

    @Mock
    private TaskRepository taskRepository;
    @InjectMocks
    private TaskServiceImpl taskService;

    @BeforeEach
    public void setUp() {
        taskService = new TaskServiceImpl(taskRepository);
    }

    @Test
    public void testAddTaskSuccess() {
        Task taskToAdd = new Task("Task 1", "Description 1");
        when(taskRepository.save(taskToAdd)).thenReturn(taskToAdd);
        Task addedTask = taskService.addTask(taskToAdd);
        assertNotNull(addedTask);
        assertEquals(taskToAdd.getTitle(), addedTask.getTitle());
        assertEquals(taskToAdd.getDescription(), addedTask.getDescription());
    }

    @Test
    public void testAddTaskAlreadyExists() {
        Task existingTask = new Task("Task 1", "Description 1");
        Task duplicateTask = new Task("Task 1", "Description 1");
        taskService.addTask(existingTask);
        when(taskRepository.save(duplicateTask)).thenThrow(TaskAlreadyExistsException.class);
        assertThrows(TaskAlreadyExistsException.class, () -> taskService.addTask(duplicateTask));
    }

    @Test
    public void testFindById() {
        Task task = new Task(1L, "Task 1", "Description 1");
        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        Task foundTask = taskService.findTaskById(task.getId());
        assertNotNull(foundTask);
        assertEquals(task.getId(), foundTask.getId());
        assertEquals(task.getTitle(), foundTask.getTitle());
        assertEquals(task.getDescription(), foundTask.getDescription());
    }

    @Test
    public void testFindAll() {
        List<Task> allTasks = Arrays.asList(
                new Task(1L, "Task 1", "Description 1"),
                new Task(2L, "Task 2", "Description 2"));
        when(taskRepository.findAll()).thenReturn(allTasks);
        List<Task> retrievedTasks = taskService.findAll();
        assertEquals(retrievedTasks, allTasks);
    }

    @Test
    void testUpdateTask() {
        Task existingTask = new Task(1L, "Existing Task", "Description 1");
        Task updatedTask = new Task(1L, "Updated Task", "Description 2");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);
        Task retrievedTask = taskService.updateTask(updatedTask);
        assertNotNull(retrievedTask);
        assertEquals("Updated Task", retrievedTask.getTitle());
        assertEquals("Description 2", retrievedTask.getDescription());
    }

    @Test
    public void testDeleteTaskById() {
        Task taskToDelete = new Task(1L, "Task 1", "Description 1");
        when(taskService.deleteTaskById(taskToDelete.getId())).thenReturn(true);
        taskService.addTask(taskToDelete);
        boolean result = taskService.deleteTaskById(taskToDelete.getId());
        assertTrue(result);
    }

    @Test
    public void testDeleteTaskByIdNotFound() {
        Task taskToDelete = new Task(1L, "Task 1", "Description 1");
        when(taskService.deleteTaskById(taskToDelete.getId())).thenReturn(false);
        boolean result = taskService.deleteTaskById(taskToDelete.getId());
        assertFalse(result);
    }

}
