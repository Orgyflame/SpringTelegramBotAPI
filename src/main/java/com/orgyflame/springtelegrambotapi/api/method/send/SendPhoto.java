package com.orgyflame.springtelegrambotapi.api.method.send;

import com.fasterxml.jackson.core.type.TypeReference;
import com.orgyflame.springtelegrambotapi.api.method.PartialBotApiMethod;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.api.object.InputFile;
import com.orgyflame.springtelegrambotapi.api.object.Message;
import com.orgyflame.springtelegrambotapi.api.object.MessageEntity;
import com.orgyflame.springtelegrambotapi.api.object.replykeyboard.ReplyKeyboard;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiRequestException;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send photos. On success, the sent Message is returned.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SendPhoto extends PartialBotApiMethod<Message> {
    public static final String PATH = "sendphoto";

    public static final String CHATID_FIELD = "chat_id";
    public static final String PHOTO_FIELD = "photo";
    public static final String CAPTION_FIELD = "caption";
    public static final String DISABLENOTIFICATION_FIELD = "disable_notification";
    public static final String REPLYTOMESSAGEID_FIELD = "reply_to_message_id";
    public static final String REPLYMARKUP_FIELD = "reply_markup";
    public static final String PARSEMODE_FIELD = "parse_mode";
    public static final String CAPTION_ENTITIES_FIELD = "caption_entities";
    public static final String ALLOWSENDINGWITHOUTREPLY_FIELD = "allow_sending_without_reply";

    @NonNull
    private String chatId; ///< Unique identifier for the chat to send the message to (Or username for channels)
    @NonNull
    private InputFile photo; ///< Photo to send. file_id as String to resend a photo that is already on the Telegram servers or URL to upload it
    private String caption; ///< Optional Photo caption (may also be used when resending photos by file_id).
    private Boolean disableNotification; ///< Optional. Sends the message silently. Users will receive a notification with no sound.
    private Integer replyToMessageId; ///< Optional. If the message is a reply, ID of the original message
    private ReplyKeyboard replyMarkup; ///< Optional. JSON-serialized object for a custom reply keyboard
    private String parseMode; ///< Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
    @Singular
    private List<MessageEntity> captionEntities; ///< Optional. 	List of special entities that appear in the caption, which can be specified instead of parse_mode
    private Boolean allowSendingWithoutReply; ///< Optional	Pass True, if the message should be sent even if the specified replied-to message is not found

    public void enableNotification() {
        this.disableNotification = false;
    }

    public void disableNotification() {
        this.disableNotification = true;
    }

    public void setPhoto(InputFile photo) {
        Objects.requireNonNull(photo, "photo cannot be null!");
        this.photo = photo;
    }

    @Override
    public Message deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Message> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Message>>() {
                    });
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error sending photo", result);
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

        if (photo == null) {
            throw new TelegramApiValidationException("Photo parameter can't be empty", this);
        }

        if (parseMode != null && (captionEntities != null && !captionEntities.isEmpty())) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if Entities are provided", this);
        }
        photo.validate();

        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }
}
