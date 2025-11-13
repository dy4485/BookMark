package com.bookmark.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bookmark.entity.Content;
import com.bookmark.entity.User;

public interface ContentRepository extends JpaRepository<Content, Long>{

	    List<Content> findByUser(User user);
	    List<Content> findByUserAndTitleContainingIgnoreCase(User user, String title);
	    List<Content> findByUserAndTagsContainingIgnoreCase(User user, String tag);
	    boolean existsByUrl(String url);
	    Content findTopByOrderByCreatedAtDesc();
	    
	    @Query(value = "SELECT * FROM content c " +
                "WHERE (:query IS NULL OR LOWER(c.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
                "   OR LOWER(c.tags) LIKE LOWER(CONCAT('%', :query, '%'))) " +
                "AND (:fromDate IS NULL OR c.created_at >= :fromDate) " +
                "AND (:toDate IS NULL OR c.created_at <= :toDate)", 
        nativeQuery = true)
 List<Content> searchWithFilters(@Param("query") String query,
                                 @Param("fromDate") LocalDateTime fromDate,
                                 @Param("toDate") LocalDateTime toDate);
}
