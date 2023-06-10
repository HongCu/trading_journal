package com.stock.app.entity;

import javax.persistence.*;

@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id", nullable = false)
    private Long stock_id;

    @Column(name = "stock_name", nullable = false)
    private String stock_name;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name="income", nullable = true)
    private long income;
}
