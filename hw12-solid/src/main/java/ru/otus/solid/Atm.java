package ru.otus.solid;

import java.util.*;
import ru.otus.solid.banknotes.Banknote;
import ru.otus.solid.banknotes.BanknoteImpl;
import ru.otus.solid.trays.*;

public class Atm {
    private final Map<Denomination, BanknoteTray> trays;

    public Atm() {
        this.trays = prepareTrays();
    }

    private Set<Banknote> preloadBanknotes(Denomination denomination, int preloadAmount) {
        Set<Banknote> resultBanknotes = new HashSet<>();
        for (int i = 0; i < preloadAmount; i++) {
            resultBanknotes.add(new BanknoteImpl(denomination));
        }
        return resultBanknotes;
    }

    public int getBalance() {
        int allTraysBalance = 0;
        for (Denomination denomination : trays.keySet()) {
            int trayBalance = trays.get(denomination).getBalance();
            if (trayBalance >= 0) {
                allTraysBalance = allTraysBalance + trayBalance;
            }
        }
        return allTraysBalance;
    }

    public Map<Denomination, Integer> getEachTrayBalance() {
        Map<Denomination, Integer> denominationToBalance = new EnumMap<>(Denomination.class);
        for (BanknoteTray banknoteTray : trays.values()) {
            denominationToBalance.put(banknoteTray.getDenomination(), banknoteTray.getBalance());
        }
        return denominationToBalance;
    }

    public void putMoney(Banknote banknote) {
        Set<Banknote> money = Collections.singleton(banknote);
        putMoney(money);
    }

    public void putMoney(Set<Banknote> money) {
        BanknoteTray targetTray;
        List<String> errorMessages = new ArrayList<>();
        for (Banknote banknote : money) {
            Denomination banknoteDenomination = banknote.getDenomination();
            if (trays.containsKey(banknoteDenomination)) {
                targetTray = trays.get(banknoteDenomination);
                boolean putBanknoteResult = targetTray.putBanknote(banknote);
                if (!putBanknoteResult) {
                    errorMessages.add(String.format(
                            "Банкнота номинала %s не может быть принята, ошибка добавления банкноты в лоток.",
                            banknote.getDenomination().getValue()));
                }
            } else {
                errorMessages.add(String.format(
                        "Банкнота номинала %s не может быть принята, недопустимый номинал.",
                        banknote.getDenomination().getValue()));
            }
        }
        if (!errorMessages.isEmpty()) {
            // TODO: бросать исключение: Возникли ошибки при получении бакнот. ${errorMessages}
        }
    }

    public Set<Banknote> getMoney(int requestedSum) {
        Set<Banknote> resultBanknotes = new HashSet<>();
        int remainSum = requestedSum;
        List<Denomination> reversedDenominations =
                Arrays.asList(Denomination.values()).reversed();

        for (Denomination denomination : reversedDenominations) {
            int denominationValue = denomination.getValue(); // номинал банкноты
            int divisionInt = remainSum / denominationValue;
            int divisionReminder = remainSum % denominationValue;

            if (divisionInt >= 1) {
                BanknoteTray targetTray = trays.get(denomination);
                Set<Banknote> getBanknotes = targetTray.getBanknotes(divisionInt);
                resultBanknotes.addAll(getBanknotes);
                remainSum = remainSum - divisionInt * denominationValue;
            }

            if (divisionReminder == 0) {
                break;
            }
        }

        if (remainSum != 0) {
            // TODO бросать исключение: Запрошенная сумма не может быть выдана.
        }
        return resultBanknotes;
    }

    private Map<Denomination, BanknoteTray> prepareTrays() {
        Map<Denomination, BanknoteTray> preparedTrays = new EnumMap<>(Denomination.class);

        Set<Banknote> preloadBanknotesTen = preloadBanknotes(Denomination.TEN, 100);
        BanknoteTray banknoteTrayTen = new BanknoteTrayTen(preloadBanknotesTen);
        preparedTrays.put(Denomination.TEN, banknoteTrayTen);

        Set<Banknote> preloadBanknotesFifty = preloadBanknotes(Denomination.FIFTY, 100);
        BanknoteTray banknoteTrayFifty = new BanknoteTrayFifty(preloadBanknotesFifty);
        preparedTrays.put(Denomination.FIFTY, banknoteTrayFifty);

        Set<Banknote> preloadBanknotesOneHundred = preloadBanknotes(Denomination.ONE_HUNDRED, 100);
        BanknoteTray banknoteTrayOneHundred = new BanknoteTrayOneHundred(preloadBanknotesOneHundred);
        preparedTrays.put(Denomination.ONE_HUNDRED, banknoteTrayOneHundred);

        Set<Banknote> preloadBanknotesTwoHundred = preloadBanknotes(Denomination.TWO_HUNDRED, 100);
        BanknoteTray banknoteTrayTwoHundred = new BanknoteTrayTwoHundred(preloadBanknotesTwoHundred);
        preparedTrays.put(Denomination.TWO_HUNDRED, banknoteTrayTwoHundred);

        Set<Banknote> preloadBanknotesFiveHundred = preloadBanknotes(Denomination.FIVE_HUNDRED, 100);
        BanknoteTray banknoteTrayFiveHundred = new BanknoteTrayFiveHundred(preloadBanknotesFiveHundred);
        preparedTrays.put(Denomination.FIVE_HUNDRED, banknoteTrayFiveHundred);

        Set<Banknote> preloadBanknotesOneThousand = preloadBanknotes(Denomination.ONE_THOUSAND, 100);
        BanknoteTray BanknoteTrayOneThousand = new BanknoteTrayOneThousand(preloadBanknotesOneThousand);
        preparedTrays.put(Denomination.ONE_THOUSAND, BanknoteTrayOneThousand);

        Set<Banknote> preloadBanknotesTwoThousand = preloadBanknotes(Denomination.TWO_THOUSAND, 100);
        BanknoteTray BanknoteTrayTwoThousand = new BanknoteTrayTwoThousand(preloadBanknotesTwoThousand);
        preparedTrays.put(Denomination.TWO_THOUSAND, BanknoteTrayTwoThousand);

        Set<Banknote> preloadBanknotesFiveThousand = preloadBanknotes(Denomination.FIVE_THOUSAND, 100);
        BanknoteTray BanknoteTrayFiveThousand = new BanknoteTrayFiveThousand(preloadBanknotesFiveThousand);
        preparedTrays.put(Denomination.FIVE_THOUSAND, BanknoteTrayFiveThousand);

        return preparedTrays;
    }
}
