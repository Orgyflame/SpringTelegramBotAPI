package com.orgyflame.springtelegrambotapi.api.object.passport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import lombok.*;

import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 4.0.0
 * Contains information about Telegram Passport data shared with the bot by the user.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PassportData implements BotApiObject {
    private static final String DATA_FIELD = "data";
    private static final String CREDENTIALS_FIELD = "credentials";

    @JsonProperty(DATA_FIELD)
    private List<EncryptedPassportElement> data; ///< Array with information about documents and other Telegram Passport data that was shared with the bot
    @JsonProperty(CREDENTIALS_FIELD)
    private EncryptedCredentials credentials; ///< Array with information about documents and other Telegram Passport data shared with the bot.
}
