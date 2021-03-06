package com.orgyflame.springtelegrambotapi.api.method;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiRequestException;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.io.IOException;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * Use this method to send answers to callback queries sent from inline keyboards. The answer
 * will be displayed to the user as a notification at the top of the chat screen or as an alert. On
 * success, True is returned.
 * @apiNote Alternatively, the user can be redirected to the specified URL. For this option to work,
 * you must enable /setcustomurls for your bot via BotFather and accept the terms.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerCallbackQuery extends BotApiMethod<Boolean> {
    public static final String PATH = "answercallbackquery";

    private static final String CALLBACKQUERYID_FIELD = "callback_query_id";
    private static final String TEXT_FIELD = "text";
    private static final String SHOWALERT_FIELD = "show_alert";
    private static final String URL_FIELD = "url";
    private static final String CACHETIME_FIELD = "cache_time";

    @JsonProperty(CALLBACKQUERYID_FIELD)
    @NonNull
    private String callbackQueryId; ///< Unique identifier for the query to be answered
    @JsonProperty(TEXT_FIELD)
    private String text; ///< Optional	Text of the notification. If not specified, nothing will be shown to the user, 0-200 characters
    @JsonProperty(SHOWALERT_FIELD)
    private Boolean showAlert; ///< Optional. If true, an alert will be shown by the client instead of a notification at the top of the chat screen. Defaults to false.
    /**
     * Optional. URL that will be opened by the user's client.
     * If you have created a Game and accepted the conditions via @Botfather,
     * specify the URL that opens your game. Otherwise you may use links
     * like telegram.me/your_bot?start=XXXX that open your bot with a parameter.
     */
    @JsonProperty(URL_FIELD)
    private String url;
    /**
     * Optional	The maximum amount of time in seconds that the result of the callback query
     * may be cached client-side.
     *
     * @apiNote Telegram apps will support caching starting in version 3.14. Defaults to 0.
     */
    @JsonProperty(CACHETIME_FIELD)
    private Integer cacheTime;

    @Override
    public String getMethod() {
        return PATH;
    }

    @Override
    public Boolean deserializeResponse(String answer) throws TelegramApiRequestException {
        try {
            ApiResponse<Boolean> result = OBJECT_MAPPER.readValue(answer,
                    new TypeReference<ApiResponse<Boolean>>() {
                    });
            if (result.getOk()) {
                return result.getResult();
            } else {
                throw new TelegramApiRequestException("Error answering callback query", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (callbackQueryId == null) {
            throw new TelegramApiValidationException("CallbackQueryId can't be null", this);
        }
    }
}
