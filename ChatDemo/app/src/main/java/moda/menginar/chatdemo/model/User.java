package moda.menginar.chatdemo.model;

import java.util.ArrayList;

/**
 * Created by Menginar on 1.4.2017.
 */

public class User extends  JsonParsable {

    private int jobid;
    private ArrayList<ChatMessage> messageArrayList;

    public User () {}

    public User(int jobid, ArrayList<ChatMessage> messageArrayList) {
        this.jobid = jobid;
        this.messageArrayList = messageArrayList;
    }

    public int getJobid() {
        return jobid;
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }

    public ArrayList<ChatMessage> getMessageArrayList() {
        return messageArrayList;
    }

    public void setMessageArrayList(ArrayList<ChatMessage> messageArrayList) {
        this.messageArrayList = messageArrayList;
    }
}
