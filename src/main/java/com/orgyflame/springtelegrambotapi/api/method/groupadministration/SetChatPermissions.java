package com.orgyflame.springtelegrambotapi.api.method.groupadministration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.orgyflame.springtelegrambotapi.api.method.BotApiMethod;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.api.object.ChatPermissions;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiRequestException;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 4.4
 * Use this method to set default chat permissions for all members.
 * The bot must be an administrator in the group or a supergroup
 * for this to work and must have the can_restrict_members admin rights.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetChatPermissions extends BotApiMethod<Boolean> {
    public static final String PATH = "setChatPermissions";

    private static final String CHAT_ID_FIELD = "chat_id";
    private static final String PERMISSIONS_FIELD = "permissions";

    @JsonProperty(CHAT_ID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the target chat or username of the target supergroup (in the format @supergroupusername)
    @JsonProperty(PERMISSIONS_FIELD)
    @NonNull
    private ChatPermissions permissions; ///< New default chat permissions

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Boolean>>() {
                    });
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error setting chat description", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null || chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId can't be empty", this);
        }
        if (permissions == null) {
            throw new TelegramApiValidationException("Permissions can't be null", this);
        }
    }
}
