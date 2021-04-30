package com.orgyflame.springtelegrambotapi.api.object.voicechat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import com.orgyflame.springtelegrambotapi.api.object.User;
import lombok.*;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 5.1
 * <p>
 * This object represents a service message about new members invited to a voice chat.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class VoiceChatParticipantsInvited implements BotApiObject {
    private static final String USERS_FIELD = "users";

    @JsonProperty(USERS_FIELD)
    private List<User> users; ///< Optional. New members that were invited to the voice chat
}
