package com.ridealong;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.ridealong.Modules.PassengerListAdapter;
import com.ridealong.models.PassengerDetails;
import com.ridealong.models.ServerRequest;
import com.ridealong.models.ServerResponse;
import com.ridealong.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.msebera.android.httpclient.entity.StringEntity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class PassengerListActivityFragment extends Fragment {

    private static final String LOG_TAG = PassengerListActivityFragment.class.getSimpleName();

    public PassengerListActivityFragment() {
    }

    ArrayList<User> users=new ArrayList<User>();
    String driverFrm;
    String driverTo;
    String leavingDate;
    RecyclerView recyclerView;
    private PassengerListAdapter mAdapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_passenger_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new PassengerListAdapter(users,getActivity());
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        addPsgrLists();
        return view;
    }


    public void addPsgrLists(){

        Log.i(LOG_TAG,"in add psgr fn");

        driverFrm = getActivity().getIntent().getExtras().getString("drStartPt");
        driverTo = getActivity().getIntent().getExtras().getString("drDestPt");
        leavingDate = getActivity().getIntent().getExtras().getString("drDate");
        Log.v("driver from",driverFrm);
        Log.v("driver to",driverTo);
        Log.v("driver leaving date",leavingDate);

        getPassgrListUsingFrmAndTo();

//        getDriverListUsingFrm(passengerFrom);

    }

    public void getPassgrListUsingFrmAndTo(){

        try {
            JSONObject obj = new JSONObject();
            obj.put("driverFrom", driverFrm);
            obj.put("driverTo",driverTo);
            obj.put("leavingDate",leavingDate);
            String jsonString = obj.toString();
            StringEntity stringEntity = new StringEntity(jsonString);
            stringEntity.setContentType("application/json");
            stringEntity.setContentEncoding("UTF-8");

            AsyncHttpClient client = new AsyncHttpClient();

            client.post(this.getActivity(), "http://www.ridealong.lewebev.com/passenger_list.php", stringEntity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseString, Throwable throwable) {

                }


                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseString) {
                    Log.v("response Str",responseString.toString());
                    try {
                        String jsonStr = new String(responseString,"UTF-8");
                        Log.v("json str",jsonStr);
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        String driverListStr = jsonObject.getString("passengerList");
                        JSONArray jsonArray = new JSONArray(driverListStr);
                        Log.v("jsonArr len",String.valueOf(jsonArray.length()));
                        if(jsonArray.length() == 0){
                            Toast.makeText(getActivity(),"No Passengers availble", Toast.LENGTH_SHORT).show();

                        }
                        for(int i= 0;i<jsonArray.length();i++){
                            JSONObject dataObject = (JSONObject) jsonArray.get(i);
                            User user = new User();
                            user.setId(dataObject.getInt("sno"));
                            String name = dataObject.getString("name");
                            user.setName(name);
                            users.add(user);

                        }
                        mAdapter.notifyDataSetChanged();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        }catch (JSONException e) {
            e.printStackTrace();
        }catch(UnsupportedEncodingException ee){
            ee.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

//    LatLng driverFromLatLong = getLocationFromAddress(getActivity(),driverFrom);
//    LatLng driverToLatLong = getLocationFromAddress(getActivity(),driverDest);
//    LatLng passengerFromLatLong = getLocationFromAddress(getActivity(),passengerFrom);
//    LatLng passengerToLatLong = getLocationFromAddress(getActivity(),passengerTo);
//
//
//
//    double driverTotalDist = calculationByDistance(driverFromLatLong,driverToLatLong);
//    double passengrDistToDriverDest = calculationByDistance(passengerToLatLong,driverToLatLong);
//    double driverFromToPassgrDestDist = calculationByDistance(driverFromLatLong,passengerToLatLong);
//
//    Log.v("driver total", String.valueOf(driverTotalDist));
//    Log.v("passgr total", String.valueOf(passengrDistToDriverDest));
//
//    if ((passengrDistToDriverDest <= driverTotalDist) && (driverTotalDist <= driverFromToPassgrDestDist)){
//        Log.v(LOG_TAG,"display the chosen drivers cheers!");
//    }

//    public LatLng getLocationFromAddress(Context context, String strAddress) {
//
//        Geocoder coder = new Geocoder(context);
//        List<Address> address;
//        LatLng p1 = null;
//
//        try {
//            address = coder.getFromLocationName(strAddress, 5);
//            if (address == null) {
//                return null;
//            }
//            Address location = address.get(0);
//            location.getLatitude();
//            location.getLongitude();
//
//            p1 = new LatLng(location.getLatitude(), location.getLongitude() );
//
//        } catch (Exception ex) {
//
//            ex.printStackTrace();
//        }
//
//        return p1;
//    }
//
//    //  got this method from stackoverflow
//    public double calculationByDistance(LatLng StartP, LatLng EndP) {
//        int Radius = 6371;// radius of earth in Km
//        double lat1 = StartP.latitude;
//        double lat2 = EndP.latitude;
//        double lon1 = StartP.longitude;
//        double lon2 = EndP.longitude;
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
//                + Math.cos(Math.toRadians(lat1))
//                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
//                * Math.sin(dLon / 2);
//        double c = 2 * Math.asin(Math.sqrt(a));
//        double valueResult = Radius * c;
//        double km = valueResult / 1;
//        DecimalFormat newFormat = new DecimalFormat("####");
//        int kmInDec = Integer.valueOf(newFormat.format(km));
//        double meter = valueResult % 1000;
//        int meterInDec = Integer.valueOf(newFormat.format(meter));
//        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
//                + " Meter   " + meterInDec);
//
//        return Radius * c;
//    }



}

