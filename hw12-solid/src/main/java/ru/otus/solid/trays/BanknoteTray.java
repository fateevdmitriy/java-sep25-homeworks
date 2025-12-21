package ru.otus.solid.trays;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import ru.otus.solid.Denomination;
import ru.otus.solid.banknotes.Banknote;

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

    public boolean putBanknote(Banknote banknote) {
        if (this.getDenomination() != banknote.getDenomination()) {
            return false; // кидать исключение: добавить купюру в лоток невозможно, номинал банкноты не соответствует
            // лотку.
        }
        if (this.getAmount() >= this.getCapacity()) {
            return false; // кидать исключение: добавить купюру в лоток невозможно, лоток заполнен.
        }
        if (this.getAmount() == 0) {
            this.banknotes = new HashSet<>();
        }
        return banknotes.add(banknote); // добавить обработку исключений
    }

    public boolean putBanknotes(Set<Banknote> banknotes) {
        boolean resultFlag = true;
        for (Banknote banknote : banknotes) {
            resultFlag = resultFlag && putBanknote(banknote);
        }
        return resultFlag;
    }

    public Set<Banknote> getBanknotes(int amount) {
        Set<Banknote> resultBanknotes = new HashSet<>();

        if (amount <= 0 || amount > getAmount()) {
            return resultBanknotes; // кидать исключение: запрошенное число купюр должно быть положительным и не больше
            // кол-ва в лотке
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
