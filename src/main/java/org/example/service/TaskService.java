package org.example.service;

import org.example.model.Tag;
import org.example.model.Task;
import org.example.repository.implementation.TaskRepository;

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


    public Task insertTaskWithTags(Task task, String[] tags) {
        if (task == null || task.getTitle() == null || task.getTitle().isEmpty()) {
            throw new IllegalArgumentException("La tâche est invalide ou vide.");
        }

        List<Tag> tagsToAdd = new ArrayList<>();

        if (tags != null) {
            for (String tagTitle : tags) {
                Tag foundTag = tagService.findByTitle(tagTitle);
                if (foundTag == null) {
                    // Si le tag n'existe pas, on le crée
                    foundTag = new Tag(tagTitle);
                    tagService.insert(foundTag);
                }
                tagsToAdd.add(foundTag); // Ajoute le tag trouvé ou nouvellement créé
            }
        }

        task.setTags(tagsToAdd); // Associer les tags à la tâche
        return taskRepository.insertTask(task); // Insérer la tâche dans le repository
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
        if (task != null && task.getId() != null) {
            Task existingTask = taskRepository.findById(Math.toIntExact(task.getId()));

            if (existingTask != null) {
                // Mettez à jour les attributs de la tâche
                // Update other task fields
                existingTask.setTitle(task.getTitle());
                existingTask.setDescription(task.getDescription());
                existingTask.setDate(task.getEndDate());
                existingTask.setAssignedUser(task.getAssignedUser());

                // Mettez à jour les tags
                existingTask.getTags().clear(); // Effacez les anciens tags
                List<Tag> updatedTags = task.getTags().stream()
                        .filter(Objects::nonNull)
                        .map(tagService::insert) // Assurez-vous que `insert` insère les nouveaux tags
                        .collect(Collectors.toList());
                existingTask.setTags(updatedTags);

                try {
                    return taskRepository.updateTask(existingTask);
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
