package ru.otus.solid;

import java.util.*;
import ru.otus.solid.banknotes.Banknote;
import ru.otus.solid.banknotes.BanknoteImpl;
import ru.otus.solid.exceptions.GetBanknotesFromTrayException;
import ru.otus.solid.exceptions.PutBanknoteToTrayException;
import ru.otus.solid.trays.*;

public class Atm {
    private static final int PRELOAD_BANKNOTES_AMOUNT = 100;
    private final Map<Denomination, BanknoteTray> trays;

    public Atm() {
        this.trays = prepareTrays();
    }

    public BanknoteTray getTray(Denomination denomination) {
        return trays.get(denomination);
    }

    public int getTrayBalance(Denomination denomination) {
        return getTray(denomination).getBalance();
    }

    public int getAllTraysBalance() {
        int allTraysBalance = 0;
        for (Denomination denomination : trays.keySet()) {
            allTraysBalance += getTrayBalance(denomination);
        }
        return allTraysBalance;
    }

    public Map<Denomination, Integer> getEachTrayBalance() {
        Map<Denomination, Integer> denominationToBalance = new EnumMap<>(Denomination.class);
        for (Denomination denomination : trays.keySet()) {
            denominationToBalance.put(denomination, getTray(denomination).getBalance());
        }
        return denominationToBalance;
    }

    public Map<Denomination, Integer> getEachTrayAmount() {
        Map<Denomination, Integer> denominationToAmount = new EnumMap<>(Denomination.class);
        for (BanknoteTray banknoteTray : trays.values()) {
            denominationToAmount.put(banknoteTray.getDenomination(), banknoteTray.getAmount());
        }
        return denominationToAmount;
    }

    public void putMoney(Banknote banknote) throws PutBanknoteToTrayException {
        Set<Banknote> money = Collections.singleton(banknote);
        putMoney(money);
    }

    public void putMoney(Set<Banknote> money) throws PutBanknoteToTrayException {
        BanknoteTray targetTray;
        for (Banknote banknote : money) {
            Denomination banknoteDenomination = banknote.getDenomination();
            if (trays.containsKey(banknoteDenomination)) {
                targetTray = trays.get(banknoteDenomination);
                targetTray.putBanknote(banknote);
            } else {
                throw new PutBanknoteToTrayException("Банкнота номинала %s не может быть принята, недопустимый номинал."
                        + banknote.getDenomination().getValue());
            }
        }
    }

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
                BanknoteTray targetTray = trays.get(denomination);
                Set<Banknote> getBanknotes = null;
                getBanknotes = targetTray.getBanknotes(divisionInt);
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

    private Set<Banknote> preloadBanknotes(Denomination denomination, int preloadAmount) {
        Set<Banknote> resultBanknotes = new HashSet<>();
        for (int i = 0; i < preloadAmount; i++) {
            resultBanknotes.add(new BanknoteImpl(denomination));
        }
        return resultBanknotes;
    }

    private Map<Denomination, BanknoteTray> prepareTrays() {
        Map<Denomination, BanknoteTray> preparedTrays = new EnumMap<>(Denomination.class);

        Set<Banknote> preloadBanknotesTen = preloadBanknotes(Denomination.TEN, PRELOAD_BANKNOTES_AMOUNT);
        BanknoteTray banknoteTrayTen = new BanknoteTrayTen(preloadBanknotesTen);
        preparedTrays.put(Denomination.TEN, banknoteTrayTen);

        Set<Banknote> preloadBanknotesFifty = preloadBanknotes(Denomination.FIFTY, PRELOAD_BANKNOTES_AMOUNT);
        BanknoteTray banknoteTrayFifty = new BanknoteTrayFifty(preloadBanknotesFifty);
        preparedTrays.put(Denomination.FIFTY, banknoteTrayFifty);

        Set<Banknote> preloadBanknotesOneHundred = preloadBanknotes(Denomination.ONE_HUNDRED, PRELOAD_BANKNOTES_AMOUNT);
        BanknoteTray banknoteTrayOneHundred = new BanknoteTrayOneHundred(preloadBanknotesOneHundred);
        preparedTrays.put(Denomination.ONE_HUNDRED, banknoteTrayOneHundred);

        Set<Banknote> preloadBanknotesTwoHundred = preloadBanknotes(Denomination.TWO_HUNDRED, PRELOAD_BANKNOTES_AMOUNT);
        BanknoteTray banknoteTrayTwoHundred = new BanknoteTrayTwoHundred(preloadBanknotesTwoHundred);
        preparedTrays.put(Denomination.TWO_HUNDRED, banknoteTrayTwoHundred);

        Set<Banknote> preloadBanknotesFiveHundred =
                preloadBanknotes(Denomination.FIVE_HUNDRED, PRELOAD_BANKNOTES_AMOUNT);
        BanknoteTray banknoteTrayFiveHundred = new BanknoteTrayFiveHundred(preloadBanknotesFiveHundred);
        preparedTrays.put(Denomination.FIVE_HUNDRED, banknoteTrayFiveHundred);

        Set<Banknote> preloadBanknotesOneThousand =
                preloadBanknotes(Denomination.ONE_THOUSAND, PRELOAD_BANKNOTES_AMOUNT);
        BanknoteTray BanknoteTrayOneThousand = new BanknoteTrayOneThousand(preloadBanknotesOneThousand);
        preparedTrays.put(Denomination.ONE_THOUSAND, BanknoteTrayOneThousand);

        Set<Banknote> preloadBanknotesTwoThousand =
                preloadBanknotes(Denomination.TWO_THOUSAND, PRELOAD_BANKNOTES_AMOUNT);
        BanknoteTray BanknoteTrayTwoThousand = new BanknoteTrayTwoThousand(preloadBanknotesTwoThousand);
        preparedTrays.put(Denomination.TWO_THOUSAND, BanknoteTrayTwoThousand);

        Set<Banknote> preloadBanknotesFiveThousand =
                preloadBanknotes(Denomination.FIVE_THOUSAND, PRELOAD_BANKNOTES_AMOUNT);
        BanknoteTray BanknoteTrayFiveThousand = new BanknoteTrayFiveThousand(preloadBanknotesFiveThousand);
        preparedTrays.put(Denomination.FIVE_THOUSAND, BanknoteTrayFiveThousand);

        return preparedTrays;
    }
}
