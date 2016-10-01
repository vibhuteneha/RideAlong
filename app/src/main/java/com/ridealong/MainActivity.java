package com.ridealong;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences pref;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getPreferences(0);
        initFragment();
    }

    private void initFragment(){

        //fragment=null;
        if(pref.getBoolean(Constants.IS_LOGGED_IN,false)){
            System.out.println("logged in");
            //fragment = new WelcomeActivityFragment();
            Intent intent = new Intent(context,(WelcomeActivity.class));
            startActivity(intent);
            finish();

        }else {
            Log.v("Not Loggedin", "Not Logged in");
            startActivity(new Intent(context,LoginActivity.class));
            finish();
           // setContentView(R.layout.activity_login);

            //Intent intent = new Intent(this, com.ridealong.LoginFragment.class);
//            Fragment fragment = new LoginFragment();
//            FragmentTransaction ft = getFragmentManager().beginTransaction();
//            ft.replace(R.id.fragment_frame,fragment);
//            ft.commit();
//            System.out.println("Not logged in");
//            //startActivity(intent);
        }


    }

//    @Override
//    public void onBackPressed() {
//        finish();
//    }
}
