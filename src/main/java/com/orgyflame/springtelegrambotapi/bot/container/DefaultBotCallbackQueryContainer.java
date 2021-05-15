package com.orgyflame.springtelegrambotapi.bot.container;

import com.orgyflame.springtelegrambotapi.bot.container.BotCallbackQueryContainer;
import com.orgyflame.springtelegrambotapi.bot.inline.menu.CallbackQueryMethod;
import com.orgyflame.springtelegrambotapi.bot.inline.menu.InlineMenuButton;

import java.util.HashMap;
import java.util.Map;

/**
 * Spring default component that stores callback methods for inline buttons
 */
public class DefaultBotCallbackQueryContainer implements BotCallbackQueryContainer {
    private final Map<String, CallbackQueryMethod> callbackQueryMethodMap = new HashMap<>();

    public void add(InlineMenuButton inlineMenuButton){
        callbackQueryMethodMap.put(inlineMenuButton.getUuid(), inlineMenuButton.getOnQueryCallback());
    }

    public CallbackQueryMethod getMethod(String uuid){
        return callbackQueryMethodMap.get(uuid);
    }
}
