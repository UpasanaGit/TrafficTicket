package com.example.trafficticket.ui.logout;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.example.trafficticket.LogIn;
public class LogoutFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //Navigating to login screen when click on logout option in navigation bar
       Intent logout = new Intent(getActivity().getApplicationContext(), LogIn.class);
       startActivity(logout);
       getActivity().finish();
        return null;
    }
}