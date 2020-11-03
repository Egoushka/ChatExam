package com.example.chatexam;

import java.util.Date;
import java.util.List;

public class Message {
    public List<Integer> id;
    public List<String> author;
    public List<String>text;
    public List<Date> moment;

    public Message(List<Integer> id, List<String> author, List<String> text, List<Date> moment) {
        this.id = id;
        this.author = author;
        this.text = text;
        this.moment = moment;
    }
}
