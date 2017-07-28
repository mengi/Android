package com.example.photomap.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by menginar on 22.07.2017.
 */

public class ComplaintMessage {

    @SerializedName("complaintName")
    @Expose
    private String complaintName;

    @SerializedName("complaintEmail")
    @Expose
    private String complaintEmail;

    @SerializedName("complaintTitle")
    @Expose
    private String complaintTitle;

    @SerializedName("complaintSubject")
    @Expose
    private String complaintSubject;

    @SerializedName("complainDate")
    @Expose
    private String complainDate;

    public String getComplainDate() {
        return complainDate;
    }

    public void setComplainDate(String complainDate) {
        this.complainDate = complainDate;
    }

    public String getComplaintName() {
        return complaintName;
    }

    public void setComplaintName(String complaintName) {
        this.complaintName = complaintName;
    }

    public String getComplaintEmail() {
        return complaintEmail;
    }

    public void setComplaintEmail(String complaintEmail) {
        this.complaintEmail = complaintEmail;
    }

    public String getComplaintTitle() {
        return complaintTitle;
    }

    public void setComplaintTitle(String complaintTitle) {
        this.complaintTitle = complaintTitle;
    }

    public String getComplaintSubject() {
        return complaintSubject;
    }

    public void setComplaintSubject(String complaintSubject) {
        this.complaintSubject = complaintSubject;
    }
}
