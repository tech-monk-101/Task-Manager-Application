package com.techmonk.taskmanager.service;

import com.techmonk.taskmanager.entity.Task;
import com.techmonk.taskmanager.repository.TaskRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll(); //function from parent class JpaRepository
    }

    //Optional handles null so .orElse part is not required
    public Optional<Task> getTaskById(Long id) {
        //return taskRepository.findById(id).orElse(null);
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {
        return taskRepository.save(task);
    }

    public Optional<Task> updateTask(Long id, Task updatedTask) {
        return taskRepository.findById(id)
                .map(task -> {
                    task.setTitle(updatedTask.getTitle());
                    task.setDescription(updatedTask.getDescription());
                    task.setCompleted(updatedTask.getCompleted());
                    return taskRepository.save(task);
                });
    }

    public boolean deleteTask(Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    taskRepository.delete(task);
                    return true;
                })
                .orElse(false);
    }

    public List<Task> getTasksByCompletionStatus(boolean status) {
        return taskRepository.findByCompleted(status); //DEFINED IN TASKREPOSITORY INTERFACE
    }

    public List<Task> searchTasksByTitle(String title) {
        return taskRepository.findByTitleContainingIgnoreCase(title); //DEFINED IN TASKREPOSITORY INTERFACE
    }
}
