package com.techmonk.taskmanager.controller;

import com.techmonk.taskmanager.entity.Task;
import com.techmonk.taskmanager.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    //CUSTOM QUERY
    @GetMapping("/completed/{status}")
    public List<Task> getTasksByCompletions(@PathVariable boolean status) {
        return taskService.getTasksByCompletionStatus(status);
    }

    //CUSTOM QUERY
    @GetMapping("/search")
    public List<Task> searchTasksByTitle(@RequestParam String title) {
        return taskService.searchTasksByTitle(title);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return taskService.updateTask(id, updatedTask);
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = taskService.createTask(task);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}

/*
    NOTES

    - @GetMapping | @PutMapping | @PostMapping | @DeleteMapping - Handles HTTP GET|PUT|POST|DELETE Requests
    - @PathVariable : Extracts the variable id from the url
    - @RequestBody : Tells spring to convert the JSON in the Request body to an object (JSON Parsing)
    - ResponseEntity makes it easy to handle status codes
    - @RequestMapping("/api/v1/tasks") : Base Path for all endpoints | localhost:8080/api/v1/tasks
    - @RestController : Added because every method in here returns data and not views
        - It's a combination of @Controller and @ResponseBody
    - @Autowired is a Spring Framework annotation used for automatic dependency injection
        - Allows Spring to resolve and inject collaborating beans into a class.
    - @AutoWired above the constructor is optional because Spring Boot Automatically detects if only one constructor Exists
*/
