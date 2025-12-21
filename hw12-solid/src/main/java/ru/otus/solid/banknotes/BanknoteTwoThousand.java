package ru.otus.solid.banknotes;

import ru.otus.solid.Denomination;

public class BanknoteTwoThousand extends Banknote {
    public BanknoteTwoThousand(String serialNumber) {
        super(Denomination.TWO_THOUSAND, serialNumber);
    }
}
