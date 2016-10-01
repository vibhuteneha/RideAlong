package com.ridealong.Modules;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ridealong.PassengerDetailActivity;
import com.ridealong.R;
import com.ridealong.models.User;

import java.util.ArrayList;

/**
 * Created by Neha on 6/9/2016.
 */
public class PassengerListAdapter extends RecyclerView.Adapter<PassengerListAdapter.ListViewHolder> {

    private ArrayList<User> users;
    private Context context;

    public PassengerListAdapter(ArrayList<User> users,Context context){
        this.users=users;
        this.context=context;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.passenger_item, null);
        return new ListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {

        holder.passengerName.setText(users.get(position).getName().toString());

        holder.passengerName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent passengerDetailIntent = new Intent(context, PassengerDetailActivity.class);
                passengerDetailIntent.putExtra("userId",users.get(position).getId());
                context.startActivity(passengerDetailIntent);

                Log.d("PassengerListAdapter",users.get(position).getName() + " "+users.get(position).getId() );
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView passengerName;

        public ListViewHolder(View itemView) {
            super(itemView);

            passengerName = (TextView) itemView.findViewById(R.id.txtView);
        }
    }
}
