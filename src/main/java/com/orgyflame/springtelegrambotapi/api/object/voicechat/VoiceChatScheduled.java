package com.orgyflame.springtelegrambotapi.api.object.voicechat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import lombok.*;

/**
 * @author Ruben Bermudez
 * @version 5.2
 * <p>
 * This object represents a service message about a voice chat scheduled in the chat.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VoiceChatScheduled implements BotApiObject {
    private static final String STARTDATE_FIELD = "start_date";

    @JsonProperty(STARTDATE_FIELD)
    @NonNull
    private Integer startDate; ///< Point in time (Unix timestamp) when the voice chat is supposed to be started by a chat administrator
}
