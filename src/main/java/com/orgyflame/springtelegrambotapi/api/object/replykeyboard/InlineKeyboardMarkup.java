package com.orgyflame.springtelegrambotapi.api.object.replykeyboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orgyflame.springtelegrambotapi.api.object.replykeyboard.buttons.InlineKeyboardButton;
import com.orgyflame.springtelegrambotapi.api.object.replykeyboard.buttons.KeyboardButton;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Ruben Bermudez
 * @version 1.0
 * This object represents an inline keyboard that appears right next to the message it
 * belongs to.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InlineKeyboardMarkup implements ReplyKeyboard {

    private static final String KEYBOARD_FIELD = "inline_keyboard";

    @JsonProperty(KEYBOARD_FIELD)
    @NonNull
    private List<List<InlineKeyboardButton>> keyboard; ///< Array of button rows, each represented by an Array of Strings

    public static InlineKeyboardMarkupBuilder builder() {
        return new InlineKeyboardMarkupBuilder();
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (keyboard == null) {
            throw new TelegramApiValidationException("Keyboard parameter can't be null", this);
        }
        for (List<InlineKeyboardButton> inlineKeyboardButtons : keyboard) {
            for (InlineKeyboardButton inlineKeyboardButton : inlineKeyboardButtons) {
                inlineKeyboardButton.validate();
            }
        }
    }

    public static class InlineKeyboardMarkupBuilder {
        private List<List<InlineKeyboardButton>> keyboard;
        private List<InlineKeyboardButton> keyboardRow;

        InlineKeyboardMarkupBuilder() {
            this.keyboard = new ArrayList<>();
            this.keyboardRow = new ArrayList<>();
        }

        /**
         * Adds a new row to the keyboard
         */
        public InlineKeyboardMarkupBuilder row(){
            if(!keyboardRow.isEmpty()) keyboard.add(keyboardRow);
            keyboardRow = new ArrayList<>();
            return this;
        }

        public InlineKeyboardMarkupBuilder button(InlineKeyboardButton inlineKeyboardButton){
            keyboardRow.add(inlineKeyboardButton);
            return this;
        }

        public InlineKeyboardMarkup build() {
            row();

            List<List<InlineKeyboardButton>> keyboard;
            switch (this.keyboard == null ? 0 : this.keyboard.size()) {
                case 0:
                    keyboard = java.util.Collections.emptyList();
                    break;
                case 1:
                    keyboard = java.util.Collections.singletonList(this.keyboard.get(0));
                    break;
                default:
                    keyboard = new ArrayList<>();

                    for (List<InlineKeyboardButton> keyboardRow : this.keyboard) {
                        keyboard.add(new ArrayList<>(keyboardRow));
                    }
            }

            return new InlineKeyboardMarkup(keyboard);
        }

        public String toString() {
            return "InlineKeyboardMarkup.InlineKeyboardMarkupBuilder(keyboard=" + this.keyboard + ")";
        }
    }
}
