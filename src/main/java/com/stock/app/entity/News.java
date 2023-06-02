package com.stock.app.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "news_id", nullable = false)
    private Long news_id;

    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "link", unique=true, length = 200)
    private String originalLink;

    @Builder
    public News(String title, String description, String originalLink) {
        this.title = title;
        this.description = description;
        this.originalLink = originalLink;
    }
}
