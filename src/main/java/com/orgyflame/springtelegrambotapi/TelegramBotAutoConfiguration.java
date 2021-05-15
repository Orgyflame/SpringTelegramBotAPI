package com.orgyflame.springtelegrambotapi;

import com.orgyflame.springtelegrambotapi.api.object.Update;
import com.orgyflame.springtelegrambotapi.api.service.DefaultTelegramApiService;
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import com.orgyflame.springtelegrambotapi.bot.BotUpdateHandlerService;
import com.orgyflame.springtelegrambotapi.bot.DefaultBotUpdateHandlerService;
import com.orgyflame.springtelegrambotapi.bot.TelegramBotListener;
import com.orgyflame.springtelegrambotapi.bot.container.BotApiMappingContainer;
import com.orgyflame.springtelegrambotapi.bot.container.BotCallbackQueryContainer;
import com.orgyflame.springtelegrambotapi.bot.container.DefaultBotApiMappingContainer;
import com.orgyflame.springtelegrambotapi.bot.container.DefaultBotCallbackQueryContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
@EnableConfigurationProperties(TelegramBotProperties.class)
public class TelegramBotAutoConfiguration {
    @Autowired
    private TelegramBotProperties telegramBotProperties;

    @Bean
    @ConditionalOnMissingBean
    public TelegramApiService telegramApiService() {
        return new DefaultTelegramApiService(telegramBotProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public BotApiMappingContainer botApiMappingContainer(){
        return new DefaultBotApiMappingContainer();
    }
    @Bean
    @ConditionalOnMissingBean
    public BotCallbackQueryContainer botCallbackQueryContainer(){
        return new DefaultBotCallbackQueryContainer();
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({TelegramApiService.class, BotApiMappingContainer.class})
    public TelegramBotListener telegramBotListener(ApplicationContext context, BotApiMappingContainer botApiMappingContainer) {
        return new TelegramBotListener(context, telegramBotProperties, botApiMappingContainer);
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean({BotCallbackQueryContainer.class, BotApiMappingContainer.class})
    public BotUpdateHandlerService botUpdateHandlerService(
            BotCallbackQueryContainer botCallbackQueryContainer,
            BotApiMappingContainer botApiMappingContainer,
            TelegramApiService telegramApiService
    ) {
        return new DefaultBotUpdateHandlerService(botCallbackQueryContainer, botApiMappingContainer, telegramApiService);
    }

    @Bean
    @ConditionalOnBean(BotUpdateHandlerService.class)
    public RouterFunction<ServerResponse> routeWebhookCallback(BotUpdateHandlerService botUpdateHandlerService) {

        return RouterFunctions
                .route(POST(telegramBotProperties.getCallbackMapping()),
                        serverRequest -> serverRequest.bodyToMono(Update.class)
                                .doOnNext(botUpdateHandlerService::handle)
                                .then(ServerResponse.ok().build())
                );
    }
}
