package io.github.jjang3530.leaguetracking;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;

public class AddTeamActivity extends AppCompatActivity
    implements View.OnClickListener {
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

        db = new PlayerDB(this);
        updateDisplay();
    }

    @Override
    public void onClick(View v){
        if(v.getId() == R.id.insertButton){
            String sNewPlayer = oNameEdit.getText().toString();
            if(sNewPlayer.matches("")){
                Toast.makeText(getApplicationContext(),"Please put team name!",Toast.LENGTH_LONG).show();
            }else{
                try {
                    db.insertPlayer(sNewPlayer);
                    updateDisplay();
                    oNameEdit.setText("");
                    Toast.makeText(getApplicationContext(),sNewPlayer +" Player Added!",Toast.LENGTH_LONG).show();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }

        if(v.getId() == R.id.itemsListView){
            String selectedTeam = itemsListView.getAdapter().toString();
            Toast.makeText(getApplicationContext(),selectedTeam +" selected!",Toast.LENGTH_LONG).show();
        }
    }

    private void updateDisplay(){
        // create a List of Map<String, ?> objects
        ArrayList<HashMap<String, String>> data = db.getPlayers();

        // create the resource, from, and to variables
        int resource = R.layout.listview_item;
        String[] from = {"name", "wins", "losses", "ties"};
        int[] to = {R.id.nameTextView, R.id.winsTextView, R.id.lossesTextView, R.id.tiesTextView};

        // create and set the adapter
        SimpleAdapter adapter =
                new SimpleAdapter(this, data, resource, from, to);
        itemsListView.setAdapter(adapter);
    }

    public void onBackButtonClicked(View v){
        Toast.makeText(getApplicationContext(),"Pressed Return", Toast.LENGTH_SHORT).show();
        finish();
    }
}
