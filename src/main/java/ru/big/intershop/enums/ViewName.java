package ru.big.intershop.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ViewName {
    PRODUCTS("main"),
    ORDER("order"),
    PAYMENT("payment"),
    PAYMENT_RESULT("payment-result");

    private final String value;
}
