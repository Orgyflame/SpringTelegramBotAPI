package com.orgyflame.springtelegrambotapi;

import com.orgyflame.springtelegrambotapi.api.method.commands.SetMyCommands;
import com.orgyflame.springtelegrambotapi.api.method.updates.SetWebhook;
import com.orgyflame.springtelegrambotapi.api.object.commands.BotCommand;
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import com.orgyflame.springtelegrambotapi.exceptions.BotApiMappingContainerException;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BotApiMappingContainer {
    private Map<String, BotApiMapping> mappingMap;

    public void addMapping(String path, BotApiMapping botApiMapping){
        if(path.charAt(0) != '/'){
            throw new BotApiMappingContainerException("Command must start with /");
        }

        if(mappingMap.containsKey(path)) {
            throw new BotApiMappingContainerException("BotMapping with command " + path + " already add");
        }

        mappingMap.put(path, botApiMapping);
    }

    public BotApiMapping getBotApiMapping(String path){
        return mappingMap.get(path);
    }

    public void registerCommands(TelegramApiService telegramApiService){
        SetMyCommands setMyCommands = new SetMyCommands();
        mappingMap.forEach((s, botApiMapping) -> {
            BotCommand botCommand = new BotCommand();
            botCommand.setCommand(s);
            setMyCommands.getCommands().add(botCommand);
        });

        try {
            telegramApiService.sendApiMethod(setMyCommands);
        } catch (TelegramApiValidationException e) {
            e.printStackTrace();
        }
    }

}
