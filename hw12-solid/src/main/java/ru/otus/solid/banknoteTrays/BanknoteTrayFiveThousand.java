package ru.otus.solid.banknoteTrays;

import ru.otus.solid.Denomination;

public class BanknoteTrayFiveThousand extends BanknoteTray {
    public BanknoteTrayFiveThousand() {
        super(Denomination.FIVE_THOUSAND);
    }

    @Override
    public int getCapacity() {
        return super.getDefaultCapacity();
    }
}
