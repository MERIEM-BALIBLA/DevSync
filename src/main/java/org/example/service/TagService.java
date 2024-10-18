package org.example.service;

import org.example.model.Tag;
import org.example.repository.implementation.TagRepository;
import org.example.util.ExeptionHandler;

import java.util.List;

public class TagService {

    private final TagRepository tagRepository;


    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<Tag> getAllTag() {
        return tagRepository.getAllTag();
    }

    public Tag findByTitle(String title) {
        return tagRepository.findByTitle(title);
    }

    public Tag findById(String tagId) {
        Long id = Long.parseLong(tagId);
        return tagRepository.findById(id);
    }

    public Tag insert(Tag tag) {
        if (tag==null){
            throw new ExeptionHandler("the object is empty");
        }
        return tagRepository.insert(tag);
    }

    public int count(){
        return this.getAllTag().size();
    }
}
