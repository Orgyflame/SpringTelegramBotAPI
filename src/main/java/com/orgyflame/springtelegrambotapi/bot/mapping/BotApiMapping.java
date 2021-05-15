package com.orgyflame.springtelegrambotapi.bot.mapping;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Data
@AllArgsConstructor
public class BotApiMapping {
    private Object bean;
    private Method method;
    private String description;

    public void execute(Object[] args) throws InvocationTargetException, IllegalAccessException {
        method.invoke(bean, args);
    }
}
