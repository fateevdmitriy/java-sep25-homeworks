package ru.otus.solid.banknoteTrays;

import ru.otus.solid.Denomination;

public class BanknoteTrayOneHundred extends BanknoteTray {
    private static final int CAPACITY = 12000;

    public BanknoteTrayOneHundred() {
        super(Denomination.ONE_HUNDRED, CAPACITY);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }
}
