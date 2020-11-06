package com.example.chatexam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
    }

    public void btnMsgListActivity( View view ) {
        Intent msgActivity = new Intent( this, MessageListActivity.class );
        // аналог ViewBag
        msgActivity.putExtra( "user_nickname", ( ( EditText ) findViewById( R.id.editTextTextPersonName ) ).getText() );
        startActivity( msgActivity );
    }
}