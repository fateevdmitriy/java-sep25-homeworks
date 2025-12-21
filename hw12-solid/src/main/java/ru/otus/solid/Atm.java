package ru.otus.solid;

import java.util.*;
import ru.otus.solid.banknotes.Banknote;
import ru.otus.solid.trays.*;

public class Atm {
    private final Map<Denomination, BanknoteTray> trays;

    public Atm() {
        this.trays = prepareTrays();
    }

    private Map<Denomination, BanknoteTray> prepareTrays() {
        Map<Denomination, BanknoteTray> preparedTrays = new EnumMap<>(Denomination.class);
        preparedTrays.put(Denomination.TEN, new BanknoteTrayTen());
        preparedTrays.put(Denomination.FIFTY, new BanknoteTrayFifty());
        preparedTrays.put(Denomination.ONE_HUNDRED, new BanknoteTrayOneHundred());
        preparedTrays.put(Denomination.TWO_HUNDRED, new BanknoteTrayTwoHundred());
        preparedTrays.put(Denomination.FIVE_HUNDRED, new BanknoteTrayFiveHundred());
        preparedTrays.put(Denomination.ONE_THOUSAND, new BanknoteTrayOneThousand());
        preparedTrays.put(Denomination.TWO_THOUSAND, new BanknoteTrayTwoThousand());
        preparedTrays.put(Denomination.FIVE_THOUSAND, new BanknoteTrayFiveThousand());
        return preparedTrays;
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

    //
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
                remainSum = requestedSum - divisionInt * denominationValue;
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
}
