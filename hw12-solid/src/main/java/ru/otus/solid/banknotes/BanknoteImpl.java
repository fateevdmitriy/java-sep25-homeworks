package ru.otus.solid.banknotes;

import ru.otus.solid.Denomination;

public class BanknoteImpl implements Banknote {
    private final Denomination denomination;

    public BanknoteImpl(Denomination denomination) {
        this.denomination = denomination;
    }

    public Denomination getDenomination() {
        return denomination;
    }
}
