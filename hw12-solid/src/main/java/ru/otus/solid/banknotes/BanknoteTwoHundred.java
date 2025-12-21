package ru.otus.solid.banknotes;

import ru.otus.solid.Denomination;

public class BanknoteTwoHundred extends Banknote {
    public BanknoteTwoHundred(String serialNumber) {
        super(Denomination.TWO_HUNDRED, serialNumber);
    }
}
