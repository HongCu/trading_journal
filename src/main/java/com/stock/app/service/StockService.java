package com.stock.app.service;

import com.stock.app.dto.NewsDto;
import com.stock.app.dto.StockDto;
import com.stock.app.entity.News;
import com.stock.app.entity.Stock;
import com.stock.app.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    @Transactional
    public List<StockDto> getStockList() {
        List<Stock> stockList = stockRepository.findAll();
        List<StockDto> stockDtoList = new ArrayList<>();

        for(Stock stock : stockList) {
            StockDto stockDto = StockDto.builder()
                    .stock_name(stock.getStock_name())
                    .build();
            stockDtoList.add(stockDto);
        }
        return stockDtoList;
    }

}
