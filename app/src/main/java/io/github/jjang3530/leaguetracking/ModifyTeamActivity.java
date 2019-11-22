package io.github.jjang3530.leaguetracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ModifyTeamActivity extends AppCompatActivity {
    private PlayerDB db;
    private EditText teamTextView;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifyteam);

        teamTextView = (EditText) findViewById(R.id.nameEditText);

        // get the intent
        Intent intent = getIntent();
        // get data from the intent
        String teamName = intent.getStringExtra("name");
        id = Integer.parseInt(intent.getStringExtra("id"));

        teamTextView.setText(teamName);
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

    public void onUpdateNameBtnClicked(View v){
        String updateName = teamTextView.getText().toString();
        try {
            db.updateTeam(id, updateName);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Intent intent = new Intent(getApplicationContext(), AddTeamActivity.class);
        startActivity(intent);
    }

    public void onDeleteTeamBtnClicked(View v){
        try {
            db.deleteTeam(id);
        }
        catch(Exception e){
            e.printStackTrace();
        }
        Intent intent = new Intent(getApplicationContext(), AddTeamActivity.class);
        startActivity(intent);
    }

    public void onCancelBtnClicked(View v){
        Intent intent = new Intent(getApplicationContext(), AddTeamActivity.class);
        startActivity(intent);
    }
}