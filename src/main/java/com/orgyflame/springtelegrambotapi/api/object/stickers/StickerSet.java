package com.orgyflame.springtelegrambotapi.api.object.stickers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import com.orgyflame.springtelegrambotapi.api.object.PhotoSize;
import lombok.*;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents a sticker set.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class StickerSet implements BotApiObject {
    private static final String NAME_FIELD = "name";
    private static final String TITLE_FIELD = "title";
    private static final String CONTAINSMASKS_FIELD = "contains_masks";
    private static final String STICKERS_FIELD = "stickers";
    private static final String ISANIMATED_FIELD = "is_animated";
    private static final String THUMB_FIELD = "thumb";

    @JsonProperty(NAME_FIELD)
    private String name; ///< Sticker set name
    @JsonProperty(TITLE_FIELD)
    private String title; ///< Sticker set title
    @JsonProperty(CONTAINSMASKS_FIELD)
    private Boolean containsMasks; ///< True, if the sticker set contains animated stickers
    @JsonProperty(STICKERS_FIELD)
    private List<Sticker> stickers; ///< True, if the sticker set contains masks
    @JsonProperty(ISANIMATED_FIELD)
    private Boolean isAnimated; ///< List of all set stickers
    @JsonProperty(THUMB_FIELD)
    private PhotoSize thumb; ///< Optional. Sticker set thumbnail in the .WEBP or .TGS format
}
