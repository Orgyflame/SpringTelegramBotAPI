package com.orgyflame.springtelegrambotapi.api.object.inlinequery.result.cached;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orgyflame.springtelegrambotapi.api.object.inlinequery.inputmessagecontent.InputMessageContent;
import com.orgyflame.springtelegrambotapi.api.object.inlinequery.result.InlineQueryResult;
import com.orgyflame.springtelegrambotapi.api.object.replykeyboard.InlineKeyboardMarkup;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Represents a link to a sticker stored on the Telegram servers. By default, this sticker
 * will be sent by the user. Alternatively, you can use input_message_content to send a message with
 * the specified content instead of the sticker.
 * @apiNote This will only work in Telegram versions released after 9 April, 2016. Older clients will
 * ignore them.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InlineQueryResultCachedSticker implements InlineQueryResult {

    private static final String TYPE_FIELD = "type";
    private static final String ID_FIELD = "id";
    private static final String STICKER_FILE_ID_FIELD = "sticker_file_id";
    private static final String INPUTMESSAGECONTENT_FIELD = "input_message_content";
    private static final String REPLY_MARKUP_FIELD = "reply_markup";

    @JsonProperty(TYPE_FIELD)
    private final String type = "sticker"; ///< Type of the result, must be "sticker"
    @JsonProperty(ID_FIELD)
    @NonNull
    private String id; ///< Unique identifier of this result, 1-64 bytes
    @JsonProperty(STICKER_FILE_ID_FIELD)
    @NonNull
    private String stickerFileId; ///< A valid file identifier of the sticker
    @JsonProperty(INPUTMESSAGECONTENT_FIELD)
    private InputMessageContent inputMessageContent; ///< Optional. Content of the message to be sent instead of the sticker
    @JsonProperty(REPLY_MARKUP_FIELD)
    private InlineKeyboardMarkup replyMarkup; ///< Optional. Inline keyboard attached to the message

    @Override
    public void validate() throws TelegramApiValidationException {
        if (id == null || id.isEmpty()) {
            throw new TelegramApiValidationException("ID parameter can't be empty", this);
        }
        if (stickerFileId == null || stickerFileId.isEmpty()) {
            throw new TelegramApiValidationException("StickerFileId parameter can't be empty", this);
        }
        if (inputMessageContent != null) {
            inputMessageContent.validate();
        }
        if (replyMarkup != null) {
            replyMarkup.validate();
        }
    }
}
