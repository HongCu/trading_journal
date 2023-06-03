package com.stock.app.service;

import com.stock.app.dto.NewsDto;
import com.stock.app.entity.News;
import com.stock.app.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;

    @Transactional
    public List<NewsDto> getNewsList() {
        List<News> newsList = newsRepository.findAll();
        List<NewsDto> newDtoList = new ArrayList<>();

        for(News news : newsList) {
            NewsDto newsDto = NewsDto.builder()
                    .title(news.getTitle())
                    .description(news.getDescription())
                    .originalLink(news.getOriginalLink())
                    .build();
            newDtoList.add(newsDto);
        }
        return newDtoList;
    }

    @Transactional
    public List<NewsDto> searchNews(String query) {
        List<News> newsList = newsRepository.findByTitleContainingOrDescriptionContaining(query, query);
        List<NewsDto> newDtoList = new ArrayList<>();

        for(News news : newsList) {
            NewsDto newsDto = NewsDto.builder()
                    .title(news.getTitle())
                    .description(news.getDescription())
                    .originalLink(news.getOriginalLink())
                    .build();
            newDtoList.add(newsDto);
        }
        return newDtoList;
    }
}
