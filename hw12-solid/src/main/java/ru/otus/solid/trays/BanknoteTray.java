package ru.otus.solid.trays;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import ru.otus.solid.Denomination;
import ru.otus.solid.banknotes.Banknote;
import ru.otus.solid.exceptions.GetBanknotesFromTrayException;
import ru.otus.solid.exceptions.PutBanknoteToTrayException;

public abstract class BanknoteTray {

    private final Denomination denomination;
    private Set<Banknote> banknotes;

    protected BanknoteTray(Denomination denomination) {
        this.denomination = denomination;
        this.banknotes = new HashSet<>();
    }

    protected BanknoteTray(Denomination denomination, Set<Banknote> banknotes) {
        this.denomination = denomination;
        this.banknotes = banknotes;
    }

    public abstract int getCapacity();

    public Denomination getDenomination() {
        return denomination;
    }

    public int getAmount() {
        return (banknotes == null || banknotes.isEmpty()) ? 0 : banknotes.size();
    }

    public int getBalance() {
        return getAmount() * denomination.getValue();
    }

    public boolean putBanknote(Banknote banknote) throws PutBanknoteToTrayException {
        if (this.getDenomination() != banknote.getDenomination()) {
            throw new PutBanknoteToTrayException("Недопустимый номинал банкноты: "
                    + banknote.getDenomination().getValue());
        }
        if (this.getAmount() >= this.getCapacity()) {
            throw new PutBanknoteToTrayException("Невозможно добавить купюру в лоток. Заполнен лоток с номиналом: "
                    + this.getDenomination().getValue());
        }
        if (this.getAmount() == 0) {
            this.banknotes = new HashSet<>();
        }
        return banknotes.add(banknote);
    }

    public boolean putBanknotes(Set<Banknote> banknotes) throws PutBanknoteToTrayException {
        boolean resultFlag = true;
        for (Banknote banknote : banknotes) {
            resultFlag = resultFlag && putBanknote(banknote);
        }
        return resultFlag;
    }

    public Set<Banknote> getBanknotes(int amount) throws GetBanknotesFromTrayException {
        Set<Banknote> resultBanknotes = new HashSet<>();
        if (amount <= 0) {
            throw new GetBanknotesFromTrayException(
                    "Запрошенное количество банкнот должно быть указано положительным числом.");
        }
        if (amount > getAmount()) {
            throw new GetBanknotesFromTrayException(
                    "Запрошенное количество банкнот превышает имеющееся количество банкнот номиналом: "
                            + this.getDenomination().getValue());
        }
        if (amount == getAmount()) {
            resultBanknotes.addAll(this.banknotes);
            this.banknotes.clear(); // заменить на метод очистки с проверками
        } else if (amount < getAmount()) {
            Set<Banknote> banknotesToMove = (banknotes.stream().limit(amount).collect(Collectors.toSet()));
            resultBanknotes.addAll(banknotesToMove);
            this.banknotes.removeAll(banknotesToMove);
        }
        return resultBanknotes;
    }
}
