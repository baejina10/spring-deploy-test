package com.example.springdeploytest.cart.repository;

import com.example.springdeploytest.cart.entity.Cart;
import com.example.springdeploytest.cart.service.response.RegisterCartResponse;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Long> {

    @EntityGraph(attributePaths = "book")
    List<Cart> findAllByAccountId(Long accountId);
}
