package ru.otus.solid.banknotes;

import ru.otus.solid.Denomination;

public class BanknoteOneThousand extends Banknote {
    public BanknoteOneThousand(String serialNumber) {
        super(Denomination.ONE_THOUSAND, serialNumber);
    }
}
