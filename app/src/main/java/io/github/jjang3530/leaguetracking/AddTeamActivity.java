package io.github.jjang3530.leaguetracking;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class AddTeamActivity extends AppCompatActivity
    implements View.OnClickListener, OnItemClickListener {
    private ListView itemsListView;
    private EditText oNameEdit;
    private Button oNameInsert;
    private PlayerDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addteam);
        itemsListView = (ListView) findViewById(R.id.itemsListView);
        oNameEdit = (EditText) findViewById(R.id.nameEditText);
        oNameInsert = (Button) findViewById(R.id.insertButton);
        oNameInsert.setOnClickListener(this);
        itemsListView.setOnItemClickListener(this);
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
            case R.id.menu_home:
                startActivity(new Intent(getApplicationContext(), HomeTeamActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.insertButton){
            String sNewPlayer = oNameEdit.getText().toString();
            if(sNewPlayer.matches("")){
                Toast.makeText(getApplicationContext(),"Please put team name!",Toast.LENGTH_LONG).show();
            }else{
                try {
                    db.insertTeam(sNewPlayer);
                    updateDisplay();
                    oNameEdit.setText("");
                    Toast.makeText(getApplicationContext(),sNewPlayer +" Player Added!",Toast.LENGTH_LONG).show();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {

        HashMap<String, String> item = db.getTeam(position);

        // create an intent
        Intent intent = new Intent(this, ModifyTeamActivity.class);
        intent.putExtra("id", item.get("id"));
        intent.putExtra("name", item.get("name"));
        intent.putExtra("wins", item.get("wins"));
        intent.putExtra("losses", item.get("losses"));
        intent.putExtra("ties", item.get("ties"));

        this.startActivity(intent);
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
