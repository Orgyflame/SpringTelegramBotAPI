package com.orgyflame.springtelegrambotapi.api.object.media;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import com.orgyflame.springtelegrambotapi.api.Validable;
import com.orgyflame.springtelegrambotapi.api.object.MessageEntity;
import com.orgyflame.springtelegrambotapi.api.object.media.serialization.InputMediaDeserializer;
import com.orgyflame.springtelegrambotapi.api.object.media.serialization.InputMediaSerializer;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 3.5
 */
@SuppressWarnings({"unused"})
@JsonSerialize(using = InputMediaSerializer.class)
@JsonDeserialize(using = InputMediaDeserializer.class)
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public abstract class InputMedia implements Validable, BotApiObject {
    public static final String TYPE_FIELD = "type";
    public static final String MEDIA_FIELD = "media";
    public static final String CAPTION_FIELD = "caption";
    public static final String PARSEMODE_FIELD = "parse_mode";
    public static final String ENTITIES_FIELD = "entities";

    /**
     * File to send. Pass a file_id to send a file that exists on the Telegram servers (recommended),
     * pass an HTTP URL for Telegram to get a file from the Internet, or pass "attach://<file_attach_name>"
     * to upload a new one using multipart/form-data under <file_attach_name> name.
     */
    @JsonProperty(MEDIA_FIELD)
    @NonNull
    private String media;
    @JsonProperty(CAPTION_FIELD)
    private String caption; ///< Optional. Caption of the media to be sent, 0-200 characters
    @JsonProperty(PARSEMODE_FIELD)
    private String parseMode; ///< Optional. Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in the media caption.
    @JsonProperty(ENTITIES_FIELD)
    @Singular
    private List<MessageEntity> entities; ///< Optional. List of special entities that appear in message text, which can be specified instead of parse_mode
    @JsonIgnore
    private boolean isNewMedia; ///< True to upload a new media, false to use a fileId or URL
    @JsonIgnore
    private String mediaName; ///< Name of the media to upload
    @JsonIgnore
    private File newMediaFile; ///< New media file
    @JsonIgnore
    private InputStream newMediaStream; ///< New media stream

    @JsonIgnore
    public boolean isNewMedia() {
        return isNewMedia;
    }

    /**
     * Use this setter to send an existing file (using file_id) or an url.
     *
     * @param media File_id or URL of the file to send
     */
    public void setMedia(String media) {
        this.media = media;
        this.isNewMedia = false;
    }

    /**
     * Use this setter to send new file.
     *
     * @param mediaFile File to send
     */
    public void setMedia(File mediaFile, String fileName) {
        this.newMediaFile = mediaFile;
        this.isNewMedia = true;
        this.mediaName = fileName;
        this.media = "attach://" + fileName;
    }

    /**
     * Use this setter to send new file as stream.
     *
     * @param mediaStream File to send
     */
    public void setMedia(InputStream mediaStream, String fileName) {
        this.newMediaStream = mediaStream;
        this.isNewMedia = true;
        this.mediaName = fileName;
        this.media = "attach://" + fileName;
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (isNewMedia) {
            if (mediaName == null || mediaName.isEmpty()) {
                throw new TelegramApiValidationException("Media name can't be empty", this);
            }
            if (newMediaFile == null && newMediaStream == null) {
                throw new TelegramApiValidationException("Media can't be empty", this);
            }
        } else if (media == null || media.isEmpty()) {
            throw new TelegramApiValidationException("Media can't be empty", this);
        }
        if (parseMode != null && (entities != null && !entities.isEmpty())) {
            throw new TelegramApiValidationException("Parse mode can't be enabled if Entities are provided", this);
        }
    }

    @JsonProperty(TYPE_FIELD)
    public abstract String getType();
}
