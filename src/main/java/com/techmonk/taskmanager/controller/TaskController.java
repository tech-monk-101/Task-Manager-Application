package com.techmonk.taskmanager.controller;

import com.techmonk.taskmanager.entity.Task;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController //Combination of @Controller and @ResponseBody
//Rest Controller Annotation is added because every method in here returns data and not views
@RequestMapping("/api/v1/tasks") //Base Path for all endpoints
//localhost:8080/api/v1/tasks
public class TaskController {

    //Temporary in memory task list, later replaced with database
    private List<Task> tasks = new ArrayList<>();
    private Long nextId = 1L;

    //Get endpoint which retrieves and returns all tasks
    @GetMapping
    public List<Task> getAllTasks() {

        return tasks;
    }

    //Get endpoint which retrieves and returns task by id, specified by path variable
    @GetMapping("/{id}")
    //@PathVariable extracts the variable id from the url
    public Task getTaskById(@PathVariable Long id) {
        //Using Steams, can also use for loop method specified in Put Mapping
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        for(int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if(task.getId().equals(id)) {
                updatedTask.setId(id);
                updatedTask.setCreatedAt(task.getCreatedAt());
                updatedTask.setCompleted(task.getCompleted());
                tasks.set(i, updatedTask);
                return updatedTask;
            }
        }
        return null;
    }

    @PostMapping //Handles HTTP Post Requests
    //@RequestBody tells spring to convert the JSON in the Request body to an object (JSON Parsing)
    public Task createTask(@RequestBody Task task) {
        task.setId(nextId++);
        task.setCreatedAt(LocalDateTime.now());
        task.setCompleted(false);
        tasks.add(task);

        return task;
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        tasks.removeIf(task -> task.getId().equals(id));
    }

}
