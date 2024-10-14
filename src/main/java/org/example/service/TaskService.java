package org.example.service;

import org.example.model.Tag;
import org.example.model.Task;
import org.example.repository.implementation.TaskRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    public Task save(Task task) {
        if (task != null && !task.equals(new Task())) {
            try {
                if (task.getTags() != null && !task.getTags().isEmpty()) {
                    List<Tag> updatedTags = task.getTags().stream()
                            .filter(Objects::nonNull)
                            .map(tagService::insert)
                            .collect(Collectors.toList());
                    task.setTags(updatedTags);
                }
                return taskRepository.insertTask(task);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return task;
    }

    public void deleteTask(long task_id) {
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
        if (task != null && !task.equals(new Task())) {
            return taskRepository.updateTask(task);
        }
        throw new IllegalArgumentException("Task is invalid or missing ID");
    }

    public void validateTask(Task task) throws IllegalArgumentException {
        if (task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Le titre de la tâche est obligatoire.");
        }

        if (task.getStartDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La date de fin doit être dans le futur.");
        }

        if (task.getTags() == null || task.getTags().size() < 2) {
            throw new IllegalArgumentException("La tâche doit avoir au moins deux tags.");
        }

        if (task.getEndDate().isBefore(task.getStartDate().plusDays(3))) {
            throw new IllegalArgumentException("La durée de la tâche doit être d'au moins 3 jours.");
        }

        // Règle 4: Marquer une tâche comme terminée avant la date limite.
       /* if (task.isCompleted() && task.getEndDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Une tâche ne peut pas être marquée comme terminée après la date limite.");
        }*/
        LocalDate today = LocalDate.now();
        LocalDate endDate = task.getEndDate();

        if (task.isCompleted() && (endDate.isEqual(today) || endDate.isBefore(today))) {
            throw new IllegalArgumentException("Une tâche ne peut pas être marquée comme terminée après la date limite.");
        }

       /* // Règle 5: Un utilisateur ne peut attribuer des tâches qu'à lui-même.
        if (!task.getCreatedBy().equals(task.getAssignedUser())) {
            throw new IllegalArgumentException("Vous ne pouvez attribuer des tâches qu'à vous-même.");
        }*/
    }

    public Task updateStatus(Task task) {
        if (task != null && task.getId() != null) {
            Task existingTask = taskRepository.findById(Math.toIntExact(task.getId()));

            if (existingTask != null) {
                existingTask.setCompleted(!existingTask.isCompleted());

                try {
                    return taskRepository.updateStatus(existingTask);
                } catch (Exception e) {
                    throw new RuntimeException("Error updating task", e);
                }
            } else {
                throw new IllegalArgumentException("No task found with the provided ID");
            }
        }
        throw new IllegalArgumentException("Task is invalid or missing ID");
    }


}
