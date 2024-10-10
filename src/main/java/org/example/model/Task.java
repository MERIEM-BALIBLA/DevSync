package org.example.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 500)
    private String description;

    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy; // Ajout de l'attribut createdBy

    @Column(nullable = false)
    private boolean completed;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User assignedUser;

    @Column(nullable = false)
    private LocalDate createdAt = LocalDate.now();
    ;

    @Column(nullable = false)
    private boolean isConfirmed;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "task_tags",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags = new ArrayList<>();

    public Task() {
    }

    public Task(String title, String description, LocalDate endDate, User assignedUser, User createdBy) {
        this.title = title;
        this.description = description;
        this.endDate = endDate;
        this.assignedUser = assignedUser;
        this.createdBy = createdBy; // Initialiser createdBy ici
        this.createdAt = LocalDate.now(); // Initialiser createdAt ici
        this.completed = false; // Tâche non complétée par défaut
        this.isConfirmed = true; // Initialiser isConfirmed ici
    }


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setDate(LocalDate dueDate) {
        this.endDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public User getAssignedUser() {
        return assignedUser;
    }

    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean confirmed) {
        isConfirmed = confirmed;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getTasks().add(this); // Ajoute la tâche à la liste des tâches du tag
    }

    public void addTags(List<Tag> tags) {
        for (Tag tag : tags) {
            addTag(tag); // Utilise la méthode existante pour ajouter chaque tag
        }
    }


}
