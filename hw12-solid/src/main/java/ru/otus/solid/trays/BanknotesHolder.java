package ru.otus.solid.trays;

import ru.otus.solid.Denomination;
import ru.otus.solid.banknotes.Banknote;
import ru.otus.solid.exceptions.GetBanknotesFromTrayException;
import ru.otus.solid.exceptions.PutBanknoteToTrayException;

import java.util.Set;

public interface BanknotesHolder {

    int getCapacity(Denomination denomination);

    int getAmount(Denomination denomination);

    //void clearBanknotes(Denomination denomination);

    //Set<Banknote> getAvailableBanknotes(Denomination denomination);

    //int getBalance(Denomination denomination);

    //int getGeneralBalance();

    boolean putBanknote(Banknote banknote) throws PutBanknoteToTrayException;

    boolean putBanknotes(Set<Banknote> banknotes) throws PutBanknoteToTrayException;

    Set<Banknote> getBanknotes(Denomination denomination, int requestedAmount) throws GetBanknotesFromTrayException;

}
