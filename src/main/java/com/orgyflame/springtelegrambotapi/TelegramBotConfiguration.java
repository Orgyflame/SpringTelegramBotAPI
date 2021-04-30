package com.orgyflame.springtelegrambotapi;

import com.orgyflame.springtelegrambotapi.api.object.Update;
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import com.orgyflame.springtelegrambotapi.bot.TelegramBotProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.*;
import reactor.core.publisher.Mono;

@Configuration
@EnableConfigurationProperties(TelegramBotProperties.class)
public class TelegramBotConfiguration {
    private final TelegramBotProperties telegramBotProperties;

    public TelegramBotConfiguration(TelegramBotProperties telegramBotProperties) {
        this.telegramBotProperties = telegramBotProperties;

    }


    @Bean
    @ConditionalOnMissingBean
    public TelegramBotListener telegramBotListener(ApplicationContext context){
        return new TelegramBotListener(context, telegramBotProperties);
    }

    @Component
    private static class BotUpdateHandler {
        private final BotUpdateHandlerService botUpdateHandlerService;

        private BotUpdateHandler(BotUpdateHandlerService botUpdateHandlerService) {
            this.botUpdateHandlerService = botUpdateHandlerService;
        }

        public Mono<ServerResponse> handle(ServerRequest serverRequest) {
            Mono<Update> updateMono = serverRequest.bodyToMono(Update.class);
            updateMono.subscribe(botUpdateHandlerService::handle);
            return ServerResponse.noContent().build();
        }
    }

    @Bean
    @ConditionalOnBean(BotUpdateHandler.class)
    public RouterFunction<ServerResponse> route(BotUpdateHandler botUpdateHandler) {

        return RouterFunctions
                .route(RequestPredicates.POST("/callback/" + telegramBotProperties.getToken()), botUpdateHandler::handle);
    }


}
