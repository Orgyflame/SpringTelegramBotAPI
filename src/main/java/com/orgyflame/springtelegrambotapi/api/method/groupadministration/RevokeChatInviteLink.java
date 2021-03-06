package com.orgyflame.springtelegrambotapi.api.method.groupadministration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Strings;
import com.orgyflame.springtelegrambotapi.api.method.BotApiMethod;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.api.object.ChatInviteLink;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiRequestException;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 5.1
 * <p>
 * Use this method to revoke an invite link created by the bot.
 * <p>
 * If the primary link is revoked, a new link is automatically generated.
 * <p>
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * <p>
 * Returns the revoked invite link as ChatInviteLink object.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevokeChatInviteLink extends BotApiMethod<ChatInviteLink> {
    public static final String PATH = "revokeChatInviteLink";

    private static final String CHATID_FIELD = "chat_id";
    private static final String INVITELINK_FIELD = "invite_link";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    @JsonProperty(INVITELINK_FIELD)
    @NonNull
    private String inviteLink; ///< The invite link to revoke

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public ChatInviteLink deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<ChatInviteLink> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<ChatInviteLink>>() {
                    });
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error creating invite link", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (Strings.isNullOrEmpty(chatId)) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (Strings.isNullOrEmpty(inviteLink)) {
            throw new TelegramApiValidationException("InviteLink can't be empty", this);
        }
    }
}
