package ru.yaroslav_zaharov.exchange_rates_bot.exception;

public class ServiceException extends Exception {

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
