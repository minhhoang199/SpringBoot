package com.example.spring1.repository;

import com.example.spring1.FakeDB;
import com.example.spring1.model.Task;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class ToDoRepository {
    public List<Task> getAll() {
        return FakeDB.taskDB;
    }

    public List<Task> getByStatus(Boolean status) {
        return FakeDB.taskDB.stream().filter(t -> t.isStatus() == status).toList();
    }

    private boolean validateNumber(Integer num) {
        if (num < 0) {
            throw new RuntimeException("This number can not be negative");
        }
        if (num == 0) {
            throw new RuntimeException("This number can not be 0");
        }
        return true;
    }

    public List<Task> getById(Integer id) {
        validateNumber(id);
        return FakeDB.taskDB.stream().filter(t -> Objects.equals(t.getId(), id)).toList();
    }

    private boolean validateTitle(String title) {
        if (title.length() <= 0) {
            throw new RuntimeException("Title can not be empty");
        }
        if (title == null) {
            throw new RuntimeException("Title can not be null");
        }
        return true;
    }

    public boolean addNew(Task task) {
        if (task != null) {
            validateTitle(task.getTitle());
            FakeDB.taskDB.add(task);
            return true;
        } else {
            return false;
        }
    }

    public Task updateTask(Integer id, Task newTask) {
        validateNumber(id);
        if (newTask == null) {
            return null;
        } else {
            var opTask = FakeDB.taskDB.stream().filter(t -> Objects.equals(t.getId(), id)).findAny();
            if (opTask.isPresent()) {
                Task currentTask = opTask.get();
                if (validateTitle(newTask.getTitle())) {
                    currentTask.setTitle(newTask.getTitle());
                }
                currentTask.setStatus(newTask.isStatus());
                return currentTask;
            } else {
                throw new RuntimeException("Not found Task");
            }
        }
    }

    public boolean deleteTask(Integer id){
        validateNumber(id);
        return FakeDB.taskDB.removeIf(t -> Objects.equals(t.getId(), id));
    }
}
