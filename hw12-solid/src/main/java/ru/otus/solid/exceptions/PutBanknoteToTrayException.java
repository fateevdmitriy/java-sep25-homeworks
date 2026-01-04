package ru.otus.solid.exceptions;

public class PutBanknoteToTrayException extends Exception {
    public PutBanknoteToTrayException(String message) {
        super("Возникло исключение при попытке добавить банкноту в банкомат." + message);
    }
}
