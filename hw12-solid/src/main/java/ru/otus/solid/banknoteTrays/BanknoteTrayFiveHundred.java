package ru.otus.solid.banknoteTrays;

import ru.otus.solid.Denomination;

public class BanknoteTrayFiveHundred extends BanknoteTray {
    private static final int CAPACITY = 11000;

    public BanknoteTrayFiveHundred() {
        super(Denomination.FIVE_HUNDRED, CAPACITY);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }
}
