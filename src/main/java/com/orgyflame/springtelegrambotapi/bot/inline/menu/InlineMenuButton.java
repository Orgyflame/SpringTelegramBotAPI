package com.orgyflame.springtelegrambotapi.bot.inline.menu;

import lombok.Data;

import java.util.UUID;

@Data
public class InlineMenuButton {
    private String text;
    private String url;
    private CallbackQueryMethod onQueryCallback;
    private final String uuid = UUID.randomUUID().toString();

    InlineMenuButton(String text, String url, CallbackQueryMethod onQueryCallback) {
        this.text = text;
        this.url = url;
        this.onQueryCallback = onQueryCallback;
    }

    public static InlineMenuButtonBuilder builder() {
        return new InlineMenuButtonBuilder();
    }

    public static class InlineMenuButtonBuilder {
        private String text;
        private String url;
        private CallbackQueryMethod onQueryCallback;

        InlineMenuButtonBuilder() {
        }

        public InlineMenuButtonBuilder text(String text) {
            this.text = text;
            return this;
        }

        public InlineMenuButtonBuilder url(String url) {
            this.url = url;
            return this;
        }

        public InlineMenuButtonBuilder onQueryCallback(CallbackQueryMethod onQueryCallback) {
            this.onQueryCallback = onQueryCallback;
            return this;
        }

        public InlineMenuButton build() {
            return new InlineMenuButton(text, url, onQueryCallback);
        }

        public String toString() {
            return "InlineMenuButton.InlineMenuButtonBuilder(text=" + this.text + ", url=" + this.url + ", onQueryCallback=" + this.onQueryCallback + ")";
        }
    }
}
