package ru.otus.solid.banknoteTrays;

import ru.otus.solid.Denomination;

public class BanknoteTrayTen extends BanknoteTray {
    private static final int CAPACITY = 15000;

    public BanknoteTrayTen() {
        super(Denomination.TEN, CAPACITY);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }
}
