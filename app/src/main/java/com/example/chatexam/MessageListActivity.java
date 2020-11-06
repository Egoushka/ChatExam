package com.example.chatexam;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
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


    private Runnable loadJSON;      // загрузка JSON с сервера
    private Runnable showJSON;      // отображение JSON в activity_message_list.xml в виде "облачек"
    private Runnable showLog;       // отображение logtext в поле tvlog
    private String messagesText;    // ответ запроса

    private TextView tvlog;         // лог поле для ошибок
    private String logtext;         // текст лога

    private LinearLayout msgLayout; // поле в котором содержаться сообщения

    private JSONParser jsonParser = new JSONParser();   // экземпляр класса JSONParser

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_message_list );

        Bundle arguments = getIntent().getExtras();
        String name = arguments.get( "user_nickname" ).toString();    // user_nickname from activity_main

        setTitle( "ChatExam Username: " + name );

        // инициализация переменной tvlog для управления объектом
        tvlog = findViewById (R.id.tvLog );
        tvlog.setMovementMethod( new ScrollingMovementMethod() );

        // инициализация переменной msgLayout для управления объектом
        msgLayout = findViewById( R.id.messageLayout );

        // функция получения JSON с сервера
        loadJSON = () ->{
            // получение JSON с сервера путём отправки запроса
            try ( InputStream resource = new URL( getString( R.string.URLMessages ) ).openStream() ) {
                StringBuilder response = new StringBuilder();
                int sym;
                while( ( sym=resource.read() ) != -1 )
                    response.append( (char) sym );
                messagesText = new String( response.toString().getBytes( StandardCharsets.ISO_8859_1 ), StandardCharsets.UTF_8 );

                // запуск отображения сообщений
                runOnUiThread( showJSON );
            } catch ( Exception e ) {
                logtext = e.getMessage();
                runOnUiThread( showLog );
            }
        };

        // вывод текста в лог
        showLog = () ->{
            tvlog.setText( logtext );
        };

        // отображение сообщений
        showJSON = () ->{
            try {

                // парсинг ответа запроса
                Message msg = jsonParser.getMessage( messagesText );
                String txt = "";

                // массив облачек сообщений
                TextView[] msgBoxs = new TextView[ msg.text.size() ];

                // хз копипаст с интернета
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT );
                lp.setMargins( (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 15, this.getResources().getDisplayMetrics() ), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, this.getResources().getDisplayMetrics() ), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, this.getResources().getDisplayMetrics() ),(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, this.getResources().getDisplayMetrics() ) );

                // объект для форматирования даты
                @SuppressLint( "SimpleDateFormat" ) SimpleDateFormat simpleDateFormat = new SimpleDateFormat( getString( R.string.data_string_format ) );

                for(int i = 0; i< msg.text.size();++i){
                    // инициализация текстового поля
                    msgBoxs[i] = new TextView(this);

                    // форматирование текстового поля под единый шаблон
                    msgBoxs[i].setPadding( (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, this.getResources().getDisplayMetrics() ), (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 10, this.getResources().getDisplayMetrics() ), (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 10, this.getResources().getDisplayMetrics() ), (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, this.getResources().getDisplayMetrics() ) );
                    msgBoxs[i].setLayoutParams( lp );
                    msgBoxs[i].setWidth( msgLayout.getWidth() );
                    msgBoxs[i].setHeight( (int) TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, 100, this.getResources().getDisplayMetrics() ) );
                    msgBoxs[i].setGravity( 19 ); // Gravity.CENTER|Gravity.LEFT
                    msgBoxs[i].setBackgroundColor( Color.parseColor(getString(R.string.color_message_box) ) );

                    // добавление текста в текстовое поле
                    msgBoxs[i].setText(String.format( "%s : %s\nmoment: %s", msg.author.get(i), msg.text.get(i), simpleDateFormat.format(msg.moment.get(i) ) ) );

                    // отображение облачка
                    msgLayout.addView( msgBoxs[i] );
                }
            } catch (JSONException | ParseException e) {
                ( (TextView) findViewById( R.id.tvLog ) ).setText(e.getMessage() );
            }
        };

        // старт программы при запуске
        ( new Thread(loadJSON) ).start();


    }
}