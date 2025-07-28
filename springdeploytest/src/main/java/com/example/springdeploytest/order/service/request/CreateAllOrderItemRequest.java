package com.example.springdeploytest.order.service.request;

import com.example.springdeploytest.book.entity.Book;
import com.example.springdeploytest.order.entity.Order;
import com.example.springdeploytest.order.entity.OrderItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class CreateAllOrderItemRequest {
    final private List<CreateOrderItemRequest> orderItem;

    public List<OrderItem> toOrderItemList(List<Book> bookList, Order order) {
        Map<Long, Book> bookMap = bookList.stream()
                .collect(Collectors
                        .toMap(Book::getId, Function.identity()));

        return orderItem.stream()
                .map(item ->{
                    Book book = bookMap.get(item.getBookId());
                    if(book == null){
                        throw new IllegalArgumentException("이런 책 없다: " + item.getBookId());
                    }
                    return item.toOrderItem(book, order);
                })
                .collect(Collectors.toList());
    }
}
