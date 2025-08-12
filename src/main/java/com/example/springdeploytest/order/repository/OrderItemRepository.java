package com.example.springdeploytest.order.repository;

import com.example.springdeploytest.order.entity.Order;
import com.example.springdeploytest.order.entity.OrderItem;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem,Long> {
    @Query("select oi from OrderItem oi join fetch oi.book join fetch oi.order")
    List<OrderItem> findByOrderIn(List<Order> pagedOrderList);
}
