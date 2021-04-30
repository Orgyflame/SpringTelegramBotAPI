package com.orgyflame.springtelegrambotapi.api.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author Ruben Bermudez
 * @version 5.1
 * <p>
 * This object represents a service message about a change in auto-delete timer settings.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageAutoDeleteTimerChanged implements BotApiObject {
    private static final String MESSAGEAUTODELETETIME_FIELD = "message_auto_delete_time";

    @JsonProperty(MESSAGEAUTODELETETIME_FIELD)
    private Integer messageAutoDeleteTime; ///< New auto-delete time for messages in the chat
}
