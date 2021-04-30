package com.orgyflame.springtelegrambotapi.api.service;

import com.orgyflame.springtelegrambotapi.api.ApiConstants;
import com.orgyflame.springtelegrambotapi.bot.TelegramBotProperties;
import com.orgyflame.springtelegrambotapi.api.method.BotApiMethod;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@EnableConfigurationProperties(TelegramBotProperties.class)
public class TelegramApiService {
    private final WebClient webClient;

    public TelegramApiService(TelegramBotProperties telegramBotProperties) {
        this.webClient = WebClient.create(ApiConstants.BASE_URL + telegramBotProperties.getToken() + "/");
    }

    public Mono<ApiResponse> sendApiMethod(BotApiMethod botApiMethod) throws TelegramApiValidationException {
        botApiMethod.validate();
        Mono<BotApiMethod> botApiMethodMono = Mono.just(botApiMethod);

        return webClient.post()
                .uri(botApiMethod.getMethod())
                .body(botApiMethodMono, botApiMethod.getClass())
                .retrieve()
                .bodyToMono(ApiResponse.class);
    }
}
