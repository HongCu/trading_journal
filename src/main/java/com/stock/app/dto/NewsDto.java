package com.stock.app.dto;

import com.stock.app.entity.Board;
import com.stock.app.entity.News;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NewsDto {

    private String title;
    private String description;
    private String originalLink;

    public News toEntity() {
        News build = News.builder()
                .title(title)
                .description(description)
                .originalLink(originalLink)
                .build();
        return build;
    }

    @Builder
    public NewsDto(String title, String description, String originalLink) {
        this.title = title;
        this.description = description;
        this.originalLink = originalLink;
    }
}
