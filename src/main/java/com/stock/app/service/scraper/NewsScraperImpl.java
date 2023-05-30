package com.stock.app.service.scraper;

import com.stock.app.config.NaverConfig;
import com.stock.app.entity.News;
import com.stock.app.repository.NewsRepository;
import com.stock.app.service.ScraperService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import javax.json.JsonObject;
import javax.json.stream.JsonParser;
import javax.transaction.Transactional;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsScraperImpl implements NewsSraper{

    private final ScraperService scraperService;
    private final NewsRepository newsRepository;

    @Override
    public Map<String, Object> scrap(String text, String[] fields) {
        String clientId = NaverConfig.client.getId();
        String clientSecret = NaverConfig.client.getSecret();

        try {
            text = URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패",e);
        }

        String apiURL = "https://openapi.naver.com/v1/search/news?query=" + text;    // JSON 결과

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL, requestHeaders);

        Map<String, Object> response = getResult(responseBody, fields);
        saveNews(response);

        return response;
        /**
         * TODO : news repository에 저장  : Ok
         * TODO : JSON 형태로 변환  : Ok
         */
    }

    @Transactional
    public void saveNews(Map<String, Object> response) {
        // response에서 값 추출하여 저장
        List<Map<String, Object>> resultList = (List<Map<String, Object>>) response.get("result");

        for (Map<String, Object> result : resultList) {
            News news = new News();
            String title = (String) result.get("title");
            String originalLink = (String) result.get("originallink");
            String description = (String) result.get("description");

            news.setOriginalLink(originalLink);
            news.setDescription(description);
            news.setTitle(title);

            scraperService.saveNews(news);

            News byId = newsRepository.findById(news.getNews_id()).get();

            log.info("저장 Id={}", byId);
        }
    }

    private Map<String, Object> getResult(String responseBody, String[] fields) {

        Map<String, Object> resultMap = new HashMap<>();
        try {
            JSONParser parser = new JSONParser();
            JSONObject result = (JSONObject) parser.parse(responseBody);

            resultMap.put("total", (long) result.get("total"));
            JSONArray items = (JSONArray) result.get("items");

            List<Map<String, Object>> itemList = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                JSONObject item = (JSONObject) items.get(i);
                Map<String, Object> itemMap = new HashMap<>();

                for(String field : fields) {
                    itemMap.put(field, item.get(field));
                }
                itemList.add(itemMap);
            }
            resultMap.put("result", itemList);

        } catch (ParseException e) {
            log.error("getResult Error={}", e.getMessage());
        }

        return resultMap;

    }

    private String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }

        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();
            String line;

            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }
            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
