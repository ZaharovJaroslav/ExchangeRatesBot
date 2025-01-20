package ru.yaroslav_zaharov.exchange_rates_bot.service;

import ru.yaroslav_zaharov.exchange_rates_bot.exception.ServiceException;

public interface ExchangeRatesService {
    String getUSDExchangeRate() throws ServiceException;

    String getEURExchangeRate() throws ServiceException;
}
