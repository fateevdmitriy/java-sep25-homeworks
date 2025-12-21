package ru.otus.solid.trays;

import java.util.Set;
import ru.otus.solid.Denomination;
import ru.otus.solid.banknotes.Banknote;

public class BanknoteTrayTwoHundred extends BanknoteTray {
    private static final int CAPACITY = 25000;

    public BanknoteTrayTwoHundred() {
        super(Denomination.TWO_HUNDRED);
    }

    public BanknoteTrayTwoHundred(Set<Banknote> banknotes) {
        super(Denomination.TWO_HUNDRED, banknotes);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }
}
