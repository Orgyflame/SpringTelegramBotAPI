package com.orgyflame.springtelegrambotapi.api.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represent a user's profile pictures.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserProfilePhotos implements BotApiObject {

    private static final String TOTALCOUNT_FIELD = "total_count";
    private static final String PHOTOS_FIELD = "photos";

    @JsonProperty(TOTALCOUNT_FIELD)
    private Integer totalCount; ///< Total number of profile pictures the target user has
    @JsonProperty(PHOTOS_FIELD)
    private List<List<PhotoSize>> photos; ///< Requested profile pictures (in up to 4 sizes each)
}
