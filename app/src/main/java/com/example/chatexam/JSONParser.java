package com.example.chatexam;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JSONParser {

    //public Message getMessage( String response ) throws JSONException, ParseException {
    public Message getMessage( String response ) throws JSONException, ParseException {
        //response = "{\"status\":1,\"data\":[{\"id\":\"88\",\"author\":\"User\",\"text\":\"my message sits here\",\"moment\":\"2020-11-02 12:46:20\"},{\"id\":\"87\",\"author\":\"Test\",\"text\":\"Message\",\"moment\":\"2020-11-01 12:08:27\"},{\"id\":\"86\",\"author\":\"User\",\"text\":\"randomMessage\",\"moment\":\"2020-10-30 20:04:29\"},{\"id\":\"85\",\"author\":\"User\",\"text\":\"SimpleDateFormat очень легко и приятно использовать.\",\"moment\":\"2020-10-30 20:04:07\"},{\"id\":\"84\",\"author\":\"User\",\"text\":\"debug\",\"moment\":\"2020-10-30 18:33:27\"},{\"id\":\"83\",\"author\":\"User\",\"text\":\"randomMessage\",\"moment\":\"2020-10-30 18:27:58\"},{\"id\":\"82\",\"author\":\"User\",\"text\":\"layout\",\"moment\":\"2020-10-30 17:46:37\"},{\"id\":\"81\",\"author\":\"Mich\",\"text\":\"тест2\",\"moment\":\"2020-10-30 17:25:45\"},{\"id\":\"80\",\"author\":\"Mich\",\"text\":\"тест\",\"moment\":\"2020-10-30 17:18:18\"},{\"id\":\"79\",\"author\":\"User\",\"text\":\"layout\",\"moment\":\"2020-10-30 13:33:35\"},{\"id\":\"78\",\"author\":\"User\",\"text\":\"Homework test\",\"moment\":\"2020-10-30 13:32:13\"},{\"id\":\"77\",\"author\":\"DNS\",\"text\":\"test\",\"moment\":\"2020-10-29 17:47:09\"},{\"id\":\"76\",\"author\":\"test\",\"text\":\"test\",\"moment\":\"2020-10-29 17:46:59\"},{\"id\":\"75\",\"author\":\"test\",\"text\":\"test\",\"moment\":\"2020-10-29 17:46:13\"},{\"id\":\"74\",\"author\":\"DNS\",\"text\":\"Yure 12\",\"moment\":\"2020-10-29 17:45:53\"},{\"id\":\"73\",\"author\":\"Karina\",\"text\":\"dsdasd\",\"moment\":\"2020-10-29 17:43:11\"},{\"id\":\"72\",\"author\":\"Karina\",\"text\":\"ttt\",\"moment\":\"2020-10-29 17:39:12\"},{\"id\":\"71\",\"author\":\"Mich\",\"text\":\"тест\",\"moment\":\"2020-10-29 17:37:03\"},{\"id\":\"70\",\"author\":\"User\",\"text\":\"simpleDateFormat <3\",\"moment\":\"2020-10-29 17:35:14\"},{\"id\":\"69\",\"author\":\"Karina\",\"text\":\"tttt\",\"moment\":\"2020-10-29 17:25:02\"}]}";
        JSONObject msgJson = new JSONObject( response );
        JSONArray jsonArray = msgJson.getJSONArray("data");

        ArrayList<Integer> id = new ArrayList<Integer>();
        ArrayList<String> author = new ArrayList<String>();
        ArrayList<String> text = new ArrayList<String>();
        ArrayList<Date> moment = new ArrayList<Date>();


        for (int i = 0; i< jsonArray.length(); ++i){
            id.add(jsonArray.getJSONObject(i).getInt("id"));
            author.add(jsonArray.getJSONObject(i).getString("author"));
            text.add(jsonArray.getJSONObject(i).getString("text"));
            moment.add(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(jsonArray.getJSONObject(i).getString("moment")));
        }
        return new Message( id, author, text, moment );
    }
}
