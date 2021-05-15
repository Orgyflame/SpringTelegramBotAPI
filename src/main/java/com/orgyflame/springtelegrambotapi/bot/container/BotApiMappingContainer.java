package com.orgyflame.springtelegrambotapi.bot.container;

import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import com.orgyflame.springtelegrambotapi.bot.mapping.BotApiMapping;

public interface BotApiMappingContainer {
    void addMapping(String path, BotApiMapping botApiMapping);
    BotApiMapping getBotApiMapping(String path);
    void registerCommands(TelegramApiService telegramApiService);
}
