package com.orgyflame.springtelegrambotapi;

import com.orgyflame.springtelegrambotapi.api.method.updates.SetWebhook;
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import com.orgyflame.springtelegrambotapi.bot.BotController;
import com.orgyflame.springtelegrambotapi.bot.BotRequestMapping;
import com.orgyflame.springtelegrambotapi.bot.TelegramBotProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.Method;
import java.util.Map;

public class TelegramBotListener implements ApplicationListener<ContextRefreshedEvent> {
    private final ApplicationContext applicationContext;
    private final BotApiMappingContainer botApiMappingContainer;
    private final TelegramBotProperties telegramBotProperties;
    private final TelegramApiService telegramApiService;

    public TelegramBotListener(ApplicationContext applicationContext, TelegramBotProperties telegramBotProperties) {
        this.applicationContext = applicationContext;
        this.telegramBotProperties = telegramBotProperties;
        this.telegramApiService = applicationContext.getBean(TelegramApiService.class);
        this.botApiMappingContainer = new BotApiMappingContainer();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Map<String, Object> botControllers = applicationContext.getBeansWithAnnotation(BotController.class);
        botControllers.forEach(
                (name, botController) -> {
                    Method[] methods = botController.getClass().getDeclaredMethods();
                    for(Method method: methods){
                        BotRequestMapping annotation = method.getAnnotation(BotRequestMapping.class);

                        if(annotation == null) continue;

                        botApiMappingContainer.addMapping(annotation.value(), new BotApiMapping(botController, method));
                    }
                }
        );

        applicationContext.getBean(BotUpdateHandlerService.class).setBotApiMappingContainer(botApiMappingContainer);
        botApiMappingContainer.registerCommands(telegramApiService);

        registerWebhook();
    }

    private void registerWebhook() {
        SetWebhook setWebhook = new SetWebhook();
        setWebhook.setUrl(telegramBotProperties.getHostUrl() + "/callback/" + telegramBotProperties.getToken());
    }
}
