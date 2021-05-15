package com.orgyflame.springtelegrambotapi.bot.container;

import com.orgyflame.springtelegrambotapi.api.method.commands.SetMyCommands;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.api.object.commands.BotCommand;
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import com.orgyflame.springtelegrambotapi.bot.mapping.BotApiMapping;
import com.orgyflame.springtelegrambotapi.exceptions.BotApiMappingContainerException;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class DefaultBotApiMappingContainer implements BotApiMappingContainer {
    private final Map<String, BotApiMapping> mappingMap = new HashMap<>();

    public void addMapping(String path, BotApiMapping botApiMapping){
        if(path.split(" ").length > 1){
            throw new BotApiMappingContainerException("Incorrect command format for " + path);
        }

        if(!path.equals("") && path.charAt(0) != '/'){
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
            botCommand.setDescription(botApiMapping.getDescription());
            if(!s.equals("")) setMyCommands.getCommands().add(botCommand);
        });

        try {
            Mono<ApiResponse> apiResponseMono = telegramApiService.sendApiMethod(setMyCommands);
            apiResponseMono.subscribe();
        } catch (TelegramApiValidationException e) {
            e.printStackTrace();
        }
    }

}
