package com.example.trafficticket.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.trafficticket.R;

public class AboutFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_about, container, false);

        //setting about content in the ui layout
        final TextView textView = root.findViewById(R.id.text_about);
       textView.setText("This is a mobile application which would be responsible for sending traffic ticket to the respective driver for breaking any traffic policy.\n It has a feature of sending email notification to the vehicle owner including penalty details.\n This app will take picture of the Number plate of the vehicle and select the breaking rule and send the respective charge sheet to the owner of the vehicle. In this, we would be using google image recognition binaries for fetching string from a picture.\n" +
               "Ideally, these days some AI cameras are responsible for taking a running picture of the car/number plate in case of traffic speed violation or any other but due to that limitation we have decided to let the user upload the picture or scan from the app itself. \n");

        return root;
    }
}