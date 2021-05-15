package com.orgyflame.springtelegrambotapi.bot.inline.menu;

import com.orgyflame.springtelegrambotapi.bot.container.BotCallbackQueryContainer;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class InlineMenu{

    private List<List<InlineMenuButton>> keyboard;

    public InlineMenu(List<List<InlineMenuButton>> keyboard) {
        this.keyboard = keyboard;
    }

    public static InlineMenuBuilder builder(){
        return new InlineMenuBuilder();
    }

    public static class InlineMenuBuilder {
        private List<List<InlineMenuButton>> keyboard;
        private List<InlineMenuButton> keyboardRow;

        InlineMenuBuilder() {
            this.keyboard = new ArrayList<>();
            this.keyboardRow = new ArrayList<>();
        }

        /**
         * Adds a new row to the keyboard
         */
        public InlineMenuBuilder row(){
            if(!keyboardRow.isEmpty()) keyboard.add(keyboardRow);
            keyboardRow = new ArrayList<>();
            return this;
        }

        public InlineMenuBuilder button(InlineMenuButton inlineMenuButton){
            keyboardRow.add(inlineMenuButton);
            return this;
        }

        public InlineMenu build(BotCallbackQueryContainer botCallbackQueryContainer) {
            row();

            List<List<InlineMenuButton>> keyboard;
            switch (this.keyboard == null ? 0 : this.keyboard.size()) {
                case 0:
                    keyboard = java.util.Collections.emptyList();
                    break;
                case 1:
                    keyboard = java.util.Collections.singletonList(this.keyboard.get(0));
                    break;
                default:
                    keyboard = new ArrayList<>();

                    for (List<InlineMenuButton> keyboardRow : this.keyboard) {
                        keyboard.add(new ArrayList<>(keyboardRow));
                    }
            }

            for (List<InlineMenuButton> keyboardRow : keyboard) {
                for (InlineMenuButton button : keyboardRow) {
                    botCallbackQueryContainer.add(button);
                }
            }

            return new InlineMenu(keyboard);
        }
    }
}
