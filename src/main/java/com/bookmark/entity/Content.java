package com.bookmark.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "content")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    @Column(nullable = false, length = 20)
    private String platform; 

    @Column(nullable = false, columnDefinition = "TEXT")
    private String url;

    @Column(length = 255)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String thumbnail;

    @Column(length = 100)
    private String creator;

    @Column(columnDefinition = "TEXT")
    private String tags; 

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

	
}
