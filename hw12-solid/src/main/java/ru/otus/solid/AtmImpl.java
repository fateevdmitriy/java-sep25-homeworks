package ru.otus.solid;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.solid.banknotes.Banknote;
import ru.otus.solid.banknotes.BanknoteImpl;
import ru.otus.solid.exceptions.GetBanknotesFromTrayException;
import ru.otus.solid.exceptions.PutBanknoteToTrayException;
import ru.otus.solid.trays.*;

public class AtmImpl implements Atm {
    private static final int PRELOAD_BANKNOTES_AMOUNT = 100;
    private static final Logger log = LoggerFactory.getLogger(AtmImpl.class);

    private final BanknotesHolder banknotesHolder;

    public AtmImpl() {
        banknotesHolder = new BanknotesHolderImpl();
        try {
            banknotesHolder.putBanknotes(prepareBanknotes());
        } catch (PutBanknoteToTrayException e) {
            log.error("Ошибки при первичной загрузке банкнот в банкомат: " + e.getMessage());
        }
    }

    private Set<Banknote> prepareBanknotes() {
        Set<Banknote> resultBanknotes = new HashSet<>();
        for (Denomination denomination : Denomination.values()) {
            for (int i = 0; i < PRELOAD_BANKNOTES_AMOUNT; i++) {
                resultBanknotes.add(new BanknoteImpl(denomination));
            }
        }
        return resultBanknotes;
    }

    @Override
    public int getAmount(Denomination denomination) {
        return banknotesHolder.getAmount(denomination);
    }

    @Override
    public int getBalance(Denomination denomination) {
        return banknotesHolder.getAmount(denomination) * denomination.getValue();
    }

    @Override
    public int getGeneralBalance() {
        int allTraysBalance = 0;
        for (Denomination denomination : Denomination.values()) {
            allTraysBalance += getBalance(denomination);
        }
        return allTraysBalance;
    }

    @Override
    public boolean putMoney(Banknote banknote) throws PutBanknoteToTrayException {
        return banknotesHolder.putBanknote(banknote);
    }

    @Override
    public boolean putMoney(Set<Banknote> banknotes) throws PutBanknoteToTrayException {
        return banknotesHolder.putBanknotes(banknotes);
    }

    @Override
    public Set<Banknote> getMoney(int requestedSum) throws GetBanknotesFromTrayException {
        Set<Banknote> resultBanknotes = new HashSet<>();
        int remainSum = requestedSum;
        List<Denomination> reversedDenominations =
                Arrays.asList(Denomination.values()).reversed();

        for (Denomination denomination : reversedDenominations) {
            int denominationValue = denomination.getValue();
            int divisionInt = remainSum / denominationValue;
            int divisionReminder = remainSum % denominationValue;
            if (divisionInt >= 1) {
                Set<Banknote> getBanknotes = banknotesHolder.getBanknotes(denomination, divisionInt);
                resultBanknotes.addAll(getBanknotes);
                remainSum = remainSum - divisionInt * denominationValue;
            }
            if (divisionReminder == 0) {
                break;
            }
        }

        if (remainSum != 0) {
            throw new GetBanknotesFromTrayException("Запрошенная сумма не может быть выдана: " + requestedSum);
        }
        return resultBanknotes;
    }
}
