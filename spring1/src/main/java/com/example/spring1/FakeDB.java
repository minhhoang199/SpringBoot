package com.example.spring1;


import com.example.spring1.model.Task;

import java.util.ArrayList;
import java.util.List;

public class FakeDB {

    public static List<Task> taskDB = new ArrayList<>(List.of(
            new Task(1, "Xuan", true),
            new Task(2, "Ly", true),
            new Task(3, "Thu", false),
            new Task(4, "Dong", false),
            new Task(5, "Hoa", true),
            new Task(6, "Binh", false),
            new Task(7, "Hanh", true),
            new Task(8, "Phuc", false)
    ));

    }

