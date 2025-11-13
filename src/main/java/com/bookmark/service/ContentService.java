package com.bookmark.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookmark.entity.Content;
import com.bookmark.entity.User;
import com.bookmark.repository.ContentRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    public Content saveContent(Content content) {
        return contentRepository.save(content);
    }

    public List<Content> getContentsByUser(User user) {
        return contentRepository.findByUser(user);
    }

    public Optional<Content> getContentById(Long id) {
        return contentRepository.findById(id);
    }

    public void deleteContent(Long id) {
        contentRepository.deleteById(id);
    }

    public List<Content> searchContentsByTitle(User user, String title) {
        return contentRepository.findByUserAndTitleContainingIgnoreCase(user, title);
    }

    public List<Content> searchContentsByTag(User user, String tag) {
        return contentRepository.findByUserAndTagsContainingIgnoreCase(user, tag);
    }
    public boolean existsByUrl(String url) {
        return contentRepository.existsByUrl(url);
    }
    public Content getLatestContent() {
        return contentRepository.findTopByOrderByCreatedAtDesc();
    }
    
    public List<Content> search(String query, LocalDate fromDate, LocalDate toDate) {
        LocalDateTime fromDateTime = (fromDate != null) ? fromDate.atStartOfDay() : null;
        LocalDateTime toDateTime = (toDate != null) ? toDate.atTime(LocalTime.MAX) : null;

        return contentRepository.searchWithFilters(query, fromDateTime, toDateTime);
    }

}