package com.example.springdeploytest.book.controller;

import com.example.springdeploytest.book.controller.request_form.ListBookRequestForm;
import com.example.springdeploytest.book.controller.request_form.RegisterBookRequestForm;
import com.example.springdeploytest.book.controller.response_form.ListBookResponseForm;
import com.example.springdeploytest.book.controller.response_form.MyListBookResponseForm;
import com.example.springdeploytest.book.controller.response_form.RegisterBookResponseForm;
import com.example.springdeploytest.book.service.BookService;
import com.example.springdeploytest.book.service.request.ListBookRequest;
import com.example.springdeploytest.book.service.request.RegisterBookRequest;
import com.example.springdeploytest.book.service.response.ListBookResponse;
import com.example.springdeploytest.book.service.response.MyListBookResponse;
import com.example.springdeploytest.book.service.response.RegisterBookResponse;
import com.example.springdeploytest.redis_cache.RedisCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {

    final private BookService bookService;
    final private RedisCacheService redisCacheService;

    // 책 등록
    @PostMapping("/register")
    public RegisterBookResponseForm registerBook (
            @RequestHeader ("Authorization") String userToken,
            @RequestBody RegisterBookRequestForm requestForm){

        log.info("registerBook() -> requestForm: {}", requestForm);

        // 인증 정보 체크
        // 유저토큰을 통해 accountId 구하기
        String pureToken = extractToken(userToken);
        String accountId = redisCacheService.getValueByKey(pureToken, String.class);

        /* 다른 방법
         * String pureToken = userToken.replace("Bearer ","").trim();
         * Long accountId = redisCacheService.getValueByKey(pureToken, Long.class);
         */

        // 요청 정보를 가지고 Book 엔티티 구성
        RegisterBookRequest request = requestForm.toRegisterRequestBook();
        RegisterBookResponse response = bookService.register(request, accountId);

        // 책 등록이 잘 되었는지 read 혹은 필요에 따라 list에 적합한 응답 구성
        String message = "책이 정상적으로 등록되었습니다.";
        return RegisterBookResponseForm.form(message, response);
    }

    // 책 전체 조회 - 혼자 한거 -> 페이지네이션이 안되어있다. 수정할 필요가 있음
    @GetMapping("/my-list")
    public ResponseEntity<MyListBookResponseForm> MyListBook (@RequestHeader("Authorization") String userToken){
        String pureToken = extractToken(userToken);
        Long accountId = redisCacheService.getValueByKey(pureToken, Long.class);

        List<MyListBookResponse> listAll = bookService.myList(accountId);
        MyListBookResponseForm result = MyListBookResponseForm.from("전체 책 목록", listAll);

        return ResponseEntity.ok(result);
    }


    private String extractToken(String token){
        if (token != null && token.startsWith("Bearer ")){
            return token.substring(7);
        }
        return token;
    }

    // 책 조회 - 강사님과 한거 (페이지 설정 들어가 있음)
    @GetMapping("/list")
    public ListBookResponseForm bookList (@ModelAttribute ListBookRequestForm requestForm){
        log.info("bookList() -> requestForm: {}", requestForm);

        ListBookRequest request = requestForm.toListBookRequest();
        ListBookResponse response = bookService.list(request);

        return ListBookResponseForm.from(response);

    }

}
