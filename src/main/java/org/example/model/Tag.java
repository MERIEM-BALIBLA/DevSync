package org.example.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String title;

    @ManyToMany(mappedBy = "tags", cascade = CascadeType.ALL)
    private List<Task> taskList;

    public Tag() {
    }

    public Tag(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Task> getTasks() {
        return taskList;
    }

    public void setTasks(List<Task> tasks) {
        this.taskList = tasks;
    }

    public void addTask(Task task) {
        if (!taskList.contains(task)) {
            taskList.add(task);  // Ajout de la tâche si elle n'est pas déjà présente
            task.addTag(this); // Mise à jour de la relation bidirectionnelle dans Task
        }
    }


}
