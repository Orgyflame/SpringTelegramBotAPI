package com.orgyflame.springtelegrambotapi.api.object.polls;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import lombok.*;

/**
 * @author Ruben Bermudez
 * @version 4.2
 * <p>
 * This object contains information about one answer option in a poll.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PollOption implements BotApiObject {
    private static final String TEXT_FIELD = "text";
    private static final String VOTERCOUNT_FIELD = "voter_count";

    @JsonProperty(TEXT_FIELD)
    private String text; ///< Option text, 1-100 characters
    @JsonProperty(VOTERCOUNT_FIELD)
    private Integer voterCount; ///< Number of users that voted for this option
}
