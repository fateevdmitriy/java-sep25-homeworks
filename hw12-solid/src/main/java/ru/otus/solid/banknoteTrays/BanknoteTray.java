package ru.otus.solid.banknoteTrays;

import ru.otus.solid.Denomination;
import ru.otus.solid.banknotes.Banknote;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BanknoteTray {
    private static final int DEFAULT_CAPACITY = 10000;

    private final Denomination denomination;
    private final int capacity;
    private Set<Banknote> banknotes;

    public BanknoteTray(Denomination denomination) {
        this.denomination = denomination;
        this.capacity = DEFAULT_CAPACITY;
        this.banknotes = new HashSet<>();
    }

    public BanknoteTray(Denomination denomination, int capacity) {
        this.denomination = denomination;
        this.capacity = capacity;
        this.banknotes = new HashSet<>();
    }

    public BanknoteTray(Denomination denomination, int capacity, Set<Banknote> banknotes) {
        this.denomination = denomination;
        this.capacity = capacity;
        this.banknotes = banknotes;
    }


    public Denomination getDenomination() {
        return denomination;
    }

    public abstract int getCapacity();

    protected int getDefaultCapacity() {
        return DEFAULT_CAPACITY;
    }

    public int getAmount() {
        return (banknotes == null || banknotes.isEmpty()) ? 0 : banknotes.size();
    }

    public int getBalance() {
        return getAmount() * denomination.getValue();
    }

    public boolean putBanknote(Banknote banknote) {
        if (this.getDenomination() != banknote.getDenomination()) {
            return false; // кидать исключение
        }
        if (this.getAmount() >= this.getCapacity()) {
            return false; // кидать исключение
        }
        if (this.getAmount() == 0) {
            this.banknotes = new HashSet<>();
        }
        return banknotes.add(banknote); // добавить обработку исключений
    }


    public Set<Banknote> getBanknotes(int amount) {
        Set<Banknote> resultSet = new HashSet<>();

        if (amount <= 0 || amount > getAmount()) {
            return resultSet; // кидать своё исключение - запрошенное количество купюр должно быть положительным
        }

        if (amount == getAmount()) {
            resultSet.addAll(banknotes);
            banknotes.clear(); // заменить на метод очистки с проверками
        } else if (amount < getAmount()) {
            Set<Banknote> banknotesToMove = (banknotes.stream()
                                .limit(amount)
                                .collect(Collectors.toSet()));
            resultSet.addAll(banknotesToMove);
            banknotes.removeAll(banknotesToMove);
        }
        return resultSet;
    }

    public boolean contains(Banknote banknote) {
        return banknote != null && banknotes != null && banknotes.contains(banknote);
    }
}
