package com.orgyflame.springtelegrambotapi.api.method.stickers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.orgyflame.springtelegrambotapi.api.method.PartialBotApiMethod;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.api.object.File;
import com.orgyflame.springtelegrambotapi.api.object.InputFile;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiRequestException;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to upload a .png file with a sticker for later use in createNewStickerSet and addStickerToSet
 * methods (can be used multiple times). Returns the uploaded File on success.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadStickerFile extends PartialBotApiMethod<File> {
    public static final String PATH = "uploadStickerFile";

    public static final String USERID_FIELD = "user_id";
    public static final String PNGSTICKER_FIELD = "png_sticker";

    @NonNull
    private Long userId; ///< User identifier of sticker file owner
    /**
     * Png image with the sticker, must be up to 512 kilobytes in size, dimensions must not exceed 512px,
     * and either width or height must be exactly 512px. More info on Sending Files »
     */
    @NonNull
    private InputFile pngSticker; ///< New sticker file

    @Override
    public File deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<File> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<File>>() {
                    });
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error uploading sticker set", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (userId == null || userId <= 0) {
            throw new TelegramApiValidationException("userId can't be empty", this);
        }

        if (pngSticker == null) {
            throw new TelegramApiValidationException("PngSticker parameter can't be empty", this);
        }

        pngSticker.validate();
    }
}
