package com.orgyflame.springtelegrambotapi.bot;

import com.orgyflame.springtelegrambotapi.api.object.Update;

public interface BotUpdateHandlerService {
    void handle(Update update);
}
