package com.orgyflame.springtelegrambotapi.api.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author Ruben Bermudez
 * @version 4.7
 * This object represents an animated emoji that displays a random value.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Dice implements BotApiObject {
    private static final String VALUE_FIELD = "value";
    private static final String EMOJI_FIELD = "emoji";

    /**
     * Value of the dice,
     * 1-6 for “🎲”, “🎯” and “🎳” base emoji,
     * 1-5 for “🏀” and “⚽” base emoji,
     * 1-64 for “🎰” base emoji
     */
    @JsonProperty(VALUE_FIELD)
    private Integer value;
    @JsonProperty(EMOJI_FIELD)
    private String emoji; ///< Emoji on which the dice throw animation is based
}
