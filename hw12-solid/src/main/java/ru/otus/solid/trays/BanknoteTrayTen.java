package ru.otus.solid.trays;

import java.util.Set;
import ru.otus.solid.Denomination;
import ru.otus.solid.banknotes.Banknote;

public class BanknoteTrayTen extends BanknoteTray {
    private static final int CAPACITY = 20000;

    public BanknoteTrayTen() {
        super(Denomination.TEN);
    }

    public BanknoteTrayTen(Set<Banknote> banknotes) {
        super(Denomination.TEN, banknotes);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }
}
