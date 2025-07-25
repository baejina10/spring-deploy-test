package com.example.springdeploytest.order.repository;

import com.example.springdeploytest.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
