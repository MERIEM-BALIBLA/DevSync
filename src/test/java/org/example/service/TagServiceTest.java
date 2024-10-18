package org.example.service;

import org.example.repository.implementation.TagRepository;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.example.model.Tag;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TagServiceTest {

    @InjectMocks
    private TagService tagService;

    @Mock
    private TagRepository tagRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTags() {
        // Arrange: create some tags and mock repository method
        Tag tag1 = new Tag(1, "Tag 1");
        Tag tag2 = new Tag(2, "Tag 2");
        List<Tag> expectedTags = Arrays.asList(tag1, tag2);

        // Mock repository call
        when(tagRepository.getAllTag()).thenReturn(expectedTags);

        // Act: call the service method
        List<Tag> actualTags = tagService.getAllTag();

        // Assert: verify that the returned result matches what is expected
        assertNotNull(actualTags, "The list of tags should not be null.");
        assertEquals(2, actualTags.size(), "The number of tags should be 2.");
        assertEquals(expectedTags, actualTags, "The actual tags should match the expected tags.");

        // Verify that the repository method was called exactly once
        verify(tagRepository, times(1)).getAllTag();
    }

    @Test
    void insert() {
        Tag tag1 = new Tag(1, "Tag 1");
        when(tagRepository.insert(tag1)).thenReturn(tag1);
        Tag insertTag = tagService.insert(tag1);
        assertNotNull(insertTag); // Ensure the result is not null
        assertEquals(tag1.getId(), insertTag.getId()); // Verify ID
        assertEquals(tag1.getTitle(), insertTag.getTitle()); // Verify name
        verify(tagRepository, times(1)).insert(tag1); // En
    }

}
