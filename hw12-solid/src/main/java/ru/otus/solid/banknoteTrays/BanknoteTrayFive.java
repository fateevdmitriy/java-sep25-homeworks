package ru.otus.solid.banknoteTrays;

import ru.otus.solid.Denomination;

public class BanknoteTrayFive extends BanknoteTray {
    private static final int CAPACITY = 20000;

    public BanknoteTrayFive() {
        super(Denomination.FIVE, CAPACITY);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }
}
