package ru.otus.solid.trays;

import java.util.Set;
import ru.otus.solid.Denomination;
import ru.otus.solid.banknotes.Banknote;

public class BanknoteTrayFifty extends BanknoteTray {
    private static final int CAPACITY = 20000;

    public BanknoteTrayFifty() {
        super(Denomination.FIFTY);
    }

    public BanknoteTrayFifty(Set<Banknote> banknotes) {
        super(Denomination.FIFTY, banknotes);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }
}
