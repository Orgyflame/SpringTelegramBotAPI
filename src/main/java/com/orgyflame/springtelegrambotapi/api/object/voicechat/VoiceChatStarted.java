package com.orgyflame.springtelegrambotapi.api.object.voicechat;

import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import lombok.*;

/**
 * @author Ruben Bermudez
 * @version 5.1
 * <p>
 * This object represents a service message about a voice chat started in the chat.
 * <p>
 * Currently holds no information.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
public class VoiceChatStarted implements BotApiObject {
}
