package com.orgyflame.springtelegrambotapi.api.method.groupadministration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.orgyflame.springtelegrambotapi.api.method.BotApiMethod;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiRequestException;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 3.1
 * Use this method to change the description of a supergroup or channels.
 * The bot must be an administrator in the chat for this to work and must have the appropriate admin rights.
 * Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class SetChatDescription extends BotApiMethod<Boolean> {
    public static final String PATH = "setChatDescription";

    private static final String CHATID_FIELD = "chat_id";
    private static final String DESCRIPTION_FIELD = "description";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the target chat or username of the target channel (in the format @channelusername)
    @JsonProperty(DESCRIPTION_FIELD)
    private String description; ///< Optional. New chat description, 0-255 characters

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
        if (description == null) {
            throw new TelegramApiValidationException("Description can't be null", this);
        }
    }
}
