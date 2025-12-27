package ru.otus.solid;

import java.util.Arrays;

public enum Denomination  {
    TEN(10, 50000),
    FIFTY(50, 40000),
    ONE_HUNDRED(100, 30000),
    TWO_HUNDRED(200, 30000),
    FIVE_HUNDRED(500, 30000),
    ONE_THOUSAND(1000, 50000),
    TWO_THOUSAND(2000, 40000),
    FIVE_THOUSAND(5000, 25000);

    private final int value;
    private final int capacity;

    Denomination(int value, int capacity) {
        this.value = value;
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getValue() {
        return value;
    }

    public Denomination findByValue(int value) {
        return Arrays.stream(values())
                .filter(denomination -> denomination.value == value)
                .findFirst()
                .orElse(null);
    }
}
