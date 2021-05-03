package com.orgyflame.springtelegrambotapi;

import com.orgyflame.springtelegrambotapi.api.object.Update;

import java.lang.reflect.InvocationTargetException;

public class BotUpdateHandlerService {
    private BotApiMappingContainer container;

    public void setBotApiMappingContainer(BotApiMappingContainer container) {
        this.container = container;
    }

    public void handle(Update update) {
        String path;
        BotApiMapping mapping = null;

        if (update.hasMessage() && update.getMessage().hasText()) {
            path = update.getMessage().getText().split(" ")[0].trim();
            if (path.charAt(0) == '/') mapping = container.getBotApiMapping(path);
            if (mapping == null) mapping = container.getBotApiMapping("");
        }

        if (mapping == null) return;

        try {
            mapping.execute(update);
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
