package com.orgyflame.springtelegrambotapi.bot;

import com.orgyflame.springtelegrambotapi.api.method.updates.SetWebhook;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import com.orgyflame.springtelegrambotapi.TelegramBotProperties;
import com.orgyflame.springtelegrambotapi.bot.container.BotApiMappingContainer;
import com.orgyflame.springtelegrambotapi.bot.mapping.BotApiMapping;
import com.orgyflame.springtelegrambotapi.bot.mapping.BotController;
import com.orgyflame.springtelegrambotapi.bot.mapping.BotMapping;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import reactor.core.publisher.Mono;

import java.lang.reflect.Method;
import java.util.Map;

public class TelegramBotListener implements ApplicationListener<ContextRefreshedEvent> {
    private final ApplicationContext applicationContext;
    private final BotApiMappingContainer botApiMappingContainer;
    private final TelegramBotProperties telegramBotProperties;
    private final TelegramApiService telegramApiService;

    public TelegramBotListener(ApplicationContext applicationContext, TelegramBotProperties telegramBotProperties, BotApiMappingContainer botApiMappingContainer) {
        this.applicationContext = applicationContext;
        this.telegramBotProperties = telegramBotProperties;
        this.telegramApiService = applicationContext.getBean(TelegramApiService.class);
        this.botApiMappingContainer = botApiMappingContainer;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Map<String, Object> botControllers = applicationContext.getBeansWithAnnotation(BotController.class);
        botControllers.forEach(
                (name, botController) -> {
                    Method[] methods = botController.getClass().getDeclaredMethods();
                    for(Method method: methods){
                        BotMapping annotation = method.getAnnotation(BotMapping.class);

                        if(annotation == null) continue;

                        botApiMappingContainer.addMapping(annotation.value(), new BotApiMapping(botController, method, annotation.description()));
                    }
                }
        );

        botApiMappingContainer.registerCommands(telegramApiService);

        registerWebhook();
    }

    private void registerWebhook() {
        SetWebhook setWebhook = new SetWebhook();
        setWebhook.setUrl(telegramBotProperties.getHostUrl() + telegramBotProperties.getCallbackMapping());

        try {
            Mono<ApiResponse> apiResponseMono = telegramApiService.sendApiMethod(setWebhook);
            apiResponseMono.subscribe();
        } catch (TelegramApiValidationException e) {
            e.printStackTrace();
        }
    }
}
