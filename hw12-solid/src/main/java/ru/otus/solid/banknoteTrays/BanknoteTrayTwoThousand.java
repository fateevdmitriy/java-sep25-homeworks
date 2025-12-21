package ru.otus.solid.banknoteTrays;

import ru.otus.solid.Denomination;

public class BanknoteTrayTwoThousand extends BanknoteTray {
    public BanknoteTrayTwoThousand() {
        super(Denomination.TWO_THOUSAND);
    }

    @Override
    public int getCapacity() {
        return super.getDefaultCapacity();
    }
}
