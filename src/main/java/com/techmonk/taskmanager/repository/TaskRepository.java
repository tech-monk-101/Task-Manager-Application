package com.techmonk.taskmanager.repository;

import com.techmonk.taskmanager.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository - Not Required as JpaRepository Already Has It
public interface TaskRepository extends JpaRepository<Task, Long> {

    //Method Name Query
    //SELECT * FROM tasks WHERE completed = :completed
    List<Task> findByCompleted(boolean completed);

    List<Task> findByTitleContainingIgnoreCase(String title);

    //JPQL QUERY?? - JAVA PERSISTENCE QUERY LANGUAGE
    //LIKE SQL BUT USES ENTITY NAMES INSTEAD OF TABLE NAMES
    @Query("SELECT t FROM Task t where t.completed = :completed")
    List<Task> findTaskByCompletionStatus(@Param("completed") boolean completed);
}
//Spring Magic: Spring Data is going to auto generate an implementation for save, findall, findbyid, etc.
//Interface can be left empty, if you want custom queries, write that methods in it
