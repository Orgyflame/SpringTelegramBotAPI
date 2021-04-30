package com.orgyflame.springtelegrambotapi.api.object.inlinequery;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import com.orgyflame.springtelegrambotapi.api.object.Location;
import com.orgyflame.springtelegrambotapi.api.object.User;
import lombok.*;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents an incoming inline query. When the user sends an empty query, your
 * bot could return some default or trending results.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InlineQuery implements BotApiObject {
    private static final String ID_FIELD = "id";
    private static final String FROM_FIELD = "from";
    private static final String LOCATION_FIELD = "location";
    private static final String QUERY_FIELD = "query";
    private static final String OFFSET_FIELD = "offset";
    private static final String CHATTYPE_FIELD = "chat_type";

    @JsonProperty(ID_FIELD)
    @NonNull
    private String id; ///< Unique identifier for this query
    @JsonProperty(FROM_FIELD)
    @NonNull
    private User from; ///< Sender
    @JsonProperty(LOCATION_FIELD)
    private Location location; ///< Optional. Sender location, only for bots that request user location
    @JsonProperty(QUERY_FIELD)
    @NonNull
    private String query; ///< Text of the query
    @JsonProperty(OFFSET_FIELD)
    @NonNull
    private String offset; ///< Offset of the results to be returned, can be controlled by the bot
    /**
     * Optional. Type of the chat, from which the inline query was sent.
     * Can be either “sender” for a private chat with the inline query sender, “private”, “group”, “supergroup”, or “channel”.
     * The chat type should be always known for requests sent from official clients and most third-party clients,
     * unless the request was sent from a secret chat
     */
    @JsonProperty(CHATTYPE_FIELD)
    private String chatType;
}

