package com.orgyflame.springtelegrambotapi.bot;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.PostMapping;

@ConfigurationProperties(prefix = "telegram.bot")
@Data
public class TelegramBotProperties {
    private String token;
    private String username;
    private String hostUrl;
}
