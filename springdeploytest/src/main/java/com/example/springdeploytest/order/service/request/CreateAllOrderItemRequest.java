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

    // 기존의 경우 RequestForm -> Request: toRequest()
    // Request -> Entity -> toEntity()
    // Request -> Entity는 동일하나 리스트 형태로 바꾸니까 toEntityList()
    public List<OrderItem> toOrderItemList(List<Book> bookList, Order order) {
        // Map 객체를 구성하였음
        // key: bookId
        // value: Book Entity 그 자체
        // bookList를 stream() 으로 구성
        // Collectors.toMap 사용하므로 HashMap을 구성하겠다는 의미
        // Book::getId를 사용해서 bookId를 확보
        // Function.identity() 객체 자신을 가져옵니다.
        // stream()을 사용하여 자체 for 루프라는 것을 상기해야함.
        Map<Long, Book> bookMap = bookList.stream()
                .collect(Collectors.toMap(Book::getId, Function.identity()));

        // orderItem는 CreateOrderItemRequest의 List
        // map을 사용해서 각각의 요소를 '->' 내부의 동작으로 처리하게 됨
        // return을 보면 toOrderItem이 실행되므로
        // OrderItem Entity를 리스트 형태로 구성해주고 있음.
        return orderItem.stream()
                .map(item -> {
                    // bookMap을 통해 책의 id(bookId) 값으로 Book을 찾음.
                    Book book = bookMap.get(item.getBookId());
                    if(book == null){
                        throw new IllegalArgumentException("이런 책 없다: " + item.getBookId());
                    }

                    // id에 해당하는 Book이 null이 아니라면
                    // OrderItem에 Book Entity와 OrderItem Entity를 배치함
                    // stream을 통해 이뤄지므로 List<OrderItem> 이 만들어지게 됨
                    return item.toOrderItem(book, order);
                })
                .collect(Collectors.toList());
    }
}
