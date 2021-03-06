package com.orgyflame.springtelegrambotapi.api.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents a video file.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Video implements BotApiObject {

    private static final String FILEID_FIELD = "file_id";
    private static final String FILEUNIQUEID_FIELD = "file_unique_id";
    private static final String WIDTH_FIELD = "width";
    private static final String HEIGHT_FIELD = "height";
    private static final String DURATION_FIELD = "duration";
    private static final String THUMB_FIELD = "thumb";
    private static final String MIMETYPE_FIELD = "mime_type";
    private static final String FILESIZE_FIELD = "file_size";
    private static final String FILENAME_FIELD = "file_name";

    @JsonProperty(FILEID_FIELD)
    private String fileId; ///< Identifier for this file, which can be used to download or reuse the file
    /**
     * Unique identifier for this file, which is supposed to be the same over time and for different bots.
     * Can't be used to download or reuse the file.
     */
    @JsonProperty(FILEUNIQUEID_FIELD)
    private String fileUniqueId;
    @JsonProperty(WIDTH_FIELD)
    private Integer width; ///< Video width as defined by sender
    @JsonProperty(HEIGHT_FIELD)
    private Integer height; ///< Video height as defined by sender
    @JsonProperty(DURATION_FIELD)
    private Integer duration; ///< Duration of the video in seconds as defined by sender
    @JsonProperty(THUMB_FIELD)
    private PhotoSize thumb; ///< Video thumbnail
    @JsonProperty(MIMETYPE_FIELD)
    private String mimeType; ///< Optional. Mime type of a file as defined by sender
    @JsonProperty(FILESIZE_FIELD)
    private Integer fileSize; ///< Optional. File size
    @JsonProperty(FILENAME_FIELD)
    private String fileName; ///< Optional. Original filename as defined by sender
}
