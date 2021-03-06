package com.orgyflame.springtelegrambotapi.api.method;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.api.object.UserProfilePhotos;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiRequestException;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to get a list of profile pictures for a user. Returns a UserProfilePhotos object.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserProfilePhotos extends BotApiMethod<UserProfilePhotos> {
    public static final String PATH = "getuserprofilephotos";

    private static final String USERID_FIELD = "user_id";
    private static final String OFFSET_FIELD = "offset";
    private static final String LIMIT_FIELD = "limit";

    @JsonProperty(USERID_FIELD)
    @NonNull
    private Long userId; ///< Unique identifier of the target user
    /**
     * Optional. Sequential number of the first photo to be returned. By default, all photos are returned.
     */
    @JsonProperty(OFFSET_FIELD)
    private Integer offset;
    /**
     * Optional. Limits the number of photos to be retrieved. Values between 1—100 are accepted. Defaults to 100.
     */
    @JsonProperty(LIMIT_FIELD)
    private Integer limit;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public UserProfilePhotos deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<UserProfilePhotos> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<UserProfilePhotos>>() {
                    });
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error getting user profile photos", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (userId == null) {
            throw new TelegramApiValidationException("UserId parameter can't be empty", this);
        }
    }
}
