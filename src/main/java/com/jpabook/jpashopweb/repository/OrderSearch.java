package com.jpabook.jpashopweb.repository;

import com.jpabook.jpashopweb.domain.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {

    private String memberName;
    private OrderStatus orderStatus;
}
