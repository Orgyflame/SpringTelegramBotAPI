package com.orgyflame.springtelegrambotapi.api.service;

import com.orgyflame.springtelegrambotapi.TelegramBotProperties;
import com.orgyflame.springtelegrambotapi.api.ApiConstants;
import com.orgyflame.springtelegrambotapi.api.method.BotApiMethod;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiRequestException;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Spring default service for send Telegram Bot API requests
 */
@EnableConfigurationProperties(TelegramBotProperties.class)
public class DefaultTelegramApiService implements TelegramApiService{
    private final WebClient webClient;

    public DefaultTelegramApiService(TelegramBotProperties telegramBotProperties) {
        this.webClient = WebClient.create(ApiConstants.BASE_URL + telegramBotProperties.getToken() + "/");
    }

    public Mono<ApiResponse> sendApiMethod(BotApiMethod botApiMethod) throws TelegramApiValidationException {
        botApiMethod.validate();
        Mono<BotApiMethod> botApiMethodMono = Mono.just(botApiMethod);

        return webClient.post()
                .uri(botApiMethod.getMethod())
                .body(botApiMethodMono, botApiMethod.getClass())
                .retrieve()
                .onStatus(
                        HttpStatus::is4xxClientError,
                        clientResponse -> clientResponse
                                .bodyToMono(ApiResponse.class)
                                .map(apiResponse -> new TelegramApiRequestException(apiResponse.getErrorDescription()))
                )
                .bodyToMono(ApiResponse.class);
    }
}
