package com.techmonk.taskmanager.controller;

import com.techmonk.taskmanager.entity.Task;
import com.techmonk.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //Combination of @Controller and @ResponseBody
//Rest Controller Annotation is added because every method in here returns data and not views
@RequestMapping("/api/v1/tasks") //Base Path for all endpoints
//localhost:8080/api/v1/tasks
public class TaskController {

    //taskRepository instance gets injected here
    private final TaskRepository taskRepository;

    //Constructor injection
    //Spring Boot automatically handles constructor injection by below constructor.
    //The singleton instance : taskRepository bean gets injected to task controller using constructor injection
    @Autowired
    public TaskController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    //Get endpoint which retrieves and returns all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return taskRepository.findAll(); //function from parent class JpaRepository
    }

    //Get endpoint which retrieves and returns task by id, specified by path variable
    @GetMapping("/{id}")
    //@PathVariable extracts the variable id from the url
    //ResponseEntity makes it easy to handle status codes
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        //Using Steams, can also use for loop method specified in Put Mapping
        return taskRepository.findById(id) //function from JpaRepository
                .map(ResponseEntity::ok) //If found it's wrapped with response entity with status code ok
                .orElse(ResponseEntity.notFound().build()); //if not found
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setCompleted(updatedTask.getCompleted());
                    Task savedTask = taskRepository.save(task);
                    return ResponseEntity.ok(savedTask);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping //Handles HTTP Post Requests
    //@RequestBody tells spring to convert the JSON in the Request body to an object (JSON Parsing)
    public ResponseEntity<Task> createTask(@RequestBody Task task) {
        Task savedTask = taskRepository.save(task); //JpaRepository function
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);
                    return ResponseEntity.noContent().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/completed/{status}")
    public List<Task> getTasksByCompletions(@PathVariable boolean status) {
        return taskRepository.findByCompleted(status);
    }

    @GetMapping("/search")
    public List<Task> searchTasksByTitle(@RequestParam String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title);
    }
}
