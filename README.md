# Telegram Bot API Spring Boot starter

A simple to use library to create Telegram Bots using Spring
 
## Getting started
Let's build a simple bot

### Add dependencies
Just import add the library to your project with one of these options:

* Using Maven:
    ```xml
    <dependency>
    <groupId>io.github.orgyflame</groupId>
    <artifactId>telegram-bot-api-spring-boot-starter</artifactId>
    <version>5.2.0</version>
   </dependency>
   ```
* Using Gradle:
```groovy
implementation 'io.github.orgyflame:telegram-bot-api-spring-boot-starter:5.2.0'
```

### Add properties
Add these properties to your `application.properties` file:

1. Your host url with https 
(if you don`t have this, you can use
[ngrok](https://ngrok.com/) 
for local dev):
```properties
telegram.bot.host-url=https://example.com
```
2. Your bot token, that 
[BotFather](https://t.me/botfather)
gave you:
```properties
telegram.bot.token=1234567890:Aa0Bb1Cc2Dd3Ee4
```
3. Your callback mapping
(it uses for webhooks):
```properties
telegram.bot.callback-mapping=/callback/1234567890:Aa0Bb1Cc2Dd3Ee4
```

### Create a hello command

```java
package com.orgyflame.demotelegrambot.controller;

import com.orgyflame.springtelegrambotapi.api.method.BotApiMethod;
import com.orgyflame.springtelegrambotapi.api.method.send.SendMessage;
import com.orgyflame.springtelegrambotapi.api.object.Chat;
import com.orgyflame.springtelegrambotapi.api.object.User;
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import com.orgyflame.springtelegrambotapi.bot.mapping.BotController;
import com.orgyflame.springtelegrambotapi.bot.mapping.BotMapping;
import com.orgyflame.springtelegrambotapi.bot.mapping.parameter.ChatParam;
import com.orgyflame.springtelegrambotapi.bot.mapping.parameter.FromParam;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;

@BotController
public class DemoController {
  private final TelegramApiService telegramApiService;

  public DemoController(TelegramApiService telegramApiService) {
    this.telegramApiService = telegramApiService;

  }

  private void send(BotApiMethod botApiMethod) {
    try {
      telegramApiService.sendApiMethod(botApiMethod).subscribe();
    } catch (TelegramApiValidationException e) {
      e.printStackTrace();
    }
  }

  @BotMapping(value = "/hello", description = "Greets the user")
  public void hello(@FromParam User user, @ChatParam Chat chat) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(String.valueOf(chat.getId()));
    sendMessage.setText("Hello," + user.getFirstName());
    send(sendMessage);
  }
}
```

All bot commands are handled by controllers.
These components are identified 
by `@BotController` annotation. 

The `@BotMapping` annotation ensures that
all messages that start with `/hello` command
are mapped to `hello()` method.

The `@FromParam` annotation gets the user who
send the message. The `@ChatParam` annotation gets
the chat where the user sent the message.

The `SendMessage` object is a Telegram Bot API
request.

The `TelegramApiService` is a spring component
that provides the ability to send Telegram Bot 
API requests.

### Add inline keyboard command

```java
package com.orgyflame.demotelegrambot.controller;

import com.orgyflame.springtelegrambotapi.api.method.AnswerCallbackQuery;
import com.orgyflame.springtelegrambotapi.api.method.BotApiMethod;
import com.orgyflame.springtelegrambotapi.api.method.send.SendMessage;
import com.orgyflame.springtelegrambotapi.api.object.Chat;
import com.orgyflame.springtelegrambotapi.api.object.Update;
import com.orgyflame.springtelegrambotapi.api.object.User;
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import com.orgyflame.springtelegrambotapi.bot.container.BotCallbackQueryContainer;
import com.orgyflame.springtelegrambotapi.bot.inline.menu.InlineMenu;
import com.orgyflame.springtelegrambotapi.bot.inline.menu.InlineMenuButton;
import com.orgyflame.springtelegrambotapi.bot.mapping.BotController;
import com.orgyflame.springtelegrambotapi.bot.mapping.BotMapping;
import com.orgyflame.springtelegrambotapi.bot.mapping.parameter.ChatParam;
import com.orgyflame.springtelegrambotapi.bot.mapping.parameter.FromParam;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;

