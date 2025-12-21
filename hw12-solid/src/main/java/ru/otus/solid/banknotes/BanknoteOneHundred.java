package ru.otus.solid.banknotes;

import ru.otus.solid.Denomination;

public class BanknoteOneHundred extends Banknote {
    public BanknoteOneHundred(String serialNumber) {
        super(Denomination.ONE_HUNDRED, serialNumber);
    }
}
