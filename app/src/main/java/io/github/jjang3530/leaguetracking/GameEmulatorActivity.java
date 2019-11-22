package io.github.jjang3530.leaguetracking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GameEmulatorActivity extends AppCompatActivity {
    public static final String homeTeamPrefs = "HomePref";
    public static final String awayTeamPrefs = "AwayPref";
    SharedPreferences awayPrefs;
    SharedPreferences homePrefs;
    private TextView awayTeam;
    public String selectedAwayTeam;
    public Integer selectedAwayTeamId;
    public Integer selectedAwayWins;
    public Integer selectedAwayLosses;
    public Integer selectedAwayTies;
    public Integer updateAwayLoss;
    private TextView homeTeam;
    public String selectedHomeTeam;
    public Integer selectedHomeTeamId;
    public Integer selectedHomeWins;
    public Integer selectedHomeLosses;
    public Integer selectedHomeTies;
    public Integer updateHomeWin;
    private PlayerDB db;
    private Button awayWinBtn;
    private Button homeWinBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameemulator);

        awayPrefs = getSharedPreferences(awayTeamPrefs, MODE_PRIVATE);
        selectedAwayTeam = awayPrefs.getString("name", "No name defined");//"No name defined" is the default value.
        selectedAwayTeamId = awayPrefs.getInt("id", 0); //0 is the default value.
        selectedAwayWins = awayPrefs.getInt("wins", 0);
        selectedAwayLosses = awayPrefs.getInt("losses", 0);
        selectedAwayTies = awayPrefs.getInt("ties", 0);

        homePrefs = getSharedPreferences(homeTeamPrefs, MODE_PRIVATE);
        selectedHomeTeam = homePrefs.getString("name", "No name defined");//"No name defined" is the default value.
        selectedHomeTeamId = homePrefs.getInt("id", 0); //0 is the default value.
        selectedHomeWins = homePrefs.getInt("wins", 0);
        selectedHomeLosses = homePrefs.getInt("losses", 0);
        selectedHomeTies = homePrefs.getInt("ties", 0);

        awayTeam = (TextView) findViewById(R.id.awayTeamName);
        homeTeam = (TextView) findViewById(R.id.homeTeamName);
        awayWinBtn = findViewById(R.id.awayWinBtn);
        homeWinBtn = findViewById(R.id.homeWinBtn);

        awayWinBtn.setText(selectedAwayTeam + " WIN");
        homeWinBtn.setText(selectedHomeTeam + " WIN");
        awayTeam.setText(selectedAwayTeam);
        homeTeam.setText(selectedHomeTeam);

        db = new PlayerDB(this);
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
            case R.id.menu_main:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                return true;
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

    public void onAwayWinBtnClicked(View v){
        Toast.makeText(getApplicationContext(),"WIN", Toast.LENGTH_SHORT).show();

        //update DB
        Integer updateAwayWin = selectedAwayWins + 1;
        Integer updateHomeLoss = selectedHomeLosses + 1;
        try {
            db.updateWinScore(selectedAwayTeamId, updateAwayWin);
            db.updateLossScore(selectedHomeTeamId, updateHomeLoss);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        //clear Shared Preferences
        awayPrefs = getSharedPreferences(awayTeamPrefs, MODE_PRIVATE);
        awayPrefs.edit().clear().commit();

        homePrefs = getSharedPreferences(homeTeamPrefs, MODE_PRIVATE);
        homePrefs.edit().clear().commit();

        Intent intent = new Intent(getApplicationContext(), StandingsActivity.class);
        startActivity(intent);
    }

    public void onHomeWinBtnClicked(View v){
        Toast.makeText(getApplicationContext(),"WIN", Toast.LENGTH_SHORT).show();

        //update DB
        updateHomeWin = selectedHomeWins + 1;
        updateAwayLoss = selectedAwayLosses + 1;
        try {
            db.updateWinScore(selectedHomeTeamId, updateHomeWin);
            db.updateLossScore(selectedAwayTeamId, updateAwayLoss);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        //clear Shared Preferences
        awayPrefs = getSharedPreferences(awayTeamPrefs, MODE_PRIVATE);
        awayPrefs.edit().clear().commit();

        homePrefs = getSharedPreferences(homeTeamPrefs, MODE_PRIVATE);
        homePrefs.edit().clear().commit();

        Intent intent = new Intent(getApplicationContext(), StandingsActivity.class);
        startActivity(intent);
    }

    public void onTieGameBtnClicked(View v){
        Toast.makeText(getApplicationContext(),"TIE GAME", Toast.LENGTH_SHORT).show();

        //update DB
        Integer updateHomeTie = selectedHomeTies + 1;
        Integer updateAwayTie = selectedAwayTies + 1;
        try {
            db.updateTieScore(selectedHomeTeamId, updateHomeTie);
            db.updateTieScore(selectedAwayTeamId, updateAwayTie);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        //clear Shared Preferences
        awayPrefs = getSharedPreferences(awayTeamPrefs, MODE_PRIVATE);
        awayPrefs.edit().clear().commit();

        homePrefs = getSharedPreferences(homeTeamPrefs, MODE_PRIVATE);
        homePrefs.edit().clear().commit();

        Intent intent = new Intent(getApplicationContext(), StandingsActivity.class);
        startActivity(intent);
    }
}
