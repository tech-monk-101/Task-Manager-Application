package com.techmonk.taskmanager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity //Marks this class as a JPA entity
@Table(name = "tasks") //table named tasks

//The Below 3 Tags saves us from writing 50+ lines of boilerplate code
@Data //Gives us getters, setters, toString, equals, hashcode methods
@NoArgsConstructor //Gives us empty constructor
@AllArgsConstructor //Gives us constructor with all defined attributes as arguments
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private Boolean completed = false;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist //Runs Just Before We Save to the DB
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}

//Currently this is just a POJO, later we will make it a proper entity with data persistence