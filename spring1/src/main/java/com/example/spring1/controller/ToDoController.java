package com.example.spring1.controller;

import com.example.spring1.model.Task;
import com.example.spring1.repository.ToDoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/api/v1/todos")
public class ToDoController {
    private ToDoRepository toDoRepository;

    public ToDoController(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @GetMapping("")
    public ResponseEntity<List<Task>> getAll(@RequestParam(required = false) Boolean status) {
        if (status != null) {
            return ResponseEntity.ok(toDoRepository.getByStatus(status));
        } else {
            return ResponseEntity.ok().body(toDoRepository.getAll());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getAll(@PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok(toDoRepository.getById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping()
    public ResponseEntity<Object> addNew(@RequestBody String title) {
        try {
            Random rd = new Random();
            var checked = toDoRepository.addNew(new Task(rd.nextInt(100) + 1, title, false));
            if (checked) {
                return ResponseEntity.ok("Create success");
            } else {
                return ResponseEntity.internalServerError().body("Create failed");
            }

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> editTask(
            @PathVariable("id") Integer id,
            @RequestBody Task newTask) {
        try {
            var rsTask = toDoRepository.updateTask(id, newTask);
            if (rsTask != null) {
                return ResponseEntity.ok("Update success");
            } else {
                return ResponseEntity.internalServerError().body("Update failed");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(
            @PathVariable("id") Integer id) {
        try {
            var checked = toDoRepository.deleteTask(id);
            if (checked) {
                return ResponseEntity.ok("Delete success");
            } else {
                return ResponseEntity.internalServerError().body("Delete failed");
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
