package com.orgyflame.springtelegrambotapi.api.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author Ruben Bermudez
 * @version 5.0
 * This object represents a unique message identifier.
 */
@SuppressWarnings("WeakerAccess")
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MessageId implements BotApiObject {

    private static final String MESSAGEID_FIELD = "message_id";

    @JsonProperty(MESSAGEID_FIELD)
    private Long messageId; ///< Unique message identifier

}
