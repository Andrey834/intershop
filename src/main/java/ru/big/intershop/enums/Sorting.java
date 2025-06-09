package ru.big.intershop.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Sorting {
    ID("id"),
    PRICE("price"),
    ALPHABETICAL("title");

    private final String value;
}
