package com.springbootproject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springbootproject.model.Task;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskServiceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddTaskSuccess() throws Exception {
        Task task = new Task("Task 1", "Description 1");

        mockMvc.perform(post("/training/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk());
    }

    @Test
    public void testAddTaskAlreadyExists() throws Exception {
        Task existingTask = new Task("Task 1", "Description 1");
        Task duplicateTask = new Task("Task 1", "Description 1");

        mockMvc.perform(post("/training/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingTask)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/training/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicateTask)))
                .andExpect(status().isConflict());
    }


    @Test
    public void testUpdateTask() throws Exception {
        Task existingTask = new Task("Existing Task", "Description 1");
        Task updatedTask = new Task("Updated Task", "Description 2");

        mockMvc.perform(post("/training/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(existingTask)))
                .andExpect(status().isOk());

        mockMvc.perform(put("/training/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedTask)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTaskById() throws Exception {
        Task task = new Task(1L, "Task 1", "Description 1");

        mockMvc.perform(post("/training/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk());

        mockMvc.perform(delete("/training/tasks/{id}", task.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteTaskByIdNotFound() throws Exception {
        mockMvc.perform(delete("/training/tasks/{id}", -1L))
                .andExpect(status().isNotFound());
    }
}