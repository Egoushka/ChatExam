package com.example.chatexam;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.text.ParseException;

public class MessageListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        JSONParser jsonParser = new JSONParser();
        try {
            Message msg = jsonParser.getMessage("ss");
            String txt = "";
            for(int i = 0; i< msg.text.size();++i){
                txt += msg.text.get(i) + "\n";
            }

            ((TextView)findViewById(R.id.tvLog)).setText(txt);
        } catch (JSONException e) {
            ((TextView)findViewById(R.id.tvLog)).setText(e.getMessage());
        } catch (ParseException e) {
            ((TextView)findViewById(R.id.tvLog)).setText(e.getMessage());
        }
    }
}