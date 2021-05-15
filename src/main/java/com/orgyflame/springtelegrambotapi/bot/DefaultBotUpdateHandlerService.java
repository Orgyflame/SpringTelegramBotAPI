package com.orgyflame.springtelegrambotapi.bot;

import com.orgyflame.springtelegrambotapi.api.method.AnswerCallbackQuery;
import com.orgyflame.springtelegrambotapi.api.object.Update;
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import com.orgyflame.springtelegrambotapi.bot.mapping.BotApiMapping;
import com.orgyflame.springtelegrambotapi.bot.container.BotApiMappingContainer;
import com.orgyflame.springtelegrambotapi.bot.container.BotCallbackQueryContainer;
import com.orgyflame.springtelegrambotapi.bot.inline.menu.CallbackQueryMethod;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.orgyflame.springtelegrambotapi.bot.BotUtil.parseUpdateForParameter;

public class DefaultBotUpdateHandlerService implements BotUpdateHandlerService{
    private final BotApiMappingContainer botApiMappingContainer;
    private final BotCallbackQueryContainer botCallbackQueryContainer;
    private final TelegramApiService telegramApiService;

    public DefaultBotUpdateHandlerService(BotCallbackQueryContainer botCallbackQueryContainer, BotApiMappingContainer botApiMappingContainer, TelegramApiService telegramApiService) {
        this.botCallbackQueryContainer = botCallbackQueryContainer;
        this.botApiMappingContainer = botApiMappingContainer;
        this.telegramApiService = telegramApiService;
    }


    public void handle(Update update) {
        if (update.hasCallbackQuery()) {
            handleCallback(update);
        } else {
            handleMessage(update);
        }
    }

    private void handleMessage(Update update) {
        String path;
        BotApiMapping mapping = null;

        if (update.hasMessage() && update.getMessage().hasText()) {
            path = update.getMessage().getText().split(" ")[0].trim();
            if (path.charAt(0) == '/') {
                mapping = botApiMappingContainer.getBotApiMapping(path);

                update.getMessage().setText(
                        Arrays.stream(update.getMessage().getText().split(" "))
                                .skip(1)
                                .reduce((s, s2) -> s + s2)
                                .orElse("")
                                .trim()
                );
            }

        }

        if (mapping == null) mapping = botApiMappingContainer.getBotApiMapping("");
        List<Object> args = parseParameters(mapping.getMethod().getParameters(), update);

        try {
            mapping.execute(args.toArray());
        } catch (InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void handleCallback(Update update) {
        String callbackData = update.getCallbackQuery().getData();

        CallbackQueryMethod methodInterface = botCallbackQueryContainer.getMethod(callbackData);

        if (methodInterface == null) {
            handleMessage(update);
            return;
        }

       /* Method method = methodInterface.getClass().getMethods()[0];

        List<Object> args = parseParameters(method.getParameters(), update);*/

        AnswerCallbackQuery answerCallbackQuery = methodInterface.callbackQueryMethod(update);

        if(answerCallbackQuery != null){
            try {
                telegramApiService.sendApiMethod(answerCallbackQuery).subscribe();
            } catch (TelegramApiValidationException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Object> parseParameters(Parameter[] parameters, Update update) {
        List<Object> res = new ArrayList<>();

        for (Parameter parameter : parameters) {
            Object obj = parseUpdateForParameter(parameter, update);
            res.add(obj);
        }

        return res;
    }



}
