package com.stock.app.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id", nullable = false)
    private Long stock_id;

    @Column(name = "stock_name")
    private String stock_name;


    @Builder
    public Stock(String stock_name) {
        this.stock_name = stock_name;
    }
}
