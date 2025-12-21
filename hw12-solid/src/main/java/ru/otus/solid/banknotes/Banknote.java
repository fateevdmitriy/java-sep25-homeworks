package ru.otus.solid.banknotes;

import ru.otus.solid.Denomination;

public abstract class Banknote {
    private Denomination denomination;
    private String serialNumber;

    protected Banknote(Denomination denomination, String serialNumber) {
        this.denomination = denomination;
        this.serialNumber = serialNumber;
    }

    public Denomination getDenomination() {
        return denomination;
    }

    public void setDenominationEnum(Denomination denomination) {
        this.denomination = denomination;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

}
