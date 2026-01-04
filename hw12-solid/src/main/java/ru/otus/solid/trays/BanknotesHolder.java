package ru.otus.solid.trays;

import java.util.Set;
import ru.otus.solid.Denomination;
import ru.otus.solid.banknotes.Banknote;
import ru.otus.solid.exceptions.GetBanknotesFromTrayException;
import ru.otus.solid.exceptions.PutBanknoteToTrayException;

public interface BanknotesHolder {

    int getCapacity(Denomination denomination);

    int getAmount(Denomination denomination);

    boolean putBanknote(Banknote banknote) throws PutBanknoteToTrayException;

    boolean putBanknotes(Set<Banknote> banknotes) throws PutBanknoteToTrayException;

    Set<Banknote> getBanknotes(Denomination denomination, int requestedAmount) throws GetBanknotesFromTrayException;
}
