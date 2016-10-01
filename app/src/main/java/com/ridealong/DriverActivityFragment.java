package com.ridealong;




import android.app.DatePickerDialog;

import android.content.Context;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.ridealong.models.DriverDetails;
import com.ridealong.models.ServerRequest;
import com.ridealong.models.ServerResponse;
import com.ridealong.models.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class DriverActivityFragment extends Fragment implements View.OnClickListener {

    private Button submitBtn;
    private int userId;
    private int userId1;
    private EditText driverFrom, driverTo, carModel, license, leavingDate;
    Date lDate = new Date();


    private DatePicker datePicker;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private static final String LOG_TAG = DriverActivityFragment.class.getSimpleName();




    public DriverActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_driver, container, false);
        driverFrom = (EditText) view.findViewById(R.id.dfrom);
        driverTo = (EditText) view.findViewById(R.id.dto);
        carModel = (EditText) view.findViewById(R.id.dmodel);
        license = (EditText) view.findViewById(R.id.dlicense);
        leavingDate = (EditText) view.findViewById(R.id.ddate);
        datePicker = (DatePicker) view.findViewById(R.id.datepicker1);

        submitBtn = (Button) view.findViewById(R.id.dbutton);
        submitBtn.setOnClickListener(this);

        userId = getActivity().getIntent().getExtras().getInt("userId");
        Log.v("user Id in driver",String.valueOf(userId));
        dateFormatter = new SimpleDateFormat("MM-dd-yyyy", Locale.US);

        leavingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        Calendar newCalendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                leavingDate.setText(dateFormatter.format(newDate.getTime()));


            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        return view;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {



            Log.d("MainActivity","onDateSet called");
//            String year1 = String.valueOf(selectedYear);
//            String month1 = String.valueOf(selectedMonth + 1);
//            String day1 = String.valueOf(selectedDay);
//            TextView tvDt = (TextView) findViewById(R.id.tvDate);
//            tvDt.setText(day1 + "/" + month1 + "/" + year1);
        }
    };


    @Override
    public void onClick(View v) {

       String driverStartPlc = driverFrom.getText().toString();
       String driverDestPlc = driverTo.getText().toString();
       String driverCarModel = carModel.getText().toString();
       String driverLicense = license.getText().toString();
       String date =  leavingDate.getText().toString();

       if(!driverStartPlc.isEmpty() && !driverDestPlc.isEmpty() && !driverCarModel.isEmpty() && !driverLicense.isEmpty() && !date.isEmpty()){
           insertDriverInfo(driverStartPlc,driverDestPlc,driverCarModel,driverLicense,date);

           Intent driverIntent = new Intent(getActivity(),PassengerListActivity.class);
           driverIntent.putExtra("drStartPt",driverStartPlc);
           driverIntent.putExtra("drDestPt",driverDestPlc);
           driverIntent.putExtra("drDate",date);
           startActivity(driverIntent);
       }else{
           Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
       }
    }


    private void insertDriverInfo(String startPlc, String destPlc, String carModel, String license, String ldate) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);



        DriverDetails driverDetails = new DriverDetails();
        driverDetails.setUserId(userId);
        driverDetails.setcar_no(license);
        driverDetails.setCarModel(carModel);

        driverDetails.setfrom_place(startPlc);
        driverDetails.setDestination(destPlc);
        driverDetails.setLeavingDate(ldate);
        ServerRequest serverRequest = new ServerRequest();
        serverRequest.setOperation(Constants.DRIVER_TRAVEL_DETAILS_OPERATION);
        serverRequest.setDriverDetails(driverDetails);
        Log.v("server==", serverRequest.toString());
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);
        Log.v("responseCall==", responseCall.toString());
        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Log.v(LOG_TAG,"success");

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(LOG_TAG, "failed");
               // Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });

    }
}
