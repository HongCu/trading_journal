package com.stock.app.entity;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name = "stock_name")
    private String stockName;

    @Column(name = "income")
    private BigDecimal income;

    @Column(name = "average_unit_price")
    private BigDecimal averageUnitPrice;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    private List<Board> boards;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL)
    private List<Trade> trades;

    @Builder
    public Stock(String stock_name) {
        this.stockName = stock_name;
    }
}
