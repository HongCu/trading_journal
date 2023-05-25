package com.stock.app.service;

import com.stock.app.entity.News;
import com.stock.app.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScraperService {
    private final NewsRepository newsRepository;

    public News saveNews(final News newNews) {
        if(newNews == null) throw new IllegalArgumentException("News cannot be null");
        return newsRepository.save(newNews);
    }

}
