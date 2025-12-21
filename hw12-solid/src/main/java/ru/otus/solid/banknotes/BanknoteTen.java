package ru.otus.solid.banknotes;

import ru.otus.solid.Denomination;

public class BanknoteTen extends Banknote {
    public BanknoteTen(String serialNumber) {
        super(Denomination.TEN, serialNumber);
    }
}
