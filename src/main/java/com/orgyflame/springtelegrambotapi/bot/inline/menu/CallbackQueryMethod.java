package com.orgyflame.springtelegrambotapi.bot.inline.menu;

import com.orgyflame.springtelegrambotapi.api.method.AnswerCallbackQuery;
import com.orgyflame.springtelegrambotapi.api.object.Update;

@FunctionalInterface
public interface CallbackQueryMethod {
    AnswerCallbackQuery callbackQueryMethod(Update update);
}
