package ru.otus.solid.exceptions;

public class GetBanknotesFromTrayException extends Exception {
    public GetBanknotesFromTrayException(String message) {
        super("Возникло исключение при попытке получить запрошенное количество банкнот из лотка банкомата." + message);
    }
}
