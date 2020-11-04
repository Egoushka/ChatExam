package com.example.chatexam;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;

public class MessageListActivity extends AppCompatActivity {

    private Runnable loadJSON;
    private Runnable showJSON;
    private Runnable showLog;
    private String messagesText;

    private TextView tvlog;
    private String logtext;

    private JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        tvlog = ((TextView)findViewById(R.id.tvLog));
        tvlog.setMovementMethod(new ScrollingMovementMethod());

        loadJSON = () ->{
            try ( InputStream resource = new URL(getString(R.string.URLMessages)).openStream() ) {
                String response="";
                int sym;
                while((sym=resource.read())!=-1)
                    response+=(char)sym;
                messagesText = new String(response.getBytes(StandardCharsets.ISO_8859_1),"UTF-8");
                runOnUiThread(showJSON);
            } catch (Exception e) {
                logtext = e.getMessage();
                runOnUiThread(showLog);
            }
            //tvlog.setText(messagesText);

        };
        showLog = () ->{
            tvlog.setText(logtext);
        };

        showJSON = () ->{
            try {
                Message msg = jsonParser.getMessage(messagesText);
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
        };
        (new Thread(loadJSON)).start();

    }
}