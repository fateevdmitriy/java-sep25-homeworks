package ru.otus.solid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import ru.otus.solid.banknotes.*;

public class Main {
    public static void main(String[] args) {
        Atm atm1 = new Atm();
        System.out.println("ATM balance: " + atm1.getBalance());
        System.out.println("ATM each tray's amount: " + atm1.getEachTrayAmount());

        Banknote oneHundreed1 = new BanknoteImpl(Denomination.ONE_HUNDRED);
        BanknoteImpl twoHundreed1 = new BanknoteImpl(Denomination.TWO_HUNDRED);
        BanknoteImpl fifty1 = new BanknoteImpl(Denomination.FIFTY);
        BanknoteImpl twoThousand1 = new BanknoteImpl(Denomination.TWO_THOUSAND);
        Set<Banknote> testBanknotes1 = new HashSet<>(Arrays.asList(oneHundreed1, twoHundreed1, fifty1));

        atm1.putMoney(testBanknotes1);
        System.out.println("ATM balance: " + atm1.getBalance());

        atm1.putMoney(twoThousand1);
        System.out.println("ATM balance: " + atm1.getBalance());
        System.out.println("ATM each tray's balance: " + atm1.getEachTrayAmount());

        atm1.getMoney(300);
        System.out.println("ATM each tray's balance: " + atm1.getEachTrayAmount());
    }
}
