package com.example.trafficticket;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controller.RuleListAdapter;
import com.example.model.CarMetaData;
import com.example.model.FineRecordObj;
import com.example.model.GlobalClass;
import com.example.model.TrafficRule;
import com.example.utility.FirebaseConnect;
import com.example.utility.SendMail;
import com.example.utility.UtilityClass;
import com.google.gson.Gson;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class FineRecord extends AppCompatActivity {

    // object declarations of layout components
    private TextView numberText, nameText, modelText, stateText;
    private ListView ruleListView;
    private RuleListAdapter mAdapter;
    private Button btnSubmit;
    private boolean twoPane = false;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fine_record);

        // toolbar setting
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed(); // Implemented by activity
            }
        });

        // check for tab view or mobile view
        if (findViewById(R.id.ruleFrame) != null) {
            twoPane = true;
        } else {
            twoPane = false;
        }

        numberText = findViewById(R.id.txt_Number);
        nameText = findViewById(R.id.txt_Name);
        ruleListView = findViewById(R.id.listview);
        btnSubmit = findViewById(R.id.submit);

        //set car details to object from json for further usage
        Intent intent = getIntent();
        String ownerData = intent.getStringExtra("ownerData");
        final CarMetaData cmd = new Gson().fromJson(ownerData, CarMetaData.class);

        final GlobalClass gc = (GlobalClass) getApplicationContext();

        numberText.setText("License Plate - "+cmd.getCar_NUMBER());
        nameText.setText("Vehicle Owner - "+cmd.getOwner_NAME());

        if (!twoPane) {
            // call to get list of rules from firebase
            new FirebaseConnect().getRuleListData(new FirebaseConnect.RuleListCallback() {
                @Override
                public void onCallback(ArrayList<TrafficRule> dataList) {
                    mAdapter = new RuleListAdapter(dataList, getApplicationContext());
                    ruleListView.setAdapter(mAdapter);
                    ruleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        }
                    });
                }
            });

            btnSubmit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    int totalFine = 0;
                    String fineStr = "";
                    // calculate total fine on the basis of selected checkboxes
                    for (int i = 0; i < mAdapter.getCount(); i++) {
                        if (mAdapter.getItem(i).isChecked()) {
                            totalFine = totalFine + Integer.valueOf(mAdapter.getItem(i).getFineamt());
                            fineStr = fineStr + mAdapter.getItem(i).getRule_DESC() + ";";
                        }
                    }
                    if (totalFine == 0) {
                        new UtilityClass().showToast(FineRecord.this,"Please select at least one violation to submit.");
                        // Toast.makeText(getApplicationContext(), "Please select at least one item to submit.", Toast.LENGTH_LONG).show();
                    } else {
                       // Toast.makeText(getApplicationContext(), "Total Fine marked... " + totalFine, Toast.LENGTH_LONG).show();
                        //call to get unique ticket number
                        String ticketNum = new UtilityClass().getTicketId(cmd.getOwner_MAIL(), cmd.getState());
                        //call to insert record to database
                        new FirebaseConnect().insertFineTicket(cmd.getCar_NUMBER(), totalFine, ticketNum, gc.getUsername(), gc.getUserid(), fineStr);

                        // send email to car owner - have to modify to parameters
                        new SendMail(cmd.getOwner_MAIL(), totalFine, cmd.getState(), ticketNum, cmd.getOwner_NAME(), cmd.getCar_NUMBER(), fineStr).execute();

                        Toast.makeText(getApplicationContext(),"Email has been sent to the vehicle owner.",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(FineRecord.this, TicketList.class);// New activity
                        startActivity(intent);
                        finish();
                    }
                }
            });
        } else {
            //fragment loading and set data
            modelText = findViewById(R.id.txt_model);
            modelText.setText(cmd.getCar_MODEL());
            stateText = findViewById(R.id.txt_state);
            stateText.setText(cmd.getState());
            final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.ruleFrame, new FineRecordFragment(cmd));
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

}
