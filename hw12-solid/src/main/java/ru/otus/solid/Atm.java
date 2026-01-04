package ru.otus.solid;

import java.util.*;
import ru.otus.solid.banknotes.Banknote;
import ru.otus.solid.exceptions.GetBanknotesFromTrayException;
import ru.otus.solid.exceptions.PutBanknoteToTrayException;

public interface Atm {

    int getAmount(Denomination denomination);

    int getBalance(Denomination denomination);

    int getGeneralBalance();

    boolean putMoney(Banknote banknote) throws PutBanknoteToTrayException;

    boolean putMoney(Set<Banknote> money) throws PutBanknoteToTrayException;

    Set<Banknote> getMoney(int requestedSum) throws GetBanknotesFromTrayException;
}
