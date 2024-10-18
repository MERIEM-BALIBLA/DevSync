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

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "tags")
    private List<Task> taskList;

    public Tag() {
    }

    public Tag(int id, String title) {
        this.id = id;
        this.title = title;
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

}
