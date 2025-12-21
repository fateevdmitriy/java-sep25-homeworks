package ru.otus.solid.banknotes;

import ru.otus.solid.Denomination;

public  class Banknote {
    private final Denomination denomination;

    protected Banknote(Denomination denomination) {
        this.denomination = denomination;
    }

    public Denomination getDenomination() {
        return denomination;
    }
}
