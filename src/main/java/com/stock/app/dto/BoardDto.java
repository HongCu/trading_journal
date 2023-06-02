package com.stock.app.dto;

import com.stock.app.entity.Board;
import lombok.Builder;

import java.time.LocalDateTime;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {
    private Long id;
    private String country;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Board toEntity() {
        Board build = Board.builder()
                .id(id)
                .country(country)
                .title(title)
                .content(content)
                .build();
        return build;
    }

    @Builder
    public BoardDto(Long id, String country, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.id = id;
        this.country = country;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}