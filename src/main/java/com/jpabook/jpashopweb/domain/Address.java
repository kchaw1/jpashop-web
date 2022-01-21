package com.jpabook.jpashopweb.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    protected Address() { // 값타입은 기본생성자가 필요함 접근제어자는 public, protected 주로 protected 를 사용
    }

    public Address(String city, String street, String zipcode) { // 값타입은 변경 불가능하게 설계한다. @Setter를 제거하고 객체 생성시에만 값을 할당 하도록 생성자 활용
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
