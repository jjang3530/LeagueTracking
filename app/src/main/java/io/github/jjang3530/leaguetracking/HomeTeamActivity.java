package io.github.jjang3530.leaguetracking;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeTeamActivity extends AppCompatActivity
    implements OnItemClickListener{
    private ListView itemsListView;
    private PlayerDB db;
    public static final String homeTeamPrefs = "HomePref";
    public String selectedHomeTeam;
    public Integer selectedHomeTeamId;
    public Integer selectedHomeWins;
    public Integer selectedHomeLosses;
    public Integer selectedHomeTies;
    public static final String awayTeamPrefs = "AwayPref";
    public Integer selectedAwayTeamId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hometeam);
        itemsListView = (ListView) findViewById(R.id.itemsListView);
        itemsListView.setOnItemClickListener(this);

        //check away team selection
        SharedPreferences awayPrefs = getSharedPreferences(awayTeamPrefs, MODE_PRIVATE);
        selectedAwayTeamId = awayPrefs.getInt("id", 0); //0 is the default value.

        db = new PlayerDB(this);
        updateDisplay();
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
            case R.id.menu_addteam:
                startActivity(new Intent(getApplicationContext(), AddTeamActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {
        HashMap<String, String> item = db.getTeam(position);

        selectedHomeTeam = item.get("name");
        selectedHomeTeamId = Integer.parseInt(item.get("id"));
        selectedHomeWins = Integer.parseInt(item.get("wins"));
        selectedHomeLosses = Integer.parseInt(item.get("losses"));
        selectedHomeTies = Integer.parseInt(item.get("ties"));

        if(selectedHomeTeamId != selectedAwayTeamId ) {
            //set SharedPreferences
            SharedPreferences.Editor homeTeam = getSharedPreferences(homeTeamPrefs, MODE_PRIVATE).edit();
            homeTeam.putString("name", selectedHomeTeam);
            homeTeam.putInt("id", selectedHomeTeamId);
            homeTeam.putInt("wins", selectedHomeWins);
            homeTeam.putInt("losses", selectedHomeLosses);
            homeTeam.putInt("ties", selectedHomeTies);
            homeTeam.apply();

            Toast.makeText(getApplicationContext(), selectedHomeTeam + " selected!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }else{
            Toast.makeText(getApplicationContext(),"Already Away team selected this team!",Toast.LENGTH_SHORT).show();
        }
    }

    private void updateDisplay(){
        ArrayList<HashMap<String, String>> data = db.getTeams();

        int resource = R.layout.listview_item;
        String[] from = {"name", "wins", "losses", "ties"};
        int[] to = {R.id.nameTextView, R.id.winsTextView, R.id.lossesTextView, R.id.tiesTextView};

        SimpleAdapter adapter =
                new SimpleAdapter(this, data, resource, from, to);
        itemsListView.setAdapter(adapter);
    }
}
