package com.stock.app.controller;

import com.stock.app.entity.News;
import com.stock.app.repository.NewsRepository;
import com.stock.app.service.ScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/trading")

public class ScrapController {
    private final ScraperService scraperService;
    private final NewsRepository newsRepository;

    @GetMapping("/scrap")
    public ResponseEntity<Map<String, Object>> scrapNews(@RequestParam String query) throws Exception {

        String[] fields = {"title", "originallink", "description"};
        Map<String, Object> data = scraperService.scrap(query, fields);

        return ResponseEntity.status(HttpStatus.OK).body(data);
    }

    @GetMapping("/all")
    public ResponseEntity<List<News>> getAllData() {
        List<News> dataList = newsRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(dataList);
    }

    /**
     * Todo : 수집된 뉴스 불러오기.
     */
}
