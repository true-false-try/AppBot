package com.bot.psybot;

import com.bot.psybot.config.BotConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Component
@RequiredArgsConstructor
public class StarterBot extends TelegramLongPollingBot {
    private final BotConfig botConfig;

    @Override
    public void onUpdateReceived(Update update) {
        String chatId  = update.getMessage().getChatId().toString();
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Hello There");
        log.info("Received update");

        sendApiMethodAsync(sendMessage);
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }
    @Override
    public String getBotToken() {
        return botConfig.getBotKey();
    }
}
