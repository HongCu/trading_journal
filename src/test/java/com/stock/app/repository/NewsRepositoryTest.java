package com.stock.app.repository;


import com.stock.app.entity.News;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NewsRepositoryTest {

    @Autowired
    private NewsRepository newsRepository;

    @Test
    public void NewsTest() {
        News news1 = new News();
        news1.builder().url("www.news.com").content("레이크머티리얼즈").title("레이크").build();
        newsRepository.save(news1);

        News savedNews = newsRepository.findById(news1.getNews_id()).get();
        Assertions.assertEquals(savedNews, news1);
    }

}
