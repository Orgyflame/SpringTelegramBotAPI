package com.orgyflame.springtelegrambotapi.api.method;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.api.object.payments.ShippingOption;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiRequestException;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.io.IOException;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * <p>
 * If you sent an invoice requesting a shipping address and the parameter flexible was specified,
 * the Bot API will send an Update with a shipping_query field to the bot.
 * Use this method to reply to shipping queries.
 * <p>
 * On success, True is returned
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerShippingQuery extends BotApiMethod<Boolean> {
    public static final String PATH = "answerShippingQuery";

    private static final String SHIPPING_QUERY_ID_FIELD = "shipping_query_id";
    private static final String OK_FIELD = "ok";
    private static final String SHIPPING_OPTIONS_FIELD = "shipping_options";
    private static final String ERROR_MESSAGE_FIELD = "error_message";

    @JsonProperty(SHIPPING_QUERY_ID_FIELD)
    @NonNull
    private String shippingQueryId; ///< Unique identifier for the query to be answered
    @JsonProperty(OK_FIELD)
    @NonNull
    private Boolean ok; ///< Specify True if delivery to the specified address is possible and False if there are any problems
    @JsonProperty(SHIPPING_OPTIONS_FIELD)
    private List<ShippingOption> shippingOptions; ///< Optional. Required if ok is True. A JSON-serialized array of available shipping options.
    @JsonProperty(ERROR_MESSAGE_FIELD)
    private String errorMessage; ///< Optional. Required if ok is False. Error message in human readable form that explains why it is impossible to complete the order (e.g. "Sorry, delivery to your desired address is unavailable').

    @Override
    public void validate() throws TelegramApiValidationException {
        if (shippingQueryId == null || shippingQueryId.isEmpty()) {
            throw new TelegramApiValidationException("ShippingQueryId can't be empty", this);
        }
        if (ok == null) {
            throw new TelegramApiValidationException("Ok can't be null", this);
        }
        if (ok) {
            if (shippingOptions == null || shippingOptions.isEmpty()) {
                throw new TelegramApiValidationException("ShippingOptions array can't be empty if ok", this);
            }
            for (ShippingOption shippingOption : shippingOptions) {
                shippingOption.validate();
            }
        } else {
            if (errorMessage == null || errorMessage.isEmpty()) {
                throw new TelegramApiValidationException("ErrorMessage can't be empty if not ok", this);
            }
        }
    }

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
                throw new TelegramApiRequestException("Error answering shipping query", result);
            }
        } catch (IOException e) {
            throw new TelegramApiRequestException("Unable to deserialize response", e);
        }
    }
}
