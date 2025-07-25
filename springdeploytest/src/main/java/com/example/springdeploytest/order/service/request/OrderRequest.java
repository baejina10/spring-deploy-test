package com.example.springdeploytest.order.service.request;

import com.example.springdeploytest.account.entity.Account;
import com.example.springdeploytest.book.entity.Book;
import com.example.springdeploytest.order.entity.Order;
import com.example.springdeploytest.order.entity.OrderItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderRequest {
    final private int quantity;
    final private Long bookId;

    public Order toOrder(Account account) {
        return new Order (account);
    }

    public OrderItem toOrderItem(Book book,Order order) {
        return new OrderItem(book, order, quantity);
    }
}
