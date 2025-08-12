package com.example.springdeploytest.order.entity;

import com.example.springdeploytest.account.entity.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Orders")
@Getter
@NoArgsConstructor
public class Order {
    // 다중 주문
    // 주문 내역 <- 주문 하나 (Order) 내부에 여러 개의 주문 항목(OrderItem이 배치됨)
    // Order의 경우 이 주문이 누구의 것인지 알면 됨(Account)
    // 주문 일자를 토대로 언제 주문이 되었고 배송이 지연되었는지 여부를 파악할 수도 있음.

    // OrderItem의 경우엔 수량, 항목(bookId), 가격, 어디의 주문인지 여부 (ordersId)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private int totalPrice;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @CreationTimestamp
    private LocalDateTime created;

    public Order(Account account) {
        this.account = account;
    }

    public void calculateTotalPrice(List<OrderItem> orderItems) {
        int total = orderItems.stream()
                .mapToInt(item -> item.getItemTotalPrice()).sum();
        this.totalPrice = total;
    }


//    다른 예시 (for문을 사용해서)

//    private void calculateTotalPrice(List<OrderItem> orderItems) {
//        int total = 0;
//        for (OrderItem orderItem : orderItems) {
//          total = total + orderItem.getItemTotalPrice();
//        }
//        this.totalPrice = total;
//    }


}
