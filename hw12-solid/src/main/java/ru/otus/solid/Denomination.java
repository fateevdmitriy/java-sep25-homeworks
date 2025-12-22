package ru.otus.solid;

import java.util.Arrays;

public enum Denomination {
    TEN(10),
    FIFTY(50),
    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000);

    private final int value;

    Denomination(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static Denomination findByValue(int value) {
        return Arrays.stream(values())
                .filter(denomination -> denomination.value == value)
                .findFirst()
                .orElse(null);
    }
}
