package com.example.javier.spoonplayers.Model;

/**
 * Created by Menginar on 31.3.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Notice {

    @SerializedName("TopicTitle")
    @Expose
    private String topicTitle;
    @SerializedName("Subject")
    @Expose
    private String subject;
    @SerializedName("Dates")
    @Expose
    private String dates;
    @SerializedName("PicturePath")
    @Expose
    private String picturePath;

    public String getTopicTitle() {
        return topicTitle;
    }

    public void setTopicTitle(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public String getDateToString () {

        String AdYil = "";

        ArrayList<String> AyListe = new ArrayList<String >();
        AyListe.add(0, "Ocak");
        AyListe.add(1, "Şubat");
        AyListe.add(2, "Mart");
        AyListe.add(3, "Nisan");
        AyListe.add(4, "Mayıs");
        AyListe.add(5, "Haziran");
        AyListe.add(6, "Temmuz");
        AyListe.add(7, "Ağustos");
        AyListe.add(8, "Eylül");
        AyListe.add(9, "Ekim");
        AyListe.add(10, "Kasım");
        AyListe.add(11, "Aralık");

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        String year = Integer.toString(cal.get(Calendar.YEAR));

        String Yil = this.dates.substring(0, 4);
        String Ay = this.dates.substring(5, 7);
        String Gun = this.dates.substring(8, 10);

        if (year.equals(Yil)) {
            AdYil = Gun + " " + AyListe.get(Integer.parseInt(Ay) - 1);
        }

        return AdYil;
    }

}
