import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HolidayBot extends TelegramLongPollingBot {
    
    ArrayList<String> users = new ArrayList<>();
    HashMap<String, String> holidays = new HashMap<>();
    boolean feastMode = false;
    boolean addHolidayMode = false;

    public HolidayBot() {
        holidays.put("23 февраля", "в России отмечается День Защитника Отечества.");
        holidays.put("1 января", "более чем в 12 странах мира, отмечают Новый Год.");
        holidays.put("7 января", "в странах, где исповедуют христианство, отмечают Рождество. Но в некоторых странах, например, в Швеции, Норвегии, Финляндии, Франции и др, Рождество отмечают 25 декабря!");
        holidays.put("16 октября", "национальный праздник в Польше. В этот день отмечается день памяти Иоанна Павла 2 - первого польского папы.");
        holidays.put("8 марта", "в России отмечают женский день.");
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            long chatId = update.getMessage().getChatId();
            String message = update.getMessage().getText();
            int messageId = update.getMessage().getMessageId();


            if (feastMode) {
                String holiday = holidays.get(message);
                sendMessage("В этот день " + holiday, chatId);
                feastMode = false;
            } else if (addHolidayMode) {
                addHoliday(message, chatId);
             // sendMessage("Праздник добавлен " + message, chatId);
                addHolidayMode = false;
            } else {
                    switch (message) {
                        case "/getHoliday":
                            getHoliday(chatId);
                            sendMessage("Какая дата тебя интересует?", chatId);
                            feastMode = true;
                            break;
                        case "/addHoliday":
                            sendMessage("Скажи, пожалуйста, дату, а затем сам праздник", chatId);
                            addHolidayMode = true;
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


    private void addHoliday(String text, long charId) {
        String[] кусочки = text.split(" - ");
        holidays.put(кусочки[0], кусочки[1]);
        sendMessage("Праздник добавлен. Спасибо за помощь.)", charId);
    }

    private void getHoliday(long charId) {
//
    }



    //===============================================================================================================================

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
