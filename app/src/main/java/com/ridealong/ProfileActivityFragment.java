package com.ridealong;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfileActivityFragment extends Fragment {

    TextView firstname, lastname, email;
    public ProfileActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        firstname = (TextView) view.findViewById(R.id.firstname);
        lastname = (TextView) view.findViewById(R.id.lastname);
        email = (TextView) view.findViewById(R.id.email);
        return view;
    }
}
