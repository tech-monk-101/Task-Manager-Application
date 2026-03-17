package com.techmonk.taskmanager.repository;

import com.techmonk.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
//Spring Magic: Spring Data is going to auto generate an implementation for save, findall, findbyid, etc.
