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

public class AwayTeamActivity extends AppCompatActivity
implements OnItemClickListener {
    private ListView itemsListView;
    private PlayerDB db;
    public static final String awayTeamPrefs = "AwayPref";
    public String selectedAwayTeam;
    public Integer selectedAwayTeamId;
    public Integer selectedAwayWins;
    public Integer selectedAwayLosses;
    public Integer selectedAwayTies;
    public static final String homeTeamPrefs = "HomePref";
    public Integer selectedHomeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_awayteam);
        itemsListView = (ListView) findViewById(R.id.itemsListView);
        itemsListView.setOnItemClickListener(this);

        //check home team selection
        SharedPreferences homePrefs = getSharedPreferences(homeTeamPrefs, MODE_PRIVATE);
        selectedHomeId = homePrefs.getInt("id",0);

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

    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {

        HashMap<String, String> item = db.getTeam(position);

        selectedAwayTeam = item.get("name");
        selectedAwayTeamId = Integer.parseInt(item.get("id"));
        selectedAwayWins = Integer.parseInt(item.get("wins"));
        selectedAwayLosses = Integer.parseInt(item.get("losses"));
        selectedAwayTies = Integer.parseInt(item.get("ties"));

        if(selectedAwayTeamId != selectedHomeId ){
            //set SharedPreferences
            SharedPreferences.Editor awayTeam = getSharedPreferences(awayTeamPrefs, MODE_PRIVATE).edit();
            awayTeam.putString("name", selectedAwayTeam);
            awayTeam.putInt("id", selectedAwayTeamId);
            awayTeam.putInt("wins", selectedAwayWins);
            awayTeam.putInt("losses", selectedAwayLosses);
            awayTeam.putInt("ties", selectedAwayTies);
            awayTeam.apply();

            Toast.makeText(getApplicationContext(),selectedAwayTeam +" selected!",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);

        }else{
            Toast.makeText(getApplicationContext(),"Already Home team selected this team!",Toast.LENGTH_SHORT).show();
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
