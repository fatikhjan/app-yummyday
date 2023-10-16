package uz.yummyday.yummyday.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import uz.yummyday.yummyday.controller.BasicController;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Component
@RequiredArgsConstructor
public class TelegramBotYummyDay extends TelegramLongPollingBot {


    private final Configs configs;

    private final BasicController basicController;


    @Override
    public String getBotUsername() {
        return configs.getUsername();
    }

    @Override
    public String getBotToken() {
        return configs.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {

        Executor executor = Executors.newCachedThreadPool();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (update.hasMessage()) {
                    basicController.getUpdateMessageHandler(update);
                }
                if (update.hasCallbackQuery()) {
                    basicController.getUpdateCallBackQuery(update);
                }
            }
        };
        executor.execute(runnable);
    }

    public void sendMsg(Object obj) {
        try {
            if (obj instanceof SendMessage) {
                execute((SendMessage) obj);
            } else if (obj instanceof DeleteMessage) {
                execute((DeleteMessage) obj);
            } else if (obj instanceof EditMessageText) {
                execute((EditMessageText) obj);
            } else if (obj instanceof SendPhoto) {
                Message message = execute((SendPhoto) obj);

            } else if (obj instanceof SendDocument) {
                execute((SendDocument) obj);
            } else if (obj instanceof SendLocation) {
                execute((SendLocation) obj);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
