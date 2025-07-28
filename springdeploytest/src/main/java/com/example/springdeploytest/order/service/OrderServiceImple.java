package com.example.springdeploytest.order.service;

import com.example.springdeploytest.account.entity.Account;
import com.example.springdeploytest.account.repository.AccountRepository;
import com.example.springdeploytest.book.entity.Book;
import com.example.springdeploytest.book.repository.BookRepository;
import com.example.springdeploytest.cart.entity.Cart;
import com.example.springdeploytest.cart.repository.CartRepository;
import com.example.springdeploytest.order.entity.Order;
import com.example.springdeploytest.order.entity.OrderItem;
import com.example.springdeploytest.order.repository.OrderItemRepository;
import com.example.springdeploytest.order.repository.OrderRepository;
import com.example.springdeploytest.order.service.request.CreateAllOrderItemRequest;
import com.example.springdeploytest.order.service.request.CreateAllOrderRequest;
import com.example.springdeploytest.order.service.request.CreateOrderItemRequest;
import com.example.springdeploytest.order.service.request.OrderRequest;
import com.example.springdeploytest.order.service.response.CartOrderResponse;
import com.example.springdeploytest.order.service.response.CreateAllOrderResponse;
import com.example.springdeploytest.order.service.response.SingleOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImple implements OrderService{

    final private OrderRepository orderRepository;
    final private BookRepository bookRepository;
    final private AccountRepository accountRepository;
    final private CartRepository cartRepository;
    final private OrderItemRepository orderItemRepository;

    @Override
    public SingleOrderResponse order(OrderRequest request, String accountId) {

        // 사용자 확인하고 정보 받기
        Account account = accountRepository.findById(Long.parseLong(accountId))
                .orElseThrow(()-> new RuntimeException("존재하지 않는 사용자 입니다."));
        // 책 확인하고 정보 받기
        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(()-> new RuntimeException("등록되어 있지 않는 책입니다."));

        // 주문 db에 account_id 정보 넣는 코드
        // => order에 account 정보를 초기화해 객체 생성하고, 그 객체를 생성한걸 db에 넣어 객체 생성
        Order requestOrder = request.toOrder(account);
        Order savedOrder = orderRepository.save(requestOrder);

        // 주문할 책이 여러개 일 수 있으니 orderItem 이라는 엔티티를 따로 구성
        // order에는 책 정보를 넣을 필드멤버가 없으니 책 필드가 있는 OrderItem을 객체를 생성해서 저장
        OrderItem requestOrderIdem = request.toOrderItem(book,savedOrder);
        OrderItem savedOrderIdem = orderItemRepository.save(requestOrderIdem);

        return SingleOrderResponse.from(savedOrderIdem,savedOrder);
    }

    @Override
    @Transactional
    public CreateAllOrderResponse createAll(CreateAllOrderRequest orderRequest, CreateAllOrderItemRequest orderItemRequest) {
        Long accountId = orderRequest.getAccountId();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()-> new RuntimeException("존재하지 않는 사용자입니다."));

        Order order = orderRequest.toOrder(account);
        orderRepository.save(order);

        List<Long> bookIdList = orderItemRequest.getOrderItem().stream()
                .map(CreateOrderItemRequest::getBookId)
                .distinct()
                .collect(Collectors.toList());

        List<Book> bookList = bookRepository.findAllById(bookIdList);
        if (bookList.size() != bookIdList.size()) {
            throw new IllegalArgumentException("존재하지 않는 책이 포함되었습니다.");
        }
        List<OrderItem> orderItemList = orderItemRequest.toOrderItemList(bookList, order);
        List<OrderItem> savedOrderItemList = orderItemRepository.saveAll(orderItemList);

        return CreateAllOrderResponse.from(order,savedOrderItemList);
    }

    @Override
    @Transactional
    public List<CartOrderResponse> orderFromCart(Long accountId) {

        // 사용자 확인하고 정보 받기
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()-> new RuntimeException("존재하지 않는 사용자 입니다."));

        // 로그인한 사용자가 DB에 담긴 장바구니 리스트를 꺼내서 cartList에 담는다.
        List<Cart> cartList = cartRepository.findAllByAccountId(accountId);
        // 장바구니가 비어있는지 확인, 없으면 예외처리 한다.
        if (cartList.isEmpty()) {
            throw new RuntimeException("장바구니가 비어있습니다.");
        }
        // 꺼낸 장바구니 정보를 주문 DB 에 담는다.

        // account 정보를 order에 객체 생성하고 db에 저장,
        List<Order> savedOrders = new ArrayList<>();
        Order accountSave = new Order(account);
        Order savedOrder = orderRepository.save(accountSave);
        savedOrders.add(savedOrder);

        // order, book, 수량을 cartList에 꺼내서 orderItem에 객체 생성하며 초기화하고 db에 저장
        List<OrderItem> savedOrderItems = new ArrayList<>();
        for (Cart cart : cartList) {
            OrderItem orderItem = new OrderItem(cart.getBook(),savedOrder,cart.getQuantity());
            OrderItem savedOrderItem = orderItemRepository.save(orderItem);
            savedOrderItems.add(savedOrderItem);
        }
        // 총 가격 계산 후 저장
        savedOrder.calculateTotalPrice(savedOrderItems);
        orderRepository.save(savedOrder);

        // 장바구니를 비우는 작업
        cartRepository.deleteAll(cartList);

        return CartOrderResponse.from(savedOrderItems, savedOrders);
    }

    // 주문 전체 가격 구하는 메서드
//    private int orderTotalPrice (Book book, OrderRequest request){
//        return book.getPrice() * request.getQuantity();
//    }
}
