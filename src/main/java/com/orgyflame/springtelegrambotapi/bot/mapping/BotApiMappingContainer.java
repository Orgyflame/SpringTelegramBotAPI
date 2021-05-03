package com.orgyflame.springtelegrambotapi;

import com.orgyflame.springtelegrambotapi.api.method.commands.SetMyCommands;
import com.orgyflame.springtelegrambotapi.api.method.updates.SetWebhook;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.api.object.commands.BotCommand;
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import com.orgyflame.springtelegrambotapi.exceptions.BotApiMappingContainerException;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BotApiMappingContainer {
    private final Map<String, BotApiMapping> mappingMap = new HashMap<>();

    public void addMapping(String path, BotApiMapping botApiMapping){
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
            Mono<ApiResponse> apiResponseMono = telegramApiService.sendApiMethod(setMyCommands);
            apiResponseMono.subscribe();
        } catch (TelegramApiValidationException e) {
            e.printStackTrace();
        }
    }

}
