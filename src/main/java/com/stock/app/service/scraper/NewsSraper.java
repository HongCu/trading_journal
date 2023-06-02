package com.stock.app.service.scraper;

import java.util.Map;

public interface NewsSraper {
    public Map<String, Object> scrap(String text, String[] fields);
}