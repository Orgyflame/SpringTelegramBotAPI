package com.orgyflame.springtelegrambotapi.bot.container;

import com.orgyflame.springtelegrambotapi.bot.inline.menu.CallbackQueryMethod;
import com.orgyflame.springtelegrambotapi.bot.inline.menu.InlineMenuButton;

/**
 * Spring component that stores callback methods for inline buttons
 */
public interface BotCallbackQueryContainer {
    void add(InlineMenuButton inlineMenuButton);
    CallbackQueryMethod getMethod(String uuid);
}
