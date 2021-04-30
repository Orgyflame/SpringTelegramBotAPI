package com.orgyflame.springtelegrambotapi.api.object.inlinequery.inputmessagecontent;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import com.orgyflame.springtelegrambotapi.api.Validable;
import com.orgyflame.springtelegrambotapi.api.object.inlinequery.inputmessagecontent.serialization.InputMessageContentDeserializer;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents the content of a message to be sent as a result of an inline
 * query.
 */
@JsonDeserialize(using = InputMessageContentDeserializer.class)
public interface InputMessageContent extends Validable, BotApiObject {
}
