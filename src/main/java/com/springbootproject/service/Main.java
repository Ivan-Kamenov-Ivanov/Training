package com.springbootproject.service;

import com.springbootproject.model.Task;
import com.springbootproject.repositories.TaskRepository;

public class Main {
    public static void main(String[] args) {
        Task task1 = new Task(1L, "Test", "Alabala");
        System.out.println(task1.getTitle());

    }

}
