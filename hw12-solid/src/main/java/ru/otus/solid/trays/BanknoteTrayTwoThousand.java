package ru.otus.solid.trays;

import java.util.Set;
import ru.otus.solid.Denomination;
import ru.otus.solid.banknotes.Banknote;

public class BanknoteTrayTwoThousand extends BanknoteTray {
    private static final int CAPACITY = 15000;

    public BanknoteTrayTwoThousand() {
        super(Denomination.TWO_THOUSAND);
    }

    public BanknoteTrayTwoThousand(Set<Banknote> banknotes) {
        super(Denomination.TWO_THOUSAND, banknotes);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }
}
