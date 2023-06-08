package com.stock.app.dto;

import com.stock.app.entity.News;
import com.stock.app.entity.Stock;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class StockDto {
    private String stock_name;

    public Stock toEntity() {
        Stock build = Stock.builder()
                .stock_name(stock_name)
                .build();
        return build;
    }

    @Builder
    public StockDto(String stock_name) {
        this.stock_name = stock_name;
    }

}
