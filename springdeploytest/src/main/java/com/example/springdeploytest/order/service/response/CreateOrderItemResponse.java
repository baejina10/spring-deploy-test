package com.example.springdeploytest.order.service.response;

import com.example.springdeploytest.book.entity.Book;
import com.example.springdeploytest.order.entity.OrderItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CreateOrderItemResponse {
    final private Long bookId;
    final private String bookTitle;
    final private int quantity;
    final private int totalPrice;

    public static CreateOrderItemResponse from(final OrderItem orderItem) {
        Book orderedBook = orderItem.getBook();

        return new CreateOrderItemResponse(
                orderedBook.getId(),
                orderedBook.getTitle(),
                orderItem.getQuantity(),
                orderItem.getItemTotalPrice()
        );
    }

}
