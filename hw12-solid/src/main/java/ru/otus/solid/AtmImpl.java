package ru.otus.solid;

import java.util.*;
import ru.otus.solid.banknotes.Banknote;
import ru.otus.solid.exceptions.GetBanknotesFromTrayException;
import ru.otus.solid.exceptions.PutBanknoteToTrayException;
import ru.otus.solid.trays.*;

public class AtmImpl implements Atm {
    private static final int PRELOAD_BANKNOTES_AMOUNT = 100;

    private final BanknotesHolder banknotesHolder;

    public AtmImpl() throws PutBanknoteToTrayException {
        banknotesHolder = new BanknotesHolderImpl();
        putMoney(prepareBanknotes());
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

    /*
    @Override
    public BanknoteTrayImpl getTray(Denomination denomination) {
        return trays.get(denomination);
    }

    @Override
    public int getTrayBalance(Denomination denomination) {
        return getTray(denomination).getBalance();
    }

    @Override
    public int getAllTraysBalance() {
        int allTraysBalance = 0;
        for (Denomination denomination : trays.keySet()) {
            allTraysBalance += getTrayBalance(denomination);
        }
        return allTraysBalance;
    }

    @Override
    public Map<Denomination, Integer> getEachTrayBalance() {
        Map<Denomination, Integer> denominationToBalance = new EnumMap<>(Denomination.class);
        for (Denomination denomination : trays.keySet()) {
            denominationToBalance.put(denomination, getTray(denomination).getBalance());
        }
        return denominationToBalance;
    }

    @Override
    public Map<Denomination, Integer> getEachTrayAmount() {
        Map<Denomination, Integer> denominationToAmount = new EnumMap<>(Denomination.class);
        for (BanknoteTrayImpl banknoteTray : trays.values()) {
            denominationToAmount.put(banknoteTray.getDenomination(), banknoteTray.getAmount());
        }
        return denominationToAmount;
    }
*/

    @Override
    public boolean putMoney(Banknote banknote) throws PutBanknoteToTrayException {
        /*
        Set<Banknote> banknotes = Collections.singleton(banknote);
        putMoney(banknotes);
        */
        return banknotesHolder.putBanknote(banknote);
    }

    @Override
    public boolean putMoney(Set<Banknote> banknotes) throws PutBanknoteToTrayException {
        /*
        BanknoteTrayImpl targetTray;
        for (Banknote banknote : banknotes) {
            Denomination banknoteDenomination = banknote.getDenomination();
            if (trays.containsKey(banknoteDenomination)) {
                targetTray = trays.get(banknoteDenomination);
                targetTray.putBanknote(banknote);
            } else {
                throw new PutBanknoteToTrayException("Банкнота номинала %s не может быть принята, недопустимый номинал."
                        + banknote.getDenomination().getValue());
            }
        }
        */
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


    /*
    private Map<Denomination, BanknoteTrayImpl> prepareTrays() {
        Map<Denomination, BanknoteTrayImpl> preparedTrays = new EnumMap<>(Denomination.class);

        Set<Banknote> preloadBanknotes10 = preloadBanknotes(Denomination.TEN);
        BanknoteTrayImpl banknoteTrayTen = new BanknoteTrayImpl(Denomination.TEN, 20000, preloadBanknotes10);
        preparedTrays.put(Denomination.TEN, banknoteTrayTen);

        Set<Banknote> preloadBanknotes50 = preloadBanknotes(Denomination.FIFTY);
        BanknoteTrayImpl banknoteTrayFifty = new BanknoteTrayImpl(Denomination.FIFTY, 25000, preloadBanknotes50);
        preparedTrays.put(Denomination.FIFTY, banknoteTrayFifty);

        Set<Banknote> preloadBanknotes100 = preloadBanknotes(Denomination.ONE_HUNDRED);
        BanknoteTrayImpl banknoteTrayOneHundred = new BanknoteTrayOne(Denomination.ONE_HUNDRED, 30000, preloadBanknotes100);
        preparedTrays.put(Denomination.ONE_HUNDRED, banknoteTrayOneHundred);

        Set<Banknote> preloadBanknotes200 = preloadBanknotes(Denomination.TWO_HUNDRED);
        BanknoteTrayImpl banknoteTrayTwoHundred = new BanknoteTrayImpl(Denomination.TWO_HUNDRED, 25000, preloadBanknotes200);
        preparedTrays.put(Denomination.TWO_HUNDRED, banknoteTrayTwoHundred);

        Set<Banknote> preloadBanknotes500 = preloadBanknotes(Denomination.FIVE_HUNDRED);
        BanknoteTrayImpl banknoteTrayFiveHundred = new BanknoteTrayImpl(Denomination.FIVE_HUNDRED, 30000, preloadBanknotes500);
        preparedTrays.put(Denomination.FIVE_HUNDRED, banknoteTrayFiveHundred);

        Set<Banknote> preloadBanknotes1000 = preloadBanknotes(Denomination.ONE_THOUSAND);
        BanknoteTrayImpl banknoteTrayOneThousand = new BanknoteTrayImpl(Denomination.ONE_THOUSAND, 20000, preloadBanknotes1000);
        preparedTrays.put(Denomination.ONE_THOUSAND, banknoteTrayOneThousand);

        Set<Banknote> preloadBanknotes2000 = preloadBanknotes(Denomination.TWO_THOUSAND);
        BanknoteTrayImpl banknoteTrayTwoThousand = new BanknoteTrayImpl(Denomination.TWO_THOUSAND, 15000, preloadBanknotes2000);
        preparedTrays.put(Denomination.TWO_THOUSAND, banknoteTrayTwoThousand);

        Set<Banknote> preloadBanknotes5000 = preloadBanknotes(Denomination.FIVE_THOUSAND);
        BanknoteTrayImpl banknoteTrayFiveThousand = new BanknoteTrayImpl(Denomination.FIVE_THOUSAND, 10000, preloadBanknotes5000);
        preparedTrays.put(Denomination.FIVE_THOUSAND, banknoteTrayFiveThousand);

        return preparedTrays;
    }
     */

}
