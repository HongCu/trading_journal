package com.stock.app.controller;

import com.stock.app.service.scraper.NewsSraper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/trading")

public class ScrapController {
    @Qualifier("NewsScraperImpl")
    private final NewsSraper newsScraper;

    @Transactional
    @GetMapping("/scrap")
    public ResponseEntity<String> scrapNews(@RequestParam String query) throws Exception {
        String response = newsScraper.scrap(query);
//        String response = "query 수집 완료";
        return ResponseEntity.ok(response);
    }

}
