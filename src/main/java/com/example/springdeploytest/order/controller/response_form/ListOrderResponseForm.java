package com.example.springdeploytest.order.controller.response_form;

import com.example.springdeploytest.order.service.response.ListOrderResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class ListOrderResponseForm {
    final private List<Map<String, Object>> orderSummaryList;
    final private Integer totalPages;
    final private Long totalElement;

    public static ListOrderResponseForm from(final ListOrderResponse response) {
        return response.transformToResponseForm();
    }



}
