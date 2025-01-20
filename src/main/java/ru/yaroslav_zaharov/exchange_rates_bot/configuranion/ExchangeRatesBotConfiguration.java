package ru.yaroslav_zaharov.exchange_rates_bot.configuranion;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.yaroslav_zaharov.exchange_rates_bot.bot.ExchangeRatesBot;

@Configuration
public class ExchangeRatesBotConfiguration {

    @Bean
    public TelegramBotsApi telegramBotsApi(ExchangeRatesBot exchangeRatesBot) throws TelegramApiException {
        var api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(exchangeRatesBot);
        return api;
    }

    @Bean
    OkHttpClient okHttpClient() {
        return new OkHttpClient();
    }
}