import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.PhotoSize;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HolidayBot extends TelegramLongPollingBot {
    
    ArrayList<String> users = new ArrayList<>();
    HashMap<String, String> cities = new HashMap<>();
    boolean addMode = false;
    
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            long chatId = update.getMessage().getChatId();
            String message = update.getMessage().getText();
            int messageId = update.getMessage().getMessageId();


            if (addMode) {
                addCity(message,chatId);
                addMode = false;
            } else {
                switch (message) {
                    case "/addCity":
                        sendMessage("Какой город?", chatId);
                        addMode = true;
                        break;
                    case "/getCities":
                        getCities(chatId);
                        break;
                    default:
                        sendMessage(message, chatId, messageId);
                }

            }
        }
    }



    @Override
    public String getBotUsername() {
        return "FestivalBananaBot";
    }

    @Override
    public String getBotToken() {
        return "471316058:AAFcgNqAFib33rtld5WhNu1-e7Ktd4Gp_gU";
    }


    private void addCity(String text, long charId) {
        String[] кусочки = text.split(" ");
        cities.put(кусочки[0], кусочки[1]);
        sendMessage("Город добавлен", charId);
    }

    private void getCities(long charId) {
        String result = "Города: /n ";
        for (Map.Entry<String, String> строчка : cities.entrySet()) {
            result += строчка.getKey() + " - " + строчка.getValue();
            result += " /n ";
        }
        sendMessage(result, charId);
    }



    //================================================================================================================

    private void sendMessage(String text, long chatId) {
        SendMessage sendMessage = new SendMessage()
                .setText(text)
                .setChatId(chatId);


        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
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
