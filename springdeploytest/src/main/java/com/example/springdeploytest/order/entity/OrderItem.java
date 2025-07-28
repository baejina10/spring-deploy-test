package com.example.springdeploytest.order.entity;

import com.example.springdeploytest.account.entity.Account;
import com.example.springdeploytest.book.entity.Book;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class OrderItem {
    // OrderItem의 경우엔 수량, 항목(bookId), 가격, 어디의 주문인지 여부 (ordersId)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private int itemTotalPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    public OrderItem(Book book, Order order, int quantity) {
        this.book = book;
        this.order = order;
        this.quantity = quantity;
        this.itemTotalPrice = book.getPrice() * quantity;
    }

}
