package com.orgyflame.springtelegrambotapi.api.object.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orgyflame.springtelegrambotapi.api.object.InputFile;
import com.orgyflame.springtelegrambotapi.api.object.MessageEntity;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 * <p>
 * Represents a general file to be sent.
 */
@SuppressWarnings("unused")
@JsonDeserialize
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString
public class InputMediaDocument extends InputMedia {
    public static final String THUMB_FIELD = "thumb";
    public static final String DISABLECONTENTTYPEDETECTION_FIELD = "disable_content_type_detection";
    private static final String TYPE = "document";
    /**
     * Thumbnail of the file sent. The thumbnail should be in JPEG format and less than 200 kB in size.
     * A thumbnail’s width and height should not exceed 320.
     * Ignored if the file is not uploaded using multipart/form-data.
     * Thumbnails can’t be reused and can be only uploaded as a new file, so you can pass “attach://<file_attach_name>”
     * if the thumbnail was uploaded using multipart/form-data under <file_attach_name>.
     */
    private InputFile thumb;
    /**
     * Optional.
     * Disables automatic server-side content type detection for files uploaded using multipart/form-data.
     * Always true, if the document is sent as part of an album.
     */
    @JsonProperty(DISABLECONTENTTYPEDETECTION_FIELD)
    private Boolean disableContentTypeDetection;

    public InputMediaDocument() {
        super();
    }

    public InputMediaDocument(@NonNull String media) {
        super(media);
    }

    @Builder
    public InputMediaDocument(@NonNull String media, String caption, String parseMode, List<MessageEntity> entities, boolean isNewMedia, String mediaName, File newMediaFile, InputStream newMediaStream, InputFile thumb, Boolean disableContentTypeDetection) {
        super(media, caption, parseMode, entities, isNewMedia, mediaName, newMediaFile, newMediaStream);
        this.thumb = thumb;
        this.disableContentTypeDetection = disableContentTypeDetection;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        super.validate();
    }
}
