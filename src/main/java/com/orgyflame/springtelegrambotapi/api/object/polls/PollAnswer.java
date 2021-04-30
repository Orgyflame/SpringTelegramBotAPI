package com.orgyflame.springtelegrambotapi.api.object.polls;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import com.orgyflame.springtelegrambotapi.api.object.User;
import lombok.*;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 4.7
 * <p>
 * This object represents an answer of a user in a non-anonymous poll.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PollAnswer implements BotApiObject {
    private static final String POLLID_FIELD = "poll_id";
    private static final String USER_FIELD = "user";
    private static final String OPTIONIDS_FIELD = "option_ids";

    @JsonProperty(POLLID_FIELD)
    private String pollId; ///< Unique poll identifier
    @JsonProperty(USER_FIELD)
    private User user; ///< The user, who changed the answer to the poll
    @JsonProperty(OPTIONIDS_FIELD)
    private List<Integer> optionIds; ///< 0-based identifiers of answer options, chosen by the user. May be empty if the user retracted their vote.
}
