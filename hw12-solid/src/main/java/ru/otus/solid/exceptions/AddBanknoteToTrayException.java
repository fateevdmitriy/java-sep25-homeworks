package ru.otus.solid.exceptions;

public class AddBanknoteToTrayException extends Exception {
    public AddBanknoteToTrayException(String message) {
        super("Возникло исключение при попытке добавить банкноту в лоток банкомата." + message);
    }
}
