package ru.otus.solid.banknotes;

import ru.otus.solid.Denomination;

public class BanknoteFiveThousand extends Banknote {
    public BanknoteFiveThousand(String serialNumber) {
        super(Denomination.FIVE_THOUSAND, serialNumber);
    }
}
