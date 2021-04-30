package com.orgyflame.springtelegrambotapi.api.object.payments;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import com.orgyflame.springtelegrambotapi.api.object.User;
import lombok.*;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * <p>
 * This object contains information about incoming shipping query.
 */
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ShippingQuery implements BotApiObject {
    private static final String ID_FIELD = "id";
    private static final String FROM_FIELD = "from";
    private static final String INVOICE_PAYLOAD_FIELD = "invoice_payload";
    private static final String SHIPPING_ADDRESS_FIELD = "shipping_address";

    @JsonProperty(ID_FIELD)
    private String id; ///< Unique query identifier
    @JsonProperty(FROM_FIELD)
    private User from; ///< User who sent the query
    @JsonProperty(INVOICE_PAYLOAD_FIELD)
    private String invoicePayload; ///< Bot specified invoice payload
    @JsonProperty(SHIPPING_ADDRESS_FIELD)
    private ShippingAddress shippingAddress; ///< User specified shipping address
}
