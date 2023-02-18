package com.scaler.taskmanager.controllers;

import com.scaler.taskmanager.dtos.ErrorResponse;
import com.scaler.taskmanager.entities.Task;
import com.scaler.taskmanager.services.TasksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class TasksController {
    private TasksService tasksService;

    public TasksController(@Autowired TasksService tasksService){
        this.tasksService = tasksService;
    }

    @GetMapping("/tasks")
    ResponseEntity<List<Task>> getTasks(){
        return (ResponseEntity<List<Task>>) ResponseEntity.ok(tasksService.getTasks());
    }

    @PostMapping("/tasks")
    ResponseEntity<Task> createTask(@RequestBody Task task){
        Task newTask = tasksService.createTask(task.getTitle(), task.getDescription(), task.getDueDate());
        return ResponseEntity.created(URI.create("/tasks/" + newTask.getId())).body(newTask);
    }

    @GetMapping("/tasks/{id}")
    ResponseEntity<Task> getTask(@PathVariable("id") Integer id){
        return ResponseEntity.ok(tasksService.getTaskById(id));
    }

    @DeleteMapping("/tasks/{id}")
    ResponseEntity<Task> deleteTask(@PathVariable("id") Integer id){
        return ResponseEntity.accepted().body(tasksService.deleteTask(id));
    }

    @PatchMapping("/tasks/{id}")
    ResponseEntity<Task> updateTask(@PathVariable("id") Integer id, @RequestBody Task task){
        Task updatedTask = tasksService.updateTask(id, task.getTitle(), task.getDescription(), task.getDueDate());
        return ResponseEntity.accepted().body(updatedTask);
    }

    @ExceptionHandler(TasksService.TaskNotFoundException.class)
    ResponseEntity<ErrorResponse> handleError(Exception e){
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(e.getMessage()), HttpStatus.NOT_FOUND);
    }
}
