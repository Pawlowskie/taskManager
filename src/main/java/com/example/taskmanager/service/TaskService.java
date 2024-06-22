package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.example.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasks() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return taskRepository.findAll().stream()
                .map(task -> {
                    if (task.getCreatedAt() != null) {
                        task.setFormattedCreatedAt(task.getCreatedAt().format(formatter));
                    }
                    return task;
                })
                .collect(Collectors.toList());
    }

    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task saveTask(Task task) {
        if (task.getId() != null) {
            Optional<Task> existingTaskOpt = taskRepository.findById(task.getId());
            if (existingTaskOpt.isPresent()) {
                Task existingTask = existingTaskOpt.get();
                task.setCreatedAt(existingTask.getCreatedAt());
            }
        }
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }
}
