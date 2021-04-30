package com.orgyflame.springtelegrambotapi.api.object.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import lombok.*;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * <p>
 * This object contains basic information about a successful payment.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SuccessfulPayment implements BotApiObject {
    private static final String CURRENCY_FIELD = "currency";
    private static final String TOTAL_AMOUNT_FIELD = "total_amount";
    private static final String INVOICE_PAYLOAD_FIELD = "invoice_payload";
    private static final String SHIPPING_OPTION_ID_FIELD = "shipping_option_id";
    private static final String ORDER_INFO_FIELD = "order_info";
    private static final String TELEGRAM_PAYMENT_CHARGE_ID_FIELD = "telegram_payment_charge_id";
    private static final String PROVIDER_PAYMENT_CHARGE_ID_FIELD = "provider_payment_charge_id";

    @JsonProperty(CURRENCY_FIELD)
    private String currency; ///< Three-letter ISO 4217 currency code
    /**
     * Total price in the smallest units of the currency (integer, not float/double).
     * For example, for a price of US$ 1.45 pass amount = 145.
     */
    @JsonProperty(TOTAL_AMOUNT_FIELD)
    private Integer totalAmount;
    @JsonProperty(INVOICE_PAYLOAD_FIELD)
    private String invoicePayload; ///< Bot specified invoice payload
    @JsonProperty(SHIPPING_OPTION_ID_FIELD)
    private String shippingOptionId; ///< Optional. Identifier of a chosen by user shipping option
    @JsonProperty(ORDER_INFO_FIELD)
    private OrderInfo orderInfo; ///< Optional. Order info provided by the user
    @JsonProperty(TELEGRAM_PAYMENT_CHARGE_ID_FIELD)
    private String telegramPaymentChargeId; ///< Telegram payment identifier
    @JsonProperty(PROVIDER_PAYMENT_CHARGE_ID_FIELD)
    private String providerPaymentChargeId; ///< Provider payment identifier
}
