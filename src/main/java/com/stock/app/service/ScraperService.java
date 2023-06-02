package com.stock.app.service;

import com.stock.app.config.NaverAPIConfig;
import com.stock.app.entity.News;
import com.stock.app.repository.NewsRepository;
import com.stock.app.service.scraper.NewsScraperImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScraperService {

    private final NewsScraperImpl newsScraper;
    private final NewsRepository newsRepository;

    public Map<String, Object> scrap(String text, String[] fields) {
        String clientId = NaverAPIConfig.client.getId();
        String clientSecret = NaverAPIConfig.client.getSecret();

        try {
            text = URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/news?query=" + text;    // JSON 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = newsScraper.get(apiURL, requestHeaders);

        Map<String, Object> response = newsScraper.getResult(responseBody, fields);

        saveNewsMap(response);


        return response;
    }

    public News saveNews(final News newNews) throws SQLIntegrityConstraintViolationException {
        if(newNews == null) throw new IllegalArgumentException("News cannot be null");
        return newsRepository.save(newNews);
    }

    @Transactional
    public void saveNewsMap(Map<String, Object> response) {
        // response에서 값 추출하여 저장
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) response.get("result");

        for (Map<String, Object> result : resultList) {
            News news = new News();
            String title = (String) result.get("title");
            String originalLink = (String) result.get("originallink");
            String description = (String) result.get("description");

            news.setOriginalLink(removeHtmlTags(originalLink));
            news.setDescription(removeHtmlTags(description));
            news.setTitle(removeHtmlTags(title));

            /**
             * TODO SQLIntegrityConstraintViolationException 뭔지 제대로 파악하기
             */
            try {
                saveNews(news);
            } catch (SQLIntegrityConstraintViolationException e) {
                log.error("중복된 데이터 들어옴");
                return;
            } catch (IllegalArgumentException e) {
                log.info("null 불가능");
            }

            News byId = newsRepository.findById(news.getNews_id()).get();

            log.info("저장 Id={}", byId);
        }
    }

    public String removeHtmlTags(String input) {
        // HTML 태그를 제거하는 정규식 패턴
        String htmlPattern = "<[^>]*>";
        // 정규식을 사용하여 HTML 태그를 제거
        String result = input.replaceAll(htmlPattern, "");
        return result;
    }

}
