package com.orgyflame.springtelegrambotapi.api.method.send;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.orgyflame.springtelegrambotapi.api.method.ActionType;
import com.orgyflame.springtelegrambotapi.api.method.BotApiMethod;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiRequestException;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method when you need to tell the user that something is happening on the bot's
 * side. The status is set for 5 seconds or less (when a message arrives from your bot, Telegram
 * clients clear its typing status).
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendChatAction extends BotApiMethod<Boolean> {

    public static final String PATH = "sendChatAction";

    public static final String CHATID_FIELD = "chat_id";
    private static final String ACTION_FIELD = "action";

    @JsonProperty(CHATID_FIELD)
    @NonNull
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    /**
     * Type of action to broadcast. Choose one, depending on what the user is about to receive: typing for text messages,
     * upload_photo for photos, record_video or upload_video for videos, record_audio or upload_audio for audio files,
     * upload_document for general files, find_location for location data,
     * record_video_note or upload_video_note for video notes.
     */
    @JsonProperty(ACTION_FIELD)
    @NonNull
    private String action;

    @JsonIgnore
    public ActionType getActionType() {
        return ActionType.get(action);
    }

    @JsonIgnore
    public void setAction(ActionType action) {
        this.action = action.toString();
    }

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
                throw new TelegramApiRequestException("Error sending chat action", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (chatId == null || chatId.isEmpty()) {
            throw new TelegramApiValidationException("ChatId parameter can't be empty", this);
        }
        if (action == null) {
            throw new TelegramApiValidationException("Action parameter can't be empty", this);
        }
    }
}
