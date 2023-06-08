package com.stock.app.controller;

import com.stock.app.dto.NewsDto;
import com.stock.app.dto.StockDto;
import com.stock.app.service.NewsService;
import com.stock.app.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/stock")
public class StockController {
    private final NewsService newsService;
    private final StockService stockService;

    /**
     * TODO : 어떤 기능 넣을지 정하기
     * TODO : 주식 실시간 정보 불러오기 + DB에 저장
     * TODO : DB에 저장된 뉴스, description 정보 검색하기
     * TODO : 저장된 데이터 화면에 어떻게 뿌릴지 구성
     * TODO : 검색 기능 구현(wildcard -> elasticsearch)
     */

    @GetMapping("/search")
    public String searchDocuments(@RequestParam("query") String query, Model model) {
        List<NewsDto> newsDtoList = newsService.searchNews(query);
        model.addAttribute("newsList", newsDtoList); // postList라는 이름으로 view에 boardDtoList를 넘겨주는 것
        return "trade/news/list.html";
    }

    @GetMapping("/news")
    public String newsList(Model model) {
        List<NewsDto> newsDtoList = newsService.getNewsList();
        model.addAttribute("newsList", newsDtoList); // postList라는 이름으로 view에 boardDtoList를 넘겨주는 것
        return "trade/news/list.html";
    }

    @GetMapping("/list")
    public String stockList(Model model) {
        List<StockDto> stockDtoList = stockService.getStockList();
        model.addAttribute("stockList", stockDtoList); // postList라는 이름으로 view에 boardDtoList를 넘겨주는 것
        return "trade/stock/list.html";
    }
}
