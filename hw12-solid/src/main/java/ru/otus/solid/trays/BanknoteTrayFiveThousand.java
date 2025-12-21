package ru.otus.solid.trays;

import java.util.Set;
import ru.otus.solid.Denomination;
import ru.otus.solid.banknotes.Banknote;

public class BanknoteTrayFiveThousand extends BanknoteTray {
    private static final int CAPACITY = 10000;

    public BanknoteTrayFiveThousand() {
        super(Denomination.FIVE_THOUSAND);
    }

    public BanknoteTrayFiveThousand(Set<Banknote> banknotes) {
        super(Denomination.FIVE_THOUSAND, banknotes);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }
}
