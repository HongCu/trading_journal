package com.stock.app.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id", nullable = false)
    private Long news_id;

    private String title;
    private String description;
    private String originalLink;

    public News(String title, String description, String originalLink) {
        this.title = title;
        this.description = description;
        this.originalLink = originalLink;
    }
}
