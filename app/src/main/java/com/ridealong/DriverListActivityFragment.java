package com.ridealong;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceActivity;
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
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;
import com.ridealong.Modules.DriverListAdapter;
import com.ridealong.Modules.PassengerListAdapter;
import com.ridealong.models.DriverDetails;
import com.ridealong.models.PassengerDetails;
import com.ridealong.models.ServerRequest;
import com.ridealong.models.ServerResponse;
import com.ridealong.models.User;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


import cz.msebera.android.httpclient.entity.StringEntity;

/**
 * A placeholder fragment containing a simple view.
 */
public class DriverListActivityFragment extends Fragment {

    private static final String LOG_TAG = DriverListActivityFragment.class.getSimpleName();

    public DriverListActivityFragment() {
    }

    ArrayAdapter adapter;
    List<String> driverListView = new ArrayList<String>();
    String passengerFrom;
    String passengerTo,leavingDate;
    private DriverListAdapter driverListAdapter;
    private RecyclerView recyclerView;
    ArrayList<User> users = new ArrayList<User>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_driver_list, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.driver_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        driverListAdapter = new DriverListAdapter(users,getActivity());
        recyclerView.setAdapter(driverListAdapter);
        driverListAdapter.notifyDataSetChanged();

        addDriverLists();


        return view;
    }

    public void addDriverLists(){

        Log.i(LOG_TAG,"in add add Driver fn");

        passengerFrom = getActivity().getIntent().getExtras().getString("startPt");
        passengerTo = getActivity().getIntent().getExtras().getString("destPt");
        leavingDate = getActivity().getIntent().getExtras().getString("date");


        Log.v("passgr from",passengerFrom);
        Log.v("passgr to",passengerTo);

        getDriverListUsingFrmAndTo();

//        getDriverListUsingFrm();

    }

    public void getDriverListUsingFrmAndTo(){

        try {
            JSONObject obj = new JSONObject();
            obj.put("passgrFrom", passengerFrom);
            obj.put("passgrTo",passengerTo);
            obj.put("leavingDate",leavingDate);
            String jsonString = obj.toString();
            StringEntity stringEntity = new StringEntity(jsonString);
            stringEntity.setContentType("application/json");
            stringEntity.setContentEncoding("UTF-8");

            AsyncHttpClient client = new AsyncHttpClient();

            client.post(this.getActivity(), "http://www.ridealong.lewebev.com/driver_list.php", stringEntity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseString, Throwable throwable) {
                    Log.v(LOG_TAG,"response failure");

                }


                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseString) {
                    Log.v("response Str",responseString.toString());
                    try {
                        String jsonStr = new String(responseString,"UTF-8");
                        Log.v("json str",jsonStr);
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        String driverListStr = jsonObject.getString("driverList");
                        JSONArray jsonArray = new JSONArray(driverListStr);
                        Log.v("jsonArr len",String.valueOf(jsonArray.length()));
                        if(jsonArray.length() == 0){
                            Toast.makeText(getActivity(),"No Drivers availble", Toast.LENGTH_SHORT).show();

                        }
                        for(int i= 0;i<jsonArray.length();i++){
                            JSONObject dataObject = (JSONObject) jsonArray.get(i);
                            User user = new User();
                            user.setId(dataObject.getInt("sno"));
                            user.setName(dataObject.getString("name"));
                            users.add(user);

                        }
                        driverListAdapter.notifyDataSetChanged();
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


    List<DriverDetails> chosenDriversList = new ArrayList<DriverDetails>();
    List<DriverDetails> driverServerDetails;

    public void getDriverListUsingFrm(){




        try{
            JSONObject jsonObjectFrom = new JSONObject();
            jsonObjectFrom.put("passgrOnlyFrm",passengerFrom);
            String jsonFrmStr = jsonObjectFrom.toString();
            StringEntity stringEntity = new StringEntity(jsonFrmStr);
            stringEntity.setContentType("application/json");
            stringEntity.setContentEncoding("UTF-8");

            AsyncHttpClient client = new AsyncHttpClient();

            client.post(this.getActivity(), "http://www.ridealong.lewebev.com/driver_list_from.php", stringEntity, "application/json", new AsyncHttpResponseHandler() {
                @Override
                public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseString, Throwable throwable) {
                    Log.v(LOG_TAG,"error in fn drlstusfrm");
                }

                @Override
                public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseString) {
                    Log.v("response Str",responseString.toString());
                    try {
                        String jsonStr = new String(responseString, "UTF-8");
                        Log.v("json str from", jsonStr);
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        String driverListStr = jsonObject.getString("driverListFrom");
                        JSONArray jsonArray = new JSONArray(driverListStr);
                        Log.v("jsonArr from len", String.valueOf(jsonArray.length()));
                        driverServerDetails = new ArrayList<DriverDetails>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject dataObject = (JSONObject) jsonArray.get(i);
                            int id = dataObject.getInt("id");
                            DriverDetails driverDetails = new DriverDetails();
                            driverDetails.setId(id);
                            driverDetails.setfrom_place(dataObject.getString("from_place").toUpperCase());
                            driverDetails.setDestination(dataObject.getString("destination").toUpperCase());
                            driverDetails.setUserId(dataObject.getInt("userid"));

                            driverServerDetails.add(driverDetails);
                        }



                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
            getCalculatedChosenDriversList();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void getCalculatedChosenDriversList(){

        Log.v("drserverlst",String.valueOf(driverServerDetails.size()));

        for(DriverDetails driverDetails : driverServerDetails){
            LatLng driverFromLatLong = getLocationFromAddress(getActivity(),driverDetails.getfrom_place().toUpperCase());
            LatLng driverToLatLong = getLocationFromAddress(getActivity(),driverDetails.getDestination().toUpperCase());
            LatLng passengerFromLatLong = getLocationFromAddress(getActivity(),passengerFrom.toUpperCase());
            LatLng passengerToLatLong = getLocationFromAddress(getActivity(),passengerTo.toUpperCase());

            double driverTotalDist = calculationByDistance(driverFromLatLong,driverToLatLong);
            double passgrTotalDist = calculationByDistance(passengerFromLatLong,passengerToLatLong);
            double passengrDistToDriverDest = calculationByDistance(passengerToLatLong,driverToLatLong);
            double driverFromToPassgrDestDist = calculationByDistance(driverFromLatLong,passengerToLatLong);

            Log.v("driver total", String.valueOf(driverTotalDist));
            Log.v("passgr total", String.valueOf(passengrDistToDriverDest));
            Log.v("passgr dist to dr dest",String.valueOf(passengrDistToDriverDest));

            if ((passgrTotalDist <= driverTotalDist) && (driverTotalDist >= passengrDistToDriverDest)){
                chosenDriversList.add(driverDetails);
            }
        }


        Log.v("chosenDriverLst",String.valueOf(chosenDriversList.size()));
        getChosenDriverList(chosenDriversList);

    }



    public void getChosenDriverList(List<DriverDetails> driverList){
        Log.d("in calc fn",String.valueOf(driverList.size()));

        for(DriverDetails details : driverList){

            try {
                JSONObject jsonObjectFrom = new JSONObject();
                JSONObject obj = new JSONObject();
                jsonObjectFrom.put("driverUserId",details.getUserId());
                String jsonString = jsonObjectFrom.toString();
                StringEntity stringEntity = new StringEntity(jsonString);
                stringEntity.setContentType("application/json");
                stringEntity.setContentEncoding("UTF-8");


                stringEntity.setContentType("application/json");
                stringEntity.setContentEncoding("UTF-8");

                AsyncHttpClient client = new AsyncHttpClient();

                client.post(this.getActivity(), "http://www.ridealong.lewebev.com/userDetailsDriver.php", stringEntity, "application/json", new AsyncHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseString, Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, byte[] responseString) {
                        Log.d("response Str",responseString.toString());
                        try {
                            String jsonStr = new String(responseString,"UTF-8");
                            Log.v("json str",jsonStr);
                            JSONObject jsonObject = new JSONObject(jsonStr);
                            String driverListStr = jsonObject.getString("driverDetails");
                            JSONArray jsonArray = new JSONArray(driverListStr);
                            Log.v("jsonArr len",String.valueOf(jsonArray.length()));
                            for(int i= 0;i<jsonArray.length();i++){
                                JSONObject dataObject = (JSONObject) jsonArray.get(i);
                                User user = new User();
                                user.setId(dataObject.getInt("sno"));
                                user.setName(dataObject.getString("name"));
                                users.add(user);

                            }
                            driverListAdapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                });

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }


        }


}


    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (Exception ex) {

            ex.printStackTrace();
        }

        return p1;
    }

//  got this method from stackoverflow
    public double calculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
//        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
//                + " Meter   " + meterInDec);

        return Radius * c;
    }


}
