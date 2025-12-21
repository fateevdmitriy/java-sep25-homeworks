package ru.otus.solid.banknotes;

import ru.otus.solid.Denomination;

public class BanknoteFifty extends Banknote {
    public BanknoteFifty(String serialNumber) {
        super(Denomination.FIFTY, serialNumber);
    }
}
