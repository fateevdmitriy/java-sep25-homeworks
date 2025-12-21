package ru.otus.solid;

import ru.otus.solid.banknoteTrays.*;
import ru.otus.solid.banknotes.Banknote;

import java.util.*;

public class Atm {
    private final String atmId;
    private final Map<Denomination, BanknoteTray> trays;

    public Atm(String atmId) {
        this.atmId = atmId;
        this.trays = prepareTrays();
    }

    private Map<Denomination, BanknoteTray> prepareTrays() {
        Map<Denomination, BanknoteTray> preparedTrays = new EnumMap<>(Denomination.class);
        preparedTrays.put(Denomination.FIVE, new BanknoteTrayFive());
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

    public String getAtmId() {
        return atmId;
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

    public void putMoney(Set<Banknote> money) {
        BanknoteTray targetTray;
        List<String> errorMessages = new ArrayList<>();
        for (Banknote banknote : money) {
            Denomination banknoteDenomination = banknote.getDenomination();
            if (trays.containsKey(banknoteDenomination)) {
                targetTray = trays.get(banknoteDenomination);
                boolean putBanknoteResult = targetTray.putBanknote(banknote);
                if (!putBanknoteResult) {
                    errorMessages.add(String.format("Банкнота с номером %s не может быть принята, ошибка добавления банкноты в лоток.",
                            banknote.getSerialNumber()));
                }
            } else {
                errorMessages.add(String.format("Банкнота с номером %s не может быть принята, недопустимый номинал.",
                        banknote.getSerialNumber()));
            }
        }
        if (!errorMessages.isEmpty()) {
            // TODO: бросать исключение: Возникли ошибки при получении бакнот. ${errorMessages}
        }
    }

    public Set<Banknote> getMoney(int requestedSum) {
        Set<Banknote> resultBanknotes = new HashSet<>();
        int remainSum = requestedSum;
        List<Denomination> reversedDenominations = Arrays.asList(Denomination.values()).reversed();

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

            if (divisionReminder == 0) { break; }
        }

        if (remainSum != 0) {
            // TODO бросать исключение: Запрошенная сумма не может быть выдана.
        }
        return resultBanknotes;
    }

}
