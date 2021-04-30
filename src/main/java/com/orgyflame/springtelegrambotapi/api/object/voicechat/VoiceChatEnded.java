package com.orgyflame.springtelegrambotapi.api.object.voicechat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import lombok.*;

/**
 * @author Ruben Bermudez
 * @version 5.1
 * <p>
 * This object represents a service message about a voice chat ended in the chat.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VoiceChatEnded implements BotApiObject {
    private static final String DURATION_FIELD = "duration";

    @JsonProperty(DURATION_FIELD)
    private Integer duration; ///< Voice chat duration; in seconds
}
