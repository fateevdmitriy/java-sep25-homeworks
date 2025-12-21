package ru.otus.solid.banknoteTrays;

import ru.otus.solid.Denomination;

public class BanknoteTrayTwoHundred extends BanknoteTray {
    private static final int CAPACITY = 12000;

    public BanknoteTrayTwoHundred() {
        super(Denomination.TWO_HUNDRED, CAPACITY);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }
}
