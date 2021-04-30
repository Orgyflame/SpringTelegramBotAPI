package com.orgyflame.springtelegrambotapi.api.method.stickers;

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
 * @version 1.0
 * Use this method to move a sticker in a set created by the bot to a specific position. Returns True on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SetStickerPositionInSet extends BotApiMethod<Boolean> {
    private static final String PATH = "setStickerPositionInSet";

    private static final String STICKER_FIELD = "sticker";
    private static final String POSITION_FIELD = "position";

    @JsonProperty(STICKER_FIELD)
    @NonNull
    private String sticker; ///< File identifier of the sticker
    @JsonProperty(POSITION_FIELD)
    @NonNull
    private Integer position; ///< New sticker position in the set, zero-based

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
                throw new TelegramApiRequestException("Error setting sticker position in set", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (sticker == null || sticker.isEmpty()) {
            throw new TelegramApiValidationException("sticker can't be null", this);
        }
        if (position == null || position < 0) {
            throw new TelegramApiValidationException("position can't be null", this);
        }
    }
}
