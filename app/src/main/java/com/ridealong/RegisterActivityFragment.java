package com.ridealong;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import com.ridealong.Constants;
import com.ridealong.LoginFragment;
import com.ridealong.R;
import com.ridealong.RequestInterface;
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
public class RegisterActivityFragment extends Fragment implements View.OnClickListener {

    public RegisterActivityFragment() {
    }

    private Button eregister;
    private TextView elogin;
    private EditText e_email,elastname,efirstname,epassword,ephone;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View view){

        eregister = (Button) view.findViewById(R.id.eregister);
        elogin = (TextView)view.findViewById(R.id.elogin);
        efirstname = (EditText)view.findViewById(R.id.efirstname);
        e_email = (EditText)view.findViewById(R.id.e_email);
        epassword = (EditText)view.findViewById(R.id.epassword);
        elastname = (EditText)view.findViewById(R.id.elastname);
        ephone = (EditText)view.findViewById(R.id.ephone);

        eregister.setOnClickListener(this);
        elogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.elogin:
                goToLogin();
                break;

            case R.id.eregister:

                String firstname = efirstname.getText().toString();
                String lastname = elastname.getText().toString();
                String email = e_email.getText().toString();
                String password = epassword.getText().toString();
                String phone = ephone.getText().toString();

                if(!firstname.isEmpty() && !email.isEmpty() && !password.isEmpty() && !lastname.isEmpty() && !phone.isEmpty()) {

                    registerProcess(firstname,lastname,email,password,phone);

                } else {

                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
                break;


        }

    }

    private void registerProcess(String fName,String lName, String email,String password,String phoneNum){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setName(fName+" "+lName);
        user.setEmail(email);
        user.setPhone(phoneNum);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.REGISTER_OPERATION);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                if(resp.getMessage() == "User Already Registered !"){
                    Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                }else{
                    startActivity(new Intent(getActivity(), MainActivity.class));
                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG,"failed");
                Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();


            }
        });
    }

    private void goToLogin(){

//        Fragment login = new LoginFragment();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.fragment_frame,login);
//        ft.commit();
//
        startActivity(new Intent(getActivity(), LoginFragment.class));
}
}
