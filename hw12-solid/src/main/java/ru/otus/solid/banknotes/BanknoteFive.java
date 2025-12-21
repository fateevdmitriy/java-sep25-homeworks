package ru.otus.solid.banknotes;

import ru.otus.solid.Denomination;

public class BanknoteFive extends Banknote {
    public BanknoteFive(String serialNumber) {
        super(Denomination.FIVE, serialNumber);
    }
}
