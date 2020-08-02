package com.example.trafficticket.ui.home;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controller.TicketListAdapter;
import com.example.model.FineRecordObj;
import com.example.model.GlobalClass;
import com.example.trafficticket.R;
import com.example.trafficticket.ScanImage;
import com.example.trafficticket.UploadImage;
import com.example.utility.FirebaseConnect;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import java.util.ArrayList;

public class HomeFragment extends Fragment {

    //object declarations
    private RecyclerView ticketView;
    private TicketListAdapter mAdapter;
    private String userId = "";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        //getting instance of global activity
        GlobalClass gc = (GlobalClass) getActivity().getApplicationContext();
        userId = gc.getUserid();

       View root = inflater.inflate(R.layout.fragment_home, container, false);

       //getting instance of ui layout
        ticketView = root.findViewById(R.id.ticketList);
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogBox();
            }
        });

        Log.i("id",gc.getUserid());
        new FirebaseConnect().getTicketListData(gc.getUserid(), new FirebaseConnect.TicketListCallback() {
            @Override
            public void setCallback(ArrayList<FineRecordObj> dataList) {
                Log.i("Data", new Gson().toJson(dataList));
                mAdapter = new TicketListAdapter(getActivity().getApplicationContext(), dataList);
                ticketView.setAdapter(mAdapter);
                ticketView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
            }
        });
        return root;
    }

    //dialog box alert method for Scan/Upload of License plate
    public void showDialogBox() {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogCustom);
            builder.setMessage("How would you want to capture the license plate?")
                    .setCancelable(false)
                    .setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getActivity().getApplicationContext(), "Upload Clicked", Toast.LENGTH_SHORT).show();
                            Intent uploadIntent = new Intent(getActivity().getApplicationContext(), UploadImage.class);
                            startActivity(uploadIntent);
                        }
                    })
                    .setNegativeButton("Scan", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(getActivity().getApplicationContext(), "Scan Clicked", Toast.LENGTH_SHORT).show();
                            Intent uploadIntent = new Intent(getActivity().getApplicationContext(), ScanImage.class);
                            startActivity(uploadIntent);
                        }
                    })
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

            TextView textView = (TextView) alert.findViewById(android.R.id.message);
            textView.setTextSize(18);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }



}