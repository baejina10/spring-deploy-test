package com.example.springdeploytest.order.entity;

import com.example.springdeploytest.account.entity.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Orders")
@Getter
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private int totalPrice;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    public Order(Account account) {
        this.account = account;
    }

    private void calculateTotalPrice(List<OrderItem> orderItems) {
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