@BotController
public class DemoController {
  private final TelegramApiService telegramApiService;
  private final BotCallbackQueryContainer botCallbackQueryContainer;

  public DemoController(TelegramApiService telegramApiService, BotCallbackQueryContainer botCallbackQueryContainer) {
    this.telegramApiService = telegramApiService;

    this.botCallbackQueryContainer = botCallbackQueryContainer;
  }

  private void send(BotApiMethod botApiMethod) {
    try {
      telegramApiService.sendApiMethod(botApiMethod).subscribe();
    } catch (TelegramApiValidationException e) {
      e.printStackTrace();
    }
  }

  @BotMapping(value = "/hello", description = "Greets the user")
  public void hello(@FromParam User user, @ChatParam Chat chat) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(String.valueOf(chat.getId()));
    sendMessage.setText("Hello," + user.getFirstName());
    send(sendMessage);
  }

  @BotMapping(value = "/inline", description = "Show inline keyboard")
  public void inlineKeyboard(@ChatParam Chat chat) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(String.valueOf(chat.getId()));
    sendMessage.setText("keyboard:");

    InlineMenu inlineMenu = InlineMenu.builder()
            .button(
                    InlineMenuButton.builder()
                            .text("button 1")
                            .onQueryCallback(this::button1Callback)
                            .build()
            )
            .row()
            .button(
                    InlineMenuButton.builder()
                            .text("button 2")
                            .onQueryCallback(this::button2Callback)
                            .build()
            )
            .build(botCallbackQueryContainer);

    sendMessage.setReplyMarkup(inlineMenu);

    send(sendMessage);
  }

  public AnswerCallbackQuery button1Callback(Update update) {
    AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
    answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
    answerCallbackQuery.setText("Clicked on button 1");
    return answerCallbackQuery;
  }

  private AnswerCallbackQuery button2Callback(Update update) {
    AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
    answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
    answerCallbackQuery.setText("Clicked on button 2");
    return answerCallbackQuery;
  }

}
```

The `InlineMenuButtonBuilder.text()` method
adds a text for the inline button. The 
`InlineMenuButtonBuilder.onQueryCallback()`
method adds a callback method for the button.

The `InlineMenuBuilder.button()` method 
adds new inline button for the current row.
The `InlineMenuBuilder.row()` method
adds new row for the inline keyboard.

The `BotCallbackQueryContainer` is a
spring component that provides the ability
to build inline keyboard with callbacks.

### Add the default mapping

```java
package com.orgyflame.demotelegrambot.controller;

import com.orgyflame.springtelegrambotapi.api.method.AnswerCallbackQuery;
import com.orgyflame.springtelegrambotapi.api.method.BotApiMethod;
import com.orgyflame.springtelegrambotapi.api.method.send.SendMessage;
import com.orgyflame.springtelegrambotapi.api.object.Chat;
import com.orgyflame.springtelegrambotapi.api.object.Update;
import com.orgyflame.springtelegrambotapi.api.object.User;
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import com.orgyflame.springtelegrambotapi.bot.container.BotCallbackQueryContainer;
import com.orgyflame.springtelegrambotapi.bot.inline.menu.InlineMenu;
import com.orgyflame.springtelegrambotapi.bot.inline.menu.InlineMenuButton;
import com.orgyflame.springtelegrambotapi.bot.mapping.BotController;
import com.orgyflame.springtelegrambotapi.bot.mapping.BotMapping;
import com.orgyflame.springtelegrambotapi.bot.mapping.parameter.ChatParam;
import com.orgyflame.springtelegrambotapi.bot.mapping.parameter.FromParam;
import com.orgyflame.springtelegrambotapi.exceptions.TelegramApiValidationException;

@BotController
public class DemoController {
  private final TelegramApiService telegramApiService;
  private final BotCallbackQueryContainer botCallbackQueryContainer;

