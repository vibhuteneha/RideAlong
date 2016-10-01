package com.ridealong;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

import com.ridealong.models.PassengerDetails;
import com.ridealong.models.ServerRequest;
import com.ridealong.models.ServerResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class PassengerActivityFragment extends Fragment implements View.OnClickListener {


    private EditText fromCity, toCity, leavingDate;
    Date lDate = new Date();
    private Button submitBtn;
    private DatePicker datePicker;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private int userId;

    private static final String LOG_TAG = PassengerActivityFragment.class.getSimpleName();

    public PassengerActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_passenger, container, false);
        submitBtn = (Button) rootView.findViewById(R.id.pbutton);
        datePicker = (DatePicker) rootView.findViewById(R.id.datepicker);
        fromCity = (EditText) rootView.findViewById(R.id.pfrom);
        toCity = (EditText) rootView.findViewById(R.id.pto);
        leavingDate = (EditText) rootView.findViewById(R.id.pdate);

        submitBtn.setOnClickListener(this);

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

        userId = getActivity().getIntent().getExtras().getInt("userId");

        return rootView;
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

        String fcity = fromCity.getText().toString();
        String tcity = toCity.getText().toString();
        String date = leavingDate.getText().toString();



        if(!fcity.isEmpty() && !tcity.isEmpty() && !date.isEmpty()){
            insertPassgrTravelInfo(fcity,tcity,date);
            Intent passgrIntent = new Intent(getActivity(),DriverListActivity.class);
            passgrIntent.putExtra("startPt",fcity);
            passgrIntent.putExtra("destPt",tcity);
            passgrIntent.putExtra("date",date);
            startActivity(passgrIntent);
        }else{
            Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
        }

    }

    private void insertPassgrTravelInfo(String startCity, String destination,String ldate) {
        Log.v("start", startCity);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);



        PassengerDetails passengerDetails = new PassengerDetails();
        passengerDetails.setFrom(startCity);
        passengerDetails.setUserId(userId);
        passengerDetails.setDestination(destination);
        passengerDetails.setLeavingDate(ldate);

        Log.v("driver details--", passengerDetails.getDestination());
        ServerRequest serverRequest = new ServerRequest();
        serverRequest.setOperation(Constants.PASSENGER_TRAVEL_DETAILS_OPERATION);
        serverRequest.setPassengerDetails(passengerDetails);
        Log.v("server==", serverRequest.toString());
        Call<ServerResponse> responseCall = requestInterface.operation(serverRequest);
        Log.v("responseCall==", responseCall.toString());
        responseCall.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                ServerResponse resp = response.body();
                Log.d(Constants.TAG, "passgractfragsuccess");
                //Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {
                Log.d(Constants.TAG, "passgractfragfailed");
              //  Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
            }
        });


    }


}
