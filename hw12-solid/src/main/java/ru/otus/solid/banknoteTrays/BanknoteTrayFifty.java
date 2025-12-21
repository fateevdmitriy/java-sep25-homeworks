package ru.otus.solid.banknoteTrays;

import ru.otus.solid.Denomination;

public class BanknoteTrayFifty extends BanknoteTray {
    private static final int CAPACITY = 14000;

    public BanknoteTrayFifty() {
        super(Denomination.FIFTY, CAPACITY);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }
}
