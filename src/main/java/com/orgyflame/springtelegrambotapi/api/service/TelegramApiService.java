package com.orgyflame.springtelegrambotapi.api.service;

import com.orgyflame.springtelegrambotapi.api.method.BotApiMethod;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import reactor.core.publisher.Mono;

/**
 * Spring service for send Telegram Bot API requests
 */
public interface TelegramApiService {
    Mono<ApiResponse> sendApiMethod(BotApiMethod botApiMethod)
            throws TelegramApiValidationException;
}
