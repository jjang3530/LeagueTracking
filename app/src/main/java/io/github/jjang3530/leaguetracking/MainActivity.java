package io.github.jjang3530.leaguetracking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String homeTeamPrefs = "HomePref";
    public static final String awayTeamPrefs = "AwayPref";
    private String awayTeam;
    private Integer awayId;
    private String homeTeam;
    private Integer homeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences awayPrefs = getSharedPreferences(awayTeamPrefs, MODE_PRIVATE);
        awayTeam = awayPrefs.getString("name", "No name defined");//"No name defined" is the default value.
        awayId = awayPrefs.getInt("id", 0); //0 is the default value.

        SharedPreferences homePrefs = getSharedPreferences(homeTeamPrefs, MODE_PRIVATE);
        homeTeam = homePrefs.getString("name", "No name defined");//"No name defined" is the default value.
        homeId = homePrefs.getInt("id", 0); //0 is the default value.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_standings:
                startActivity(new Intent(getApplicationContext(), StandingsActivity.class));
                return true;
            case R.id.menu_away:
                startActivity(new Intent(getApplicationContext(), AwayTeamActivity.class));
                return true;
            case R.id.menu_home:
                startActivity(new Intent(getApplicationContext(), HomeTeamActivity.class));
                return true;
            case R.id.menu_addteam:
                startActivity(new Intent(getApplicationContext(), AddTeamActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onStartGameBtnClicked(View v){

        if(awayId == 0){
            Toast.makeText(getApplicationContext(),"Please select Away Team",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), AwayTeamActivity.class);
            startActivity(intent);
        }
        else if(homeId == 0){
            Toast.makeText(getApplicationContext(),"Please select Home Team",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), HomeTeamActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(getApplicationContext(), GameEmulatorActivity.class);
            startActivity(intent);
        }
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
