package org.example.service;

import org.example.model.Tag;
import org.example.model.Task;
import org.example.repository.implementation.TaskRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskService {
    private final TaskRepository taskRepository;
    private final TagService tagService;

    public TaskService() {
        this.taskRepository = new TaskRepository();
        this.tagService = new TagService();
    }

    public List<Task> getAllTask() {
        return taskRepository.getAllTask().stream()
                .filter(e -> e.getCreatedBy().isManager())
                .collect(Collectors.toList());
    }


     public Task insertTask(Task task) {
         if (task == null) {
             throw new IllegalArgumentException("La tâche ne peut pas être nulle.");
         }

         Task createdTask = taskRepository.insertTask(task);

         // Ajout des tags à la tâche
         List<Tag> tags = task.getTags();
         if (tags != null && !tags.isEmpty()) {
             for (Tag tag : tags) {
                 tag.getTasks().add(createdTask);
                 tagService.insert(tag); // Insérer ou mettre à jour le tag
             }
         }
         return createdTask; // Retournez la tâche créée
     }
    public void deleteTask(int task_id) {
        taskRepository.deleteTask(task_id);
    }

    public List<Task> getTasksByUserId(int id) {
        return taskRepository.getTasksByUserId(id).stream()
                .filter(e -> e.getCreatedBy().isManager())
                .collect(Collectors.toList());
    }

    public List<Task> getSubTasks(int id) {
        return taskRepository.getTasksByUserId(id).stream()
                .filter(e -> e.getCreatedBy().equals(e.getAssignedUser()))
                .collect(Collectors.toList());
    }

    public Task findById(int id) {
        Task task = taskRepository.findById(id);

        if (task == null) {
            throw new IllegalArgumentException("Task with ID " + id + " not found");
        }

        return task;
    }

    public Task updateTask(Task task) {
        return taskRepository.updateTask(task);
    }

}
