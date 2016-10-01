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
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.ridealong.models.User;

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
public class DriverDetailActivityFragment extends Fragment implements View.OnClickListener {

    private final String LOG_TAG = DriverDetailActivityFragment.class.getSimpleName();
    private int userId;
    private TextView name,email,from,dest,carModel,carNum,leavingDate;

    Button mapButton;

    public DriverDetailActivityFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_driver_detail, container, false);
        Intent intent = getActivity().getIntent();
        userId = intent.getExtras().getInt("userId");
        Log.v(LOG_TAG,String.valueOf(userId));
        name = (TextView) rootView.findViewById(R.id.driverName);
        email = (TextView) rootView.findViewById(R.id.driverEmail);
        from = (TextView) rootView.findViewById(R.id.driverFrom);
        dest = (TextView) rootView.findViewById(R.id.driverDest);
        carModel = (TextView) rootView.findViewById(R.id.driverCarModel);
        carNum = (TextView) rootView.findViewById(R.id.driverCarNum);
        leavingDate = (TextView) rootView.findViewById(R.id.driverLeavingDate);
        getDriverDetails();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mapButton){
            startActivity(new Intent(getActivity(),MapsActivity.class));
        }
    }

    public void getDriverDetails(){

        try {
            JSONObject obj = new JSONObject();
            obj.put("id", userId);

            String jsonString = obj.toString();
            StringEntity stringEntity = new StringEntity(jsonString);
            stringEntity.setContentType("application/json");
            stringEntity.setContentEncoding("UTF-8");

            AsyncHttpClient client = new AsyncHttpClient();

            client.post(this.getActivity(), "http://www.ridealong.lewebev.com/userDetailsDriver.php", stringEntity, "application/json", new AsyncHttpResponseHandler() {
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
                        String driverDetailsStr = jsonObject.getString("driverDetails");
                        JSONArray jsonArray = new JSONArray(driverDetailsStr);
                        Log.v("jsonArr len",String.valueOf(jsonArray.length()));
                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject dataObject = (JSONObject) jsonArray.get(i);
                            name.setText(dataObject.getString("name"));
                            email.setText(dataObject.getString("email"));
                            from.setText(dataObject.getString("from_place"));
                            dest.setText(dataObject.getString("destination"));
                            carModel.setText(dataObject.getString("car_model"));
                            carNum.setText(dataObject.getString("car_no"));
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
