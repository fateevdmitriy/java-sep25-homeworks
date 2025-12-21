package ru.otus.solid;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import ru.otus.solid.banknotes.*;

public class Main {
    public static void main(String[] args) {
        Atm atm1 = new Atm();
        System.out.println("ATM balance: " + atm1.getBalance());

        Banknote oneHundreed1 = new BanknoteOneHundred();
        Banknote twoHundreed1 = new BanknoteTwoHundred();
        Banknote fifty1 = new BanknoteFifty();
        Banknote twoThousand1 = new BanknoteTwoThousand();
        Set<Banknote> testBanknotes1 = new HashSet<>(Arrays.asList(oneHundreed1, twoHundreed1, fifty1));

        atm1.putMoney(testBanknotes1);
        System.out.println("ATM balance: " + atm1.getBalance());

        atm1.putMoney(twoThousand1);
        System.out.println("ATM balance: " + atm1.getBalance());
        System.out.println("ATM each tray's balance: " + atm1.getEachTrayBalance());

        // atm1.getMoney(300);
        // System.out.println("ATM id: " + atm1.getAtmId() + ", ATM balance: " + atm1.getBalance());
    }
}
