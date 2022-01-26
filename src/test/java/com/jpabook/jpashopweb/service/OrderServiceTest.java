package com.jpabook.jpashopweb.service;

import com.jpabook.jpashopweb.domain.Address;
import com.jpabook.jpashopweb.domain.Member;
import com.jpabook.jpashopweb.domain.Order;
import com.jpabook.jpashopweb.domain.OrderStatus;
import com.jpabook.jpashopweb.domain.item.Book;
import com.jpabook.jpashopweb.domain.item.Item;
import com.jpabook.jpashopweb.exception.NotEnoughStockException;
import com.jpabook.jpashopweb.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;
    @Autowired EntityManager em;

    @Test
    public void 상품주문() {
        //given
        Member member = createMember();

        Book book = createBook("노원 JPA", 10000, 10);

        int orderCount = 2;

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류수가 정확해야 한다");
        assertEquals(10000*orderCount, getOrder.getTotalPrice(), "주문 가격은 가격 * 수량이다");
        assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
    }

    @Test(expected = NotEnoughStockException.class)
    public void 상품주문_재고수량초과() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("노원 JPA", 10000, 10);

        int orderCount = 11;

        //when
        Long order = orderService.order(member.getId(), item.getId(), orderCount);

        //then
        fail("예외가 발생해야 한다.");
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook("JPA", 10000, 10);

        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, getOrder.getStatus(), "주문 취소시 상태는 CANCEL 이다.");
        assertEquals(10, item.getStockQuantity(), "주문이 취소된 상품은 그만큼 재고가 증가해야 한다.");
    }

    private Book createBook(String name, int price, int stockQuantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockQuantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("회원1");
        member.setAddress(new Address("서울", "노원", "123-123"));
        em.persist(member);
        return member;
    }
}