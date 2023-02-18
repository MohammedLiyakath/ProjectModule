package com.scaler.taskmanager.services;

import com.scaler.taskmanager.entities.Task;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TasksService {
    private final List<Task> taskList;
    private AtomicInteger taskId = new AtomicInteger(0);

    public static class TaskNotFoundException extends IllegalArgumentException{
        public TaskNotFoundException(Integer id){
            super("Task with id " + id + " not fount");
        }
    }

    public TasksService(){
        taskList = new ArrayList<>();
        taskList.add(new Task(taskId.incrementAndGet(), "Task 1", "Description 1", "2021-01-01"));
        taskList.add(new Task(taskId.incrementAndGet(), "Task 2", "Description 2", "2021-01-02"));
        taskList.add(new Task(taskId.incrementAndGet(), "Task 3", "Description 3", "2021-01-03"));
    }

    public List<Task> getTasks(){
        return taskList;
    }

    public Task createTask(String title, String description, String dueDate){
        Task newTask = new Task(taskId.incrementAndGet(), title, description, dueDate);
        taskList.add(newTask);
        return newTask;
    }

    public Task getTaskById(Integer id){
        return taskList.stream().filter(task -> task.getId().equals(id)).findFirst().orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task updateTask(Integer id, String title, String description, String dueDate){
        Task task = getTaskById(id);
        if(title != null)
            task.setTitle(title);
        if(description != null)
            task.setDescription(title);
        if(dueDate != null)
            task.setDueDate(dueDate);
        return task;
    }
    public Task deleteTask(Integer id){
        Task task = getTaskById(id);
        taskList.remove(task);
        return task;
    }
}
