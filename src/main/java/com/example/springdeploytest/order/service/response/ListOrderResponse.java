package com.example.springdeploytest.order.service.response;

import com.example.springdeploytest.order.controller.response_form.ListOrderResponseForm;
import com.example.springdeploytest.order.entity.Order;
import com.example.springdeploytest.order.entity.OrderItem;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class ListOrderResponse {
    final private List<Order> pagedOrderList;
    final private List<OrderItem> pagedOrderItemList;
    final private Integer totalPages;
    final private Long totalElement;


    public ListOrderResponseForm transformToResponseForm() {

        List<Map<String, Object>> orderSummaryList = pagedOrderList.stream()
                .map(order -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("orderId", order.getId());
                    map.put("created", order.getCreated());

                    List<OrderItem> itemsForOrder = pagedOrderItemList.stream()
                            .filter(orderItem -> orderItem.getOrder().getId().equals(order.getId()))
                            .collect(Collectors.toList());

                    // A-B-C-D, DEF-FUNCTION, CLASS-CONSTRUCTION, MAKE-COFFEE
                    // 위와 같이 책 이름이 전부 긴 경우 아래와 같이 처리하기도 함
                    // A-B-C-D, DEF-FUNCTION 외 다수
                    // 혹은 A-B-C-D, DEF-FUNCTION ....
                    // 또는 A-B-C-D, DEF-FUNCTION 외 2건
                    String bookNameList = itemsForOrder.stream()
                            .map(item -> item.getBook().getTitle())
                            .collect(Collectors.joining(", "));

                    if(bookNameList.length() > 20) {
                        bookNameList = bookNameList.substring(0, 20) + "......";
                    }

                    int totalPrice = itemsForOrder.stream()
                                .mapToInt(item -> item.getItemTotalPrice())
                                .sum();

                    map.put("orderName", bookNameList);
                    map.put("totalPrice", totalPrice);

                    return map;

                })
                .collect(Collectors.toList());

        return new ListOrderResponseForm(
                orderSummaryList,
                totalPages,
                totalElement
        );

    }



    public static ListOrderResponse from(
            final List<Order> pagedOrderList,
            final List<OrderItem> pagedOrderItemList,
            final Integer totalPages,
            final Long totalElement) {

        return new ListOrderResponse(
                pagedOrderList,
                pagedOrderItemList,
                totalPages,
                totalElement
        );
    }

}