package com.orgyflame.springtelegrambotapi.api.object.stickers;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.orgyflame.springtelegrambotapi.api.object.BotApiObject;
import com.orgyflame.springtelegrambotapi.api.Validable;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

/**
 * @author Ruben Bermudez
 * @version 3.2
 * This object describes the position on faces where a mask should be placed by default.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaskPosition implements Validable, BotApiObject {
    private static final String POINT_FIELD = "point";
    private static final String XSHIFT_FIELD = "x_shift";
    private static final String YSHIFT_FIELD = "y_shift";
    private static final String SCALE_FIELD = "scale";

    @JsonProperty(POINT_FIELD)
    @NonNull
    private String point; ///< The part of the face relative to which the mask should be placed. One of “forehead”, “eyes”, “mouth”, or “chin”.
    @JsonProperty(XSHIFT_FIELD)
    @NonNull
    private Float xShift; ///< Shift by X-axis measured in widths of the mask scaled to the face size, from left to right. For example, choosing -1.0 will place mask just to the left of the default mask position.
    @JsonProperty(YSHIFT_FIELD)
    @NonNull
    private Float yShift; ///< Shift by Y-axis measured in heights of the mask scaled to the face size, from top to bottom. For example, 1.0 will place the mask just below the default mask position.
    @JsonProperty(SCALE_FIELD)
    @NonNull
    private Float scale; ///< Mask scaling coefficient. For example, 2.0 means double size.

    @Override
    public void validate() throws TelegramApiValidationException {
        if (point == null || point.isEmpty()) {
            throw new TelegramApiValidationException("point can't be empty", this);
        }
        if (xShift == null) {
            throw new TelegramApiValidationException("xShift can't be empty", this);
        }
        if (yShift == null) {
            throw new TelegramApiValidationException("yShift can't be empty", this);
        }
        if (scale == null) {
            throw new TelegramApiValidationException("scale can't be empty", this);
        }
    }
}
