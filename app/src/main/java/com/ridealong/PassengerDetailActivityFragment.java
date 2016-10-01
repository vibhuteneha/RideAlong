package com.ridealong;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * A placeholder fragment containing a simple view.
 */
public class PassengerDetailActivityFragment extends Fragment implements View.OnClickListener {

    private final String LOG_TAG = PassengerDetailActivity.class.getSimpleName();
    private int userId;
    private TextView name,email,from,dest,carModel,carNum,leavingDate;
    Button mapButton;

    public PassengerDetailActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_passenger_detail, container, false);

        Intent intent = getActivity().getIntent();
        userId = intent.getExtras().getInt("userId");
        Log.v(LOG_TAG,String.valueOf(userId));
        name = (TextView) rootView.findViewById(R.id.passgrName);
        email = (TextView) rootView.findViewById(R.id.passgrEmail);
        from = (TextView) rootView.findViewById(R.id.passgrFrom);
        dest = (TextView) rootView.findViewById(R.id.passgrDest);
        leavingDate = (TextView) rootView.findViewById(R.id.passgrLeavingDate);
        getPassengerDetails();

        mapButton = (Button) rootView.findViewById(R.id.mapButton);

        mapButton.setOnClickListener(this);


        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mapButton) {
            startActivity(new Intent(getActivity(), MapsActivity.class));
        }
    }

    public void getPassengerDetails(){

        try {
            JSONObject obj = new JSONObject();
            obj.put("id", userId);

            String jsonString = obj.toString();
            StringEntity stringEntity = new StringEntity(jsonString);
            stringEntity.setContentType("application/json");
            stringEntity.setContentEncoding("UTF-8");

            AsyncHttpClient client = new AsyncHttpClient();

            client.post(this.getActivity(), "http://www.ridealong.lewebev.com/userDetailsPassenger.php", stringEntity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseString, Throwable throwable) {
                    Log.v(LOG_TAG,"failed to get driver details");

                }


                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseString) {
                    Log.v("response Str",responseString.toString());
                    try {
                        String jsonStr = new String(responseString,"UTF-8");
                        Log.v("json str",jsonStr);
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        String driverDetailsStr = jsonObject.getString("passengerDetails");
                        JSONArray jsonArray = new JSONArray(driverDetailsStr);
                        Log.v("jsonArr len",String.valueOf(jsonArray.length()));
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject dataObject = (JSONObject) jsonArray.get(i);
                            name.setText(dataObject.getString("name"));
                            email.setText(dataObject.getString("email"));
                            from.setText(dataObject.getString("from_place"));
                            dest.setText(dataObject.getString("destination"));
                            leavingDate.setText(dataObject.getString("date"));
                        }
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }catch (Exception e) {
            e.printStackTrace();
        }

    }


}

