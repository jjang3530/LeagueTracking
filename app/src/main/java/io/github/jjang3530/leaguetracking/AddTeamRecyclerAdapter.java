package io.github.jjang3530.leaguetracking;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;

public class AddTeamRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private AddTeamActivity addTeamActivity;
    private ArrayList<HashMap<String, String>> data;
    private int resource;
    private String[] from;
    private int[] to;

    public AddTeamRecyclerAdapter(AddTeamActivity addTeamActivity, ArrayList<HashMap<String, String>> data, int resource, String[] from, int[] to) {
        this.addTeamActivity = addTeamActivity;
        this.data = data;
        this.resource = resource;
        this.from = from;
        this.to = to;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ArrayList<TextView> aTexts;
        public TextView popText;

        public MyViewHolder(View view) {
            super(view);
            this.aTexts = new ArrayList<TextView>();
            for(int nId: AddTeamRecyclerAdapter.this.to){
                this.aTexts.add( (TextView) view.findViewById(nId));
            }
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(this.resource,parent, false);
        return new MyViewHolder(v);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HashMap<String, String> map = this.data.get(position);
        for(int nPosition = 0; nPosition < this.from.length; nPosition++){
            String sKey = this.from[nPosition];
            ((MyViewHolder)holder).aTexts.get(nPosition).setText(map.get(sKey));
        }
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }
}
