package ru.otus.solid.banknotes;

import ru.otus.solid.Denomination;

public class BanknoteFiveHundred extends Banknote {
    public BanknoteFiveHundred(String serialNumber) {
        super(Denomination.FIVE_HUNDRED, serialNumber);
    }
}
