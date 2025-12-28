package ru.otus.solid.trays;

import java.util.*;
import java.util.stream.Collectors;
import ru.otus.solid.Denomination;
import ru.otus.solid.banknotes.Banknote;
import ru.otus.solid.exceptions.GetBanknotesFromTrayException;
import ru.otus.solid.exceptions.PutBanknoteToTrayException;

public class BanknotesHolderImpl implements BanknotesHolder {
    private final EnumMap<Denomination, Set<Banknote>> trays;

    public BanknotesHolderImpl() {
        this.trays = new EnumMap<>(Denomination.class);
        for (Denomination denomination : Denomination.values()) {
            trays.computeIfAbsent(denomination, k -> new HashSet<>());
        }
    }

    @Override
    public int getCapacity(Denomination denomination) {
        return denomination.getCapacity();
    }

    @Override
    public int getAmount(Denomination denomination) {
        return getAvailableBanknotes(denomination).size();
    }

    @Override
    public boolean putBanknote(Banknote banknote) throws PutBanknoteToTrayException {
        Denomination denomination = banknote.getDenomination();
        if (Denomination.findByValue(denomination.getValue()) == null) {
            throw new PutBanknoteToTrayException("Недопустимый номинал банкноты: " + denomination.getValue());
        }
        if (getAmount(denomination) >= getCapacity(denomination)) {
            throw new PutBanknoteToTrayException(
                    "Невозможно добавить купюру в лоток. Заполнен лоток с номиналом: " + denomination.getValue());
        }
        return trays.get(denomination).add(banknote);
    }

    @Override
    public boolean putBanknotes(Set<Banknote> banknotes) throws PutBanknoteToTrayException {
        List<String> exceptionMessages = new ArrayList<>();
        String delimiter = System.lineSeparator();
        boolean resultFlag = true;
        for (Banknote banknote : banknotes) {
            try {
                resultFlag = resultFlag && putBanknote(banknote);
            } catch (PutBanknoteToTrayException e) {
                exceptionMessages.add(e.getMessage());
            }
        }
        if (!exceptionMessages.isEmpty()) {
            throw new PutBanknoteToTrayException(String.join(delimiter, exceptionMessages));
        }
        return resultFlag;
    }

    @Override
    public Set<Banknote> getBanknotes(Denomination denomination, int requestedAmount)
            throws GetBanknotesFromTrayException {
        Set<Banknote> resultBanknotes = new HashSet<>();
        int currentAmount = getAmount(denomination);
        if (requestedAmount <= 0) {
            throw new GetBanknotesFromTrayException(
                    "Запрошенное количество банкнот должно быть указано положительным числом.");
        }
        if (requestedAmount > currentAmount) {
            throw new GetBanknotesFromTrayException(
                    "Запрошенное количество банкнот превышает имеющееся количество банкнот номиналом: "
                            + denomination.getValue());
        }
        Set<Banknote> banknotes = getAvailableBanknotes(denomination);
        if (requestedAmount == currentAmount) {
            resultBanknotes.addAll(banknotes);
            clearBanknotes(denomination);
        } else {
            Set<Banknote> banknotesToMove =
                    banknotes.stream().limit(requestedAmount).collect(Collectors.toSet());
            resultBanknotes.addAll(banknotesToMove);
            banknotes.removeAll(banknotesToMove);
            trays.put(denomination, banknotes);
        }
        return resultBanknotes;
    }

    private Set<Banknote> getAvailableBanknotes(Denomination denomination) {
        if (!trays.containsKey(denomination)) {
            return new HashSet<>();
        }
        Set<Banknote> banknotes = trays.get(denomination);
        return (banknotes == null) ? new HashSet<>() : banknotes;
    }

    private void clearBanknotes(Denomination denomination) {
        trays.put(denomination, new HashSet<>());
    }
}
