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
import android.view.LayoutInflater;
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


public class LoginFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences pref;
    private EditText eusername, epassword, e_email;
    private Button elogin;
    private Button registerlink;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view) {

        pref = getActivity().getPreferences(Context.MODE_PRIVATE);

        elogin = (Button) view.findViewById(R.id.elogin);
        // tv_register = (TextView)view.findViewById(R.id.tv_register);
        eusername = (EditText) view.findViewById(R.id.eusername);
        //et_email = (EditText)view.findViewById(R.id.et_email);
        epassword = (EditText) view.findViewById(R.id.epassword);
        e_email = (EditText) view.findViewById(R.id.eusername);
        //progress = (ProgressBar)view.findViewById(R.id.progress);
        registerlink = (Button) view.findViewById(R.id.registerlink);
        registerlink.setOnClickListener(this);
        elogin.setOnClickListener(this);
        //tv_register.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {


            case R.id.elogin:
                String email = e_email.getText().toString();
                String password = epassword.getText().toString();

                if (!email.isEmpty() && !password.isEmpty()) {

                    //progress.setVisibility(View.VISIBLE);
                    loginProcess(email, password);

                } else {

                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;

            case R.id.registerlink:
                startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;

        }
    }

    private void loginProcess(String email, String password) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.LOGIN_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                if (resp.getResult().equals(Constants.SUCCESS)) {
                    SharedPreferences.Editor editor = pref.edit();

                    editor.putBoolean(Constants.IS_LOGGED_IN, true);
                    editor.putString(Constants.EMAIL, resp.getUser().getEmail());
                    editor.putInt(Constants.USER_ID, resp.getUser().getId());
                    Log.v("user id resp", String.valueOf(resp.getUser().getId()));
                    Log.v("unique id", resp.getUser().getUnique_id());
                    editor.putString(Constants.NAME, resp.getUser().getName());
                    editor.putString(Constants.UNIQUE_ID, resp.getUser().getUnique_id());
                    Log.d(Constants.EMAIL, resp.getUser().getEmail());
                    Log.d(Constants.NAME, resp.getUser().getName());

                    editor.commit();

                    Log.d(Constants.TAG,"success");
                    Intent i = new Intent(getActivity(), WelcomeActivity.class);
                    i.putExtra("userid1",resp.getUser().getId());
                    startActivity(i);
//                    goToWelcome();

                    Log.d(Constants.TAG, "success");


                }
            }


            private void goToWelcome() {


//                Fragment welcome = new WelcomeActivityFragment();
//                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                ft.replace(R.id.fragment_frame,welcome);
//                ft.commit();

                startActivity(new Intent(getActivity(), WelcomeActivity.class));
                getActivity().finish();

            }


            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                // progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG, "failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }
}
