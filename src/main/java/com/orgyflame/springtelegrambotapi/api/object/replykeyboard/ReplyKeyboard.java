package com.orgyflame.springtelegrambotapi.api.object.replykeyboard;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import com.orgyflame.springtelegrambotapi.api.Validable;
import com.orgyflame.springtelegrambotapi.api.object.replykeyboard.serialization.KeyboardDeserializer;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Reply keyboard abstract type
 */
@JsonDeserialize(using = KeyboardDeserializer.class)
public interface ReplyKeyboard extends BotApiObject, Validable {
}
