package com.orgyflame.springtelegrambotapi.api.object.replykeyboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.orgyflame.springtelegrambotapi.api.object.replykeyboard.buttons.KeyboardButton;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;
import lombok.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * This object represents a custom keyboard with reply options.
 */
@JsonDeserialize
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ReplyKeyboardMarkup implements ReplyKeyboard {

    private static final String KEYBOARD_FIELD = "keyboard";
    private static final String RESIZEKEYBOARD_FIELD = "resize_keyboard";
    private static final String ONETIMEKEYBOARD_FIELD = "one_time_keyboard";
    private static final String SELECTIVE_FIELD = "selective";

    @JsonProperty(KEYBOARD_FIELD)
    @NonNull
    @Setter
    private List<List<KeyboardButton> > keyboard; ///< Array of buttons
    @JsonProperty(RESIZEKEYBOARD_FIELD)
    private Boolean resizeKeyboard; ///< Optional. Requests clients to resize the keyboard vertically for optimal fit (e.g., make the keyboard smaller if there are just two rows of buttons). Defaults to false.
    @JsonProperty(ONETIMEKEYBOARD_FIELD)
    private Boolean oneTimeKeyboard; ///< Optional. Requests clients to hide the keyboard as soon as it's been used. Defaults to false.
    /**
     * Optional. Use this parameter if you want to show the keyboard to specific users only.
     * Targets:
     * 1) users that are @mentioned in the text of the Message object;
     * 2) if the bot's message is a reply (has reply_to_message_id), sender of the original message.
     */
    @JsonProperty(SELECTIVE_FIELD)
    private Boolean selective;

    public static ReplyKeyboardMarkupBuilder builder() {
        return new ReplyKeyboardMarkupBuilder();
    }

    @Override
    public void validate() throws TelegramApiValidationException {
        if (keyboard == null) {
            throw new TelegramApiValidationException("Keyboard parameter can't be null", this);
        }
        for (List<KeyboardButton> keyboardRow: keyboard) {
            for(KeyboardButton keyboardButton: keyboardRow){
                keyboardButton.validate();
            }
        }
    }


    public static class ReplyKeyboardMarkupBuilder {
        private List<List<KeyboardButton>> keyboard;
        private List<KeyboardButton> keyboardRow;
        private Boolean resizeKeyboard;
        private Boolean oneTimeKeyboard;
        private Boolean selective;

        ReplyKeyboardMarkupBuilder() {
            this.keyboard = new ArrayList<>();
            this.keyboardRow = new ArrayList<>();
        }

        /**
         * Adds a new row to the keyboard
         */
        public ReplyKeyboardMarkupBuilder row(){
            if(!keyboardRow.isEmpty()) keyboard.add(keyboardRow);
            keyboardRow = new ArrayList<>();
            return this;
        }

        public ReplyKeyboardMarkupBuilder button(KeyboardButton keyboardButton){
            keyboardRow.add(keyboardButton);
            return this;
        }


        public ReplyKeyboardMarkupBuilder resizeKeyboard(Boolean resizeKeyboard) {
            this.resizeKeyboard = resizeKeyboard;
            return this;
        }

        public ReplyKeyboardMarkupBuilder oneTimeKeyboard(Boolean oneTimeKeyboard) {
            this.oneTimeKeyboard = oneTimeKeyboard;
            return this;
        }

        public ReplyKeyboardMarkupBuilder selective(Boolean selective) {
            this.selective = selective;
            return this;
        }

        public ReplyKeyboardMarkup build() {
            row();

            List<List<KeyboardButton>> keyboard;
            switch (this.keyboard == null ? 0 : this.keyboard.size()) {
                case 0:
                    keyboard = java.util.Collections.emptyList();
                    break;
                case 1:
                    keyboard = java.util.Collections.singletonList(this.keyboard.get(0));
                    break;
                default:
                    keyboard = new ArrayList<>();

                    for (List<KeyboardButton> keyboardRow : this.keyboard) {
                        keyboard.add(new ArrayList<>(keyboardRow));
                    }
            }

            return new ReplyKeyboardMarkup(keyboard, resizeKeyboard, oneTimeKeyboard, selective);
        }

        public String toString() {
            return "ReplyKeyboardMarkup.ReplyKeyboardMarkupBuilder(keyboard=" + this.keyboard + ", resizeKeyboard=" + this.resizeKeyboard + ", oneTimeKeyboard=" + this.oneTimeKeyboard + ", selective=" + this.selective + ")";
        }
    }
}
