package org.example.service;

import org.example.model.Tag;
import org.example.model.Task;
import org.example.repository.implementation.TagRepository;
import org.example.repository.implementation.TaskRepository;

import java.util.ArrayList;
import java.util.List;

public class TagService {

    private TagRepository tagRepository;
    private TaskRepository taskRepository;

    public TagService() {
        this.tagRepository = new TagRepository();
        this.taskRepository = new TaskRepository();
    }

    public List<Tag> getAllTag() {
        return tagRepository.getAllTag();
    }

    public Tag findByTitle(String title) {
        return tagRepository.findByTitle(title);
    }

    public Tag findById(String tagId) {
        Long id = Long.parseLong(tagId); // Assurez-vous que c'est le bon type
        return tagRepository.findById(id); // Supposons que vous ayez cette méthode dans votre TagRepository
    }

    public Tag insert(Tag tag) {
        return tagRepository.insert(tag);
    }

    public Tag merge(Tag tag) {
        return tagRepository.merge(tag);
    }
}
