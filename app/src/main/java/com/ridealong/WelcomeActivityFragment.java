package com.ridealong;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.ridealong.models.ServerRequest;
import com.ridealong.models.ServerResponse;
import com.ridealong.models.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class WelcomeActivityFragment extends Fragment implements View.OnClickListener{

    public WelcomeActivityFragment() {
    }



    private SharedPreferences sharedPreferences;
    private TextView u_name , u_email ,u_logout;
    private Button btn_logout;
    private int userId;
    private int userId1;


    Button driver, rider;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);


        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        driver = (Button) view.findViewById(R.id.driver);
        rider = (Button) view.findViewById(R.id.rider);
      //  btn_logout = (Button) view.findViewById(R.id.btn_logout);


        driver.setOnClickListener(this);
        rider.setOnClickListener(this);

      //  btn_logout.setOnClickListener(this);


        //userId = sharedPreferences.getInt(Constants.USER_ID,0);

        //Log.v("user id is",String.valueOf(userId));
        userId = getActivity().getIntent().getExtras().getInt("userid1");
        Log.v("user id is",String.valueOf(userId));
        return view;

}





    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.driver:
                Intent driverIntent = new Intent(getActivity(), DriverActivity.class);
                driverIntent.putExtra("userId",userId);
                //Log.v("userid1=",userId1);
                startActivity(driverIntent);
                break;

            case R.id.rider:
                Intent passgrIntent = new Intent(getActivity(), PassengerActivity.class);
                passgrIntent.putExtra("userId",userId);
                //Log.v("userid1=",userId1);
                startActivity(passgrIntent);
                break;

         /*case R.id.btn_logout:
               logout();
               break;*/

        }
    }

    private void logout() {
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

//        Fragment login = new LoginFragment();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_frame,login);
//        ft.commit();
        startActivity(new Intent(getActivity(), LoginActivity.class));

    }

}
