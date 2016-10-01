package com.ridealong.Modules;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ridealong.DriverDetailActivity;
import com.ridealong.R;
import com.ridealong.models.User;

import java.util.ArrayList;

/**
 * Created by Sridhar16 on 6/9/2016.
 */
public class DriverListAdapter extends RecyclerView.Adapter<DriverListAdapter.ListViewHolder>{
    private ArrayList<User> users;
    private Context context;

    public DriverListAdapter(ArrayList<User> users,Context context){
        this.users=users;
        this.context=context;
    }

    @Override
    public ListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_item, null);
        return new ListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ListViewHolder holder, final int position) {

        holder.driverName.setText(users.get(position).getName().toString());

        holder.driverName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent driverDetailIntent = new Intent(context, DriverDetailActivity.class);
                driverDetailIntent.putExtra("userId",users.get(position).getId());
                context.startActivity(driverDetailIntent);
                Log.d("DriverListAdapter",users.get(position).getName() + " "+users.get(position).getId() );
            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ListViewHolder extends RecyclerView.ViewHolder {

        private TextView driverName;

        public ListViewHolder(View itemView) {
            super(itemView);

            driverName = (TextView) itemView.findViewById(R.id.driver_list_view);
        }
    }
}
