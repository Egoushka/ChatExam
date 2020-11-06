package com.example.chatexam;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;

import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MessageListActivity extends AppCompatActivity {

    private Runnable loadJSON;
    private Runnable showJSON;
    private Runnable showLog;
    private String messagesText;

    private TextView tvlog;
    private String logtext;

    private LinearLayout msgLayout;

    private JSONParser jsonParser = new JSONParser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        tvlog = ((TextView)findViewById(R.id.tvLog));
        tvlog.setMovementMethod(new ScrollingMovementMethod());

        msgLayout = findViewById(R.id.messageLayout);


        loadJSON = () ->{
            try ( InputStream resource = new URL(getString(R.string.URLMessages)).openStream() ) {
                String response = "";
                int sym;
                while((sym=resource.read())!=-1)
                    response+=(char)sym;
                messagesText = new String(response.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
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

                TextView[] msgBoxs = new TextView[msg.text.size()];
                //TextView msgBox = new TextView(this);

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, this.getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, this.getResources().getDisplayMetrics()), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, this.getResources().getDisplayMetrics()),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, this.getResources().getDisplayMetrics()));

                String pattern = "dd-MM-yyyy HH:mm:ss";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                //String date = new Date());

                for(int i = 0; i< msg.text.size();++i){
                    //txt += msg.author.get(i)+ " : " + msg.text.get(i) + "\n";
                    msgBoxs[i] = new TextView(this);
                    msgBoxs[i].setPadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, this.getResources().getDisplayMetrics()),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, this.getResources().getDisplayMetrics()),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, this.getResources().getDisplayMetrics()),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, this.getResources().getDisplayMetrics()));
                    msgBoxs[i].setLayoutParams(lp);
                    msgBoxs[i].setWidth(msgLayout.getWidth());
                    msgBoxs[i].setHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100, this.getResources().getDisplayMetrics()));
                    msgBoxs[i].setGravity(Gravity.CENTER|Gravity.LEFT);
                    msgBoxs[i].setBackgroundColor(Color.parseColor("#B0F6DD"));
                    msgBoxs[i].setText(msg.author.get(i)+ " : " + msg.text.get(i) + "\nmoment: " + simpleDateFormat.format(msg.moment.get(i)));
                    msgLayout.addView(msgBoxs[i]);
                }


                ((TextView)findViewById(R.id.tvLog)).setText(txt);
            } catch (JSONException | ParseException e) {
                ((TextView)findViewById(R.id.tvLog)).setText(e.getMessage());
            }
        };

        (new Thread(loadJSON)).start();


    }
}