package ru.big.intershop.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ViewName {
    PRODUCTS("main"),
    PRODUCT("product"),
    ORDER("order"),
    ORDERS("orders"),
    CART("cart"),
    PAYMENT("payment"),
    PAYMENT_RESULT("payment-result");

    private final String value;
}
