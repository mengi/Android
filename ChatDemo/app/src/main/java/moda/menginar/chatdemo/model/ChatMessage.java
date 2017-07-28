package moda.menginar.chatdemo.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatMessage {
    private int isType;
    private String text;
    private String date;

    public ChatMessage(String message, int isType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        this.date = dateFormat.format(date);
        this.text = message;
        this.isType = isType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return isType;
    }

    public void setType(int isType) {
        this.isType = isType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        date = date;
    }
}
