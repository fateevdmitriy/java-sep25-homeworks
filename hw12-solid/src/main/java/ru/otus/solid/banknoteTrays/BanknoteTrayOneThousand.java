package ru.otus.solid.banknoteTrays;

import ru.otus.solid.Denomination;

public class BanknoteTrayOneThousand extends BanknoteTray {
    public BanknoteTrayOneThousand() {
        super(Denomination.ONE_THOUSAND);
    }

    @Override
    public int getCapacity() {
        return super.getDefaultCapacity();
    }
}
