package com.ridealong;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    private SharedPreferences sharedPreferences;

    private void logout() {
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(Constants.IS_LOGGED_IN,false);
        editor.putString(Constants.EMAIL,"");
        editor.putString(Constants.NAME,"");
        editor.putString(Constants.UNIQUE_ID,"");
        editor.apply();
        editor.commit();
        goToLogin();
    }

    private void goToLogin(){

        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


//        if(id == R.id.action_notification){
//            Intent intent = new Intent(this, NotificationActivity.class);
//            startActivity(intent);
//            return true;
//
//        }

        if(id == R.id.action_logout){
            logout();
            return true;
        }

//        if(id == R.id.action_profile){
//            Intent intent = new Intent(this, ProfileActivity.class);
//            startActivity(intent);
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
