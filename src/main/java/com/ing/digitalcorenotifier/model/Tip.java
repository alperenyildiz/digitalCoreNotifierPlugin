package com.ing.digitalcorenotifier.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public class Tip {
    private final String id;
    private final String content;
    private final String title;
    private final String createdBy;  // "SYSTEM", "AI", "API", veya kullanıcı adı
    private final OffsetDateTime createdDate;
    private int likes;
    private int dislikes;

    public Tip(String title, String content, String createdBy) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
        this.createdDate = OffsetDateTime.now();
        this.likes = 0;
        this.dislikes = 0;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public OffsetDateTime getCreatedDate() {
        return createdDate;
    }

    public int getLikes() {
        return likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    // Methods for feedback
    public void addLike() {
        this.likes++;
    }

    public void addDislike() {
        this.dislikes++;
    }

    @Override
    public String toString() {
        return "Tip{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdAt=" + createdDate +
                '}';
    }
}
