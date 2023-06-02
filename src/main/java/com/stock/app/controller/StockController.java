package com.stock.app.controller;

import com.stock.app.dto.NewsDto;
import com.stock.app.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/stock")
public class StockController {
    private final NewsService newsService;

    /**
     * TODO : 어떤 기능 넣을지 정하기
     * TODO : 주식 실시간 정보 불러오기 + DB에 저장
     * TODO : DB에 저장된 뉴스, description 정보 검색하기
     * TODO : 저장된 데이터 화면에 어떻게 뿌릴지 구성
     */

    @GetMapping
    public String list(Model model) {
        List<NewsDto> newsDtoList = newsService.getNewsList();
        model.addAttribute("newsList", newsDtoList); // postList라는 이름으로 view에 boardDtoList를 넘겨주는 것
        return "trade/news/list.html";
    }
}
