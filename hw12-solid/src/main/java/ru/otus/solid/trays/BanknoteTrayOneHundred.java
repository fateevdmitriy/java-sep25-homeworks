package ru.otus.solid.trays;

import java.util.Set;
import ru.otus.solid.Denomination;
import ru.otus.solid.banknotes.Banknote;

public class BanknoteTrayOneHundred extends BanknoteTray {
    private static final int CAPACITY = 30000;

    public BanknoteTrayOneHundred() {
        super(Denomination.ONE_HUNDRED);
    }

    public BanknoteTrayOneHundred(Set<Banknote> banknotes) {
        super(Denomination.ONE_HUNDRED, banknotes);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }
}
