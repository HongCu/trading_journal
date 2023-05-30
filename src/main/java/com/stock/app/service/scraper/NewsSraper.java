package com.stock.app.service.scraper;

import com.stock.app.config.NaverConfig;

import java.util.Map;

public interface NewsSraper {
    public Map<String, Object> scrap(String text, String[] fields);
}