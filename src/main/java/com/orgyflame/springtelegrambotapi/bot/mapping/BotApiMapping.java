package com.orgyflame.springtelegrambotapi;

import com.orgyflame.springtelegrambotapi.api.method.BotApiMethod;
import com.orgyflame.springtelegrambotapi.api.object.Update;
import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Mono;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Data
@AllArgsConstructor
public class BotApiMapping {
    private Object bean;
    private Method method;

    public void execute(Update update) throws InvocationTargetException, IllegalAccessException {
        method.invoke(bean, update);
    }
}
