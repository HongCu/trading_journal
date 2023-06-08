package com.stock.app.controller;

import com.stock.app.dto.BoardDto;
import com.stock.app.entity.News;
import com.stock.app.entity.Stock;
import com.stock.app.repository.NewsRepository;
import com.stock.app.repository.StockRepository;
import com.stock.app.service.ScraperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/stock")

public class ScrapController {
    private final ScraperService scraperService;
    private final NewsRepository newsRepository;
    private final StockRepository stockRepository;


    @GetMapping("/scrap")
    public String scrapNews(@RequestParam String query) {

        try {
            // query Stock table에 추가
            stockRepository.save(new Stock(query));
            String[] fields = {"title", "originallink", "description"};
            scraperService.scrap(query, fields);
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/";
        }
            return "redirect:/";
    }


    @GetMapping("/all")
    public ResponseEntity<List<News>> getAllData() {
        List<News> dataList = newsRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(dataList);
    }

    /**
     * Todo : 수집된 주식 목록 불러오기.
     */
}
