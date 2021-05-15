package com.orgyflame.springtelegrambotapi.bot;

import com.orgyflame.springtelegrambotapi.api.object.Update;
import com.orgyflame.springtelegrambotapi.bot.mapping.parameter.*;

import java.lang.reflect.Parameter;

public class BotUtil {
    public static Object parseUpdateForParameter(Parameter parameter, Update update) {
        if (parameter.getAnnotation(UpdateParam.class) != null) {
            return update;
        }

        if (update == null) return null;

        if (parameter.getAnnotation(MessageParam.class) != null) {
            return update.getMessage();
        }

        if (parameter.getAnnotation(CallbackQueryParam.class) != null) {
            return update.getCallbackQuery();
        }

        if (update.getMessage() != null) {
            if (parameter.getAnnotation(TextParam.class) != null) {
                return update.getMessage().getText();
            }

            if (parameter.getAnnotation(AudioParam.class) != null) {
                return update.getMessage().getAudio();
            }

            if (parameter.getAnnotation(DateParam.class) != null) {
                return update.getMessage().getDate();
            }

            if (parameter.getAnnotation(LocationParam.class) != null) {
                return update.getMessage().getLocation();
            }

            if (parameter.getAnnotation(PhotoParam.class) != null) {
                return update.getMessage().getPhoto();
            }

            if (parameter.getAnnotation(AnimationParam.class) != null) {
                return update.getMessage().getAnimation();
            }

            if (parameter.getAnnotation(VideoParam.class) != null) {
                return update.getMessage().getVideo();
            }

            if (parameter.getAnnotation(ChatParam.class) != null) {
                return update.getMessage().getChat();
            }

            if (parameter.getAnnotation(FromParam.class) != null) {
                return update.getMessage().getFrom();

            }
        } else {
            if (parameter.getAnnotation(ChatParam.class) != null) {
                return update.getCallbackQuery().getMessage().getChat();

            }

            if (parameter.getAnnotation(FromParam.class) != null) {
                return update.getCallbackQuery().getFrom();

            }
        }


        return null;
    }
}
