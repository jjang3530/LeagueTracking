package io.github.jjang3530.leaguetracking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onStartGameBtnClicked(View v){
        Intent intent = new Intent(getApplicationContext(), GameEmulatorActivity.class);
        startActivity(intent);
    }

    public void onStandingsBtnClicked(View v){
        Intent intent = new Intent(getApplicationContext(), StandingsActivity.class);
        startActivity(intent);
    }

    public void onAwayTeamBtnClicked(View v){
        Intent intent = new Intent(getApplicationContext(), AwayTeamActivity.class);
        startActivity(intent);
    }

    public void onHomeTeamBtnClicked(View v){
        Intent intent = new Intent(getApplicationContext(), HomeTeamActivity.class);
        startActivity(intent);
    }

    public void onAddBtnClicked(View v){
        Intent intent = new Intent(getApplicationContext(), AddTeamActivity.class);
        startActivity(intent);
    }

}
