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
import com.example.springdeploytest.order.service.request.*;
import com.example.springdeploytest.order.service.response.CartOrderResponse;
import com.example.springdeploytest.order.service.response.CreateAllOrderResponse;
import com.example.springdeploytest.order.service.response.ListOrderResponse;
import com.example.springdeploytest.order.service.response.SingleOrderResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

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

        // accountId로 Account 찾기
        Long accountId = orderRequest.getAccountId();
        Account account = accountRepository.findById(accountId)
                .orElseThrow(()-> new RuntimeException("존재하지 않는 사용자입니다."));

        // Order의 경우 사용자가 유효하니 즉시 생성
        Order order = orderRequest.toOrder(account);
        Order savedOrder = orderRepository.save(order);

        // 실제 orderItemRequest에는 {id .... }, {id .... }, {id .... } 형식으로 배치될 것이므로
        // id만 따로 빼놓은 리스트를 구성하기 위해 아래와 같은 작업을 진행함.
        // getBookId를 통해 bookId 값만 뽑아오고 List로 만듬
        List<Long> bookIdList = orderItemRequest.getOrderItem().stream()
                .map(CreateOrderItemRequest::getBookId)
                .distinct()
                .collect(Collectors.toList());

        // findAll -> 조금 더 확장된 개념인 findAllById가 나타남.
        // findAll과 하는 일은 동일하지만 조건이 추가된 형식이라 보면 됩니다.
        // 조건은 bookIdList에 해당하는 모든 정보를 검색합니다.
        List<Book> bookList = bookRepository.findAllById(bookIdList);
        if (bookList.size() != bookIdList.size()) {
            throw new IllegalArgumentException("존재하지 않는 책이 포함되었습니다.");
        }

        // Order는 주문 그 자체이므로 1개일 수 밖에 없음
        // 그러나 주문에 포함된 항목들은 다수일 수 있음.
        // 그렇기에 OrderItem은 리스트 형식으로 구성됨.
        // OrderItem 리스트를 저장하기 위해 save가 아닌 saveAll로 bulk 연산함(다중처리)
        // for 루프 돌면서 save 하는 것과 saveAll의 차이는 결국 데이터 무결성을 보장하기 위함
        // 데이터 무결성이라는 것이 1 + 1 = 2 가 나와야 하는데
        // 1 + 1 = 1 이 나오는 괴현상을 나타나지 않게 해주는 것임.
        // 좀 더 전문 용어로는 Lock을 걸어서 데이터를 안전하게 보호한다 보면 되는데
        // 그냥 다수의 데이터를 처리할 때는 무조건 saveAll을 사용한다 생각하는 것이 속편함.
        List<OrderItem> orderItemList = orderItemRequest.toOrderItemList(bookList, savedOrder);
        List<OrderItem> savedOrderItemList = orderItemRepository.saveAll(orderItemList);

        return CreateAllOrderResponse.from(savedOrder,savedOrderItemList);
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

    @Override
    public ListOrderResponse list(ListOrderRequest request) {
        // PageRequest 생성
        PageRequest pageable = PageRequest.of(request.getPage() - 1, request.getPerPage(),
                Sort.by("created").descending());

        Long accountId = request.getAccountId();
        Page<Order> pagedOrder = orderRepository.findAllByAccountId(accountId, pageable);
        List<Order> pagedOrderList = pagedOrder.getContent();

        List<OrderItem> pageOrderItemList = orderItemRepository.findByOrderIn(pagedOrderList);

        return ListOrderResponse.from(
                pagedOrderList,
                pageOrderItemList,
                pagedOrder.getTotalPages(),
                pagedOrder.getTotalElements()
        );
    }

}
