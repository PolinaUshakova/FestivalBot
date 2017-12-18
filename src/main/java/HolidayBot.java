import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

public class HolidayBot extends TelegramLongPollingBot {
    
    ArrayList<String> users = new ArrayList<>();
    
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            long chatId = update.getMessage().getChatId();
            String message = update.getMessage().getText();
            int messageId = update.getMessage().getMessageId();

            if (message.equals("/unregister")) {
                User user = update.getMessage().getFrom();
                users.remove(user.getUserName());
            } else if (message.equals("/register")) {
                User user = update.getMessage().getFrom();
                users.add(user.getUserName());
            } else if (message.equals("/getRandomUser")) {
                 if (users.size() > 0) {
                    String randomUserName = users.get((int) (Math.random() * users.size()));
                    sendMessage("Жму вам руку, @" + randomUserName + ", я приятно удивлен)0)))0)00))0 Вы победили!", chatId, messageId);
                } else {
                     sendMessage("Никто не хочет написать мне (9(((99((", chatId, messageId);
                 }
            } else {
                sendMessage(message, chatId, messageId);
            }

        } 
    }


    private void sendPhoto(long chatId, String fileId) {
    }

    @Override
    public String getBotUsername() {
        return "FestivalBananaBot";
    }

    @Override
    public String getBotToken() {
        return "471316058:AAFcgNqAFib33rtld5WhNu1-e7Ktd4Gp_gU";
    }

    private void sendMessage(String text, long chatId, int messageId) {
        SendMessage sendMessage = new SendMessage()
                .setText(text)
                .setChatId(chatId)
                .setReplyToMessageId(messageId);


        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
