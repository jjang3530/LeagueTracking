package io.github.jjang3530.leaguetracking;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameEmulatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameemulator);
    }

    public void onBackButtonClicked(View v){
        Toast.makeText(getApplicationContext(),"Pressed Return", Toast.LENGTH_LONG).show();
        finish();
    }
}