  public DemoController(TelegramApiService telegramApiService, BotCallbackQueryContainer botCallbackQueryContainer) {
    this.telegramApiService = telegramApiService;

    this.botCallbackQueryContainer = botCallbackQueryContainer;
  }

  private void send(BotApiMethod botApiMethod) {
    try {
      telegramApiService.sendApiMethod(botApiMethod).subscribe();
    } catch (TelegramApiValidationException e) {
      e.printStackTrace();
    }
  }

  @BotMapping(value = "/hello", description = "Greets the user")
  public void hello(@FromParam User user, @ChatParam Chat chat) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(String.valueOf(chat.getId()));
    sendMessage.setText("Hello," + user.getFirstName());
    send(sendMessage);
  }

  @BotMapping(value = "/inline", description = "Show inline keyboard")
  public void inlineKeyboard(@ChatParam Chat chat) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(String.valueOf(chat.getId()));
    sendMessage.setText("keyboard:");

    InlineMenu inlineMenu = InlineMenu.builder()
            .button(
                    InlineMenuButton.builder()
                            .text("button 1")
                            .onQueryCallback(this::button1Callback)
                            .build()
            )
            .row()
            .button(
                    InlineMenuButton.builder()
                            .text("button 2")
                            .onQueryCallback(this::button2Callback)
                            .build()
            )
            .build(botCallbackQueryContainer);

    sendMessage.setReplyMarkup(inlineMenu);

    send(sendMessage);
  }

  public AnswerCallbackQuery button1Callback(Update update) {
    AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
    answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
    answerCallbackQuery.setText("Clicked on button 1");
    return answerCallbackQuery;
  }

  private AnswerCallbackQuery button2Callback(Update update) {
    AnswerCallbackQuery answerCallbackQuery = new AnswerCallbackQuery();
    answerCallbackQuery.setCallbackQueryId(update.getCallbackQuery().getId());
    answerCallbackQuery.setText("Clicked on button 2");
    return answerCallbackQuery;
  }

  @BotMapping
  public void defaultMapping(@ChatParam Chat chat) {
    SendMessage sendMessage = new SendMessage();
    sendMessage.setChatId(String.valueOf(chat.getId()));
    sendMessage.setText("default");
    send(sendMessage);
  }
}
```

The `@BotMapping` annotation with empty 
value ensures that
all messages with unsupported commands
are mapped to `defaultMapping()` method.

## Documentation
* [Send api requests](#send-telegram-bot-api-requests)
* [Available methods](#available-methods)
* [Available types](#available-types)
* [Available param annotations](#available-param-annotations)

### Send Telegram Bot API requests
To send requests you must inject
`TelegramApiService`
to your spring component, like this:

```java
import com.orgyflame.springtelegrambotapi.api.object.ApiResponse;
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class MySpringComponent {
  private final TelegramApiService telegramApiService;

  public MySpringComponent(TelegramApiService telegramApiService) {
    this.telegramApiService = telegramApiService;
  }
}
```

or this:

```java
import com.orgyflame.springtelegrambotapi.api.service.TelegramApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MySpringComponent {
  @Autowired
  private TelegramApiService telegramApiService;
}
```

Requests are sent like this:
```
Mono<ApiResponse> response = telegramApiService.sendApiMethod(someBotApiMethod);
```

### Available methods 
All Bot API methods are in
[com.orgyflame.springtelegrambotapi.api.method][1] package.

### Available types
All Bot API types are in
[com.orgyflame.springtelegrambotapi.api.object][2] package.

### Available param annotations
All param annotations for
methods marked with an `@BotMapping` 
annotation are in:
[com.orgyflame.springtelegrambotapi.bot.mapping.parameter][3] package

[1]: src/main/java/com/orgyflame/springtelegrambotapi/api/method
[2]: src/main/java/com/orgyflame/springtelegrambotapi/api/object
[3]: src/main/java/com/orgyflame/springtelegrambotapi/bot/mapping/parameter