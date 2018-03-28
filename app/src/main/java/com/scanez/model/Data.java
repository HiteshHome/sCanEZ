package com.scanez.model;

import android.os.Parcel;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Dhrumil on 9/8/2015.
 */
public class Data  implements Serializable {

    int key;
    String Note;
    String type;
    String date_time;
    String imagePath;

    public Data(int key, String note, String type, String date_time, String imagePath) {
        this.key = key;
        Note = note;
        this.type = type;
        this.date_time = date_time;
        this.imagePath = imagePath;
    }

    public Data(String note, String type, String date_time,String imagePath) {
        Note = note;
        this.type = type;
        this.date_time = date_time;
        this.imagePath = imagePath;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


}
