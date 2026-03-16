package com.techmonk.taskmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

//The Below 3 Tags saves us from writing 50+ lines of boilerplate code
@Data //Gives us getters, setters, toString, equals, hashcode methods
@NoArgsConstructor //Gives us empty constructor
@AllArgsConstructor //Gives us constructor with all defined attributes as arguments
public class Task {
    private Long id;
    private String title;
    private String description;
    private Boolean completed;
    private LocalDateTime createdAt;
}

//Currently this is just a POJO, later we will make it a proper entity with data persistence