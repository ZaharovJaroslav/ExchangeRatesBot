package ru.yaroslav_zaharov.exchange_rates_bot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.yaroslav_zaharov.exchange_rates_bot.exception.ServiceException;
import ru.yaroslav_zaharov.exchange_rates_bot.service.ExchangeRatesService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class ExchangeRatesBot extends TelegramLongPollingBot {
    @Autowired
    private ExchangeRatesService exchangeRatesService;

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String START = "/start";
    private static final String USD = "/usd";
    private static final String EUR = "/eur";
    private static final String HELP = "/help";

    public ExchangeRatesBot(@Value("${bot.token}") String botToken) {
        super(botToken);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        var message = update.getMessage().getText();
        var chatId = update.getMessage().getChatId();
        switch (message) {
            case START -> {
                String userName = update.getMessage().getChat().getUserName();
                startCommand(chatId, userName);
            }
            case USD -> usdCommand(chatId);
            case EUR -> eurCommand(chatId);
            case HELP -> helpCommand(chatId);
            default -> unknownCommand(chatId);
        }
    }

    @Override
    public String getBotUsername() {
        return "zaharov_er_bot";
    }

    private void startCommand(Long chatId, String userName) {
        var text = """
                Добро пожаловать в бот, %s!
                
                Здесь Вы сможете узнать официальные курсы валют на сегодня, установленные ЦБ РФ.
                
                Для этого воспользуйтесь командами:
                /usd - курс доллара
                /eur - курс евро
                
                Дополнительные команды:
                /help - получение справки
                """;
        var formattedText = String.format(text, userName);
        sendMessage(chatId, formattedText);
    }

    private void usdCommand(Long chatId) {
        String formattedText;
        try {
            var usd = exchangeRatesService.getUSDExchangeRate();
            var text = "Курс доллара на %s составляет %s рублей";
            formattedText = String.format(text, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)), usd);
        } catch (ServiceException e) {
            log.error("Ошибка получения курса доллара", e);
            formattedText = "Не удалось получить текущий курс доллара. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);
    }

    private void eurCommand(Long chatId) {
        String formattedText;
        try {
            var eur = exchangeRatesService.getEURExchangeRate();
            var text = "Курс евро на %s составляет %s рублей";
            formattedText = String.format(text, LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT)), eur);
        } catch (ServiceException e) {
            log.error("Ошибка получения курса евро", e);
            formattedText = "Не удалось получить текущий курс евро. Попробуйте позже.";
        }
        sendMessage(chatId, formattedText);
    }

    private void helpCommand(Long chatId) {
        var text = """
                Справочная информация по боту
                
                Для получения текущих курсов валют воспользуйтесь командами:
                /usd - курс доллара
                /eur - курс евро
                """;
        sendMessage(chatId, text);
    }

    public void unknownCommand(Long chatId) {
        var text = "Не удалось распознать команду";
        sendMessage(chatId, text);
    }

    private void sendMessage(Long cahtId, String text) {
        var chatIdStr = String.valueOf(cahtId);
        var sendMessage = new SendMessage(chatIdStr,text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }
}