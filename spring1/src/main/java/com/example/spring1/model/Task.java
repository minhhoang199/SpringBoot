package com.example.spring1.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private Integer id;

    public Task(String title) {
        this.title = title;
    }

    private String title;
    private boolean status;
}
