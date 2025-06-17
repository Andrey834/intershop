package ru.big.intershop.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor
public enum DateTimeFormat {
    FOR_VIEW_FULL(DateTimeFormatter.ofPattern("dd MMMM yyyг. HH:mm:ss")),
    FOR_VIEW_SHORT(DateTimeFormatter.ofPattern("dd MMMM yyy"));

    private final DateTimeFormatter formatter;
}

