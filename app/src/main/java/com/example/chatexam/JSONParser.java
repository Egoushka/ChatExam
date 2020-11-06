package com.example.chatexam;

import android.annotation.SuppressLint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JSONParser {   // парсинг JSON ответа

    @SuppressLint( "SimpleDateFormat" )
    public Message getMessage( String response ) throws JSONException, ParseException {

        JSONObject msgJson = new JSONObject( response );
        JSONArray jsonArray = msgJson.getJSONArray( "data" );

        ArrayList<Integer> id = new ArrayList<Integer>();
        ArrayList<String> author = new ArrayList<String>();
        ArrayList<String> text = new ArrayList<String>();
        ArrayList<Date> moment = new ArrayList<Date>();


        for ( int i = 0; i< jsonArray.length(); ++i ){
            id.add( jsonArray.getJSONObject(i).getInt( "id" ) );
            author.add( jsonArray.getJSONObject(i).getString( "author" ) );
            text.add( jsonArray.getJSONObject(i).getString( "text" ) );
            moment.add( new SimpleDateFormat( String.valueOf( R.string.data_string_format ) ).parse( jsonArray.getJSONObject(i).getString( "moment" ) ) );
        }
        return new Message( id, author, text, moment );
    }
}
