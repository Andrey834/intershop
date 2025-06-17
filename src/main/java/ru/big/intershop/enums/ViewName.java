package ru.big.intershop.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ViewName {
    PRODUCTS("products"),
    PRODUCT("product"),
    CART("cart"),
    ADMIN("admin"),
    ORDER("order"),
    ORDERS("orders"),
    PAYMENT("payment"),
    PAYMENT_RESULT("payment-result"),
    ERROR("error");

    private final String value;
}
