package ru.otus.solid.trays;

import java.util.Set;
import ru.otus.solid.Denomination;
import ru.otus.solid.banknotes.Banknote;

public class BanknoteTrayOneThousand extends BanknoteTray {
    private static final int CAPACITY = 20000;

    public BanknoteTrayOneThousand() {
        super(Denomination.ONE_THOUSAND);
    }

    public BanknoteTrayOneThousand(Set<Banknote> banknotes) {
        super(Denomination.ONE_THOUSAND, banknotes);
    }

    @Override
    public int getCapacity() {
        return CAPACITY;
    }
}
