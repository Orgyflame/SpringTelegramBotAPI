package com.orgyflame.springtelegrambotapi.api.object.inlinequery.result;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import com.orgyflame.springtelegrambotapi.api.Validable;
import com.orgyflame.springtelegrambotapi.api.object.inlinequery.result.serialization.InlineQueryResultDeserializer;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents one result of an inline query.
 * @apiNote All URLs passed in inline query results will be available to end users and therefore must be assumed to be public.
 */
@JsonDeserialize(using = InlineQueryResultDeserializer.class)
public interface InlineQueryResult extends Validable, BotApiObject {
}
