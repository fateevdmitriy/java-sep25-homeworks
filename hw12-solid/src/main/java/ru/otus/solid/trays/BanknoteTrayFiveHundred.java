package ru.otus.solid.trays;

import java.util.Set;
import ru.otus.solid.Denomination;
import ru.otus.solid.banknotes.Banknote;

public class BanknoteTrayFiveHundred extends BanknoteTray {
    private static final int CAPACITY = 30000;

    public BanknoteTrayFiveHundred() {
        super(Denomination.FIVE_HUNDRED);
    }

    public BanknoteTrayFiveHundred(Set<Banknote> banknotes) {
        super(Denomination.FIVE_HUNDRED, banknotes);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }
}
