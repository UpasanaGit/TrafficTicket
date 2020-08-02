package com.example.trafficticket;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.controller.RuleListAdapter;
import com.example.model.CarMetaData;
import com.example.model.GlobalClass;
import com.example.model.TrafficRule;
import com.example.utility.FirebaseConnect;
import com.example.utility.SendMail;
import com.example.utility.UtilityClass;

import java.util.ArrayList;

public class FineRecordFragment extends Fragment {

    //object declarations
    private RuleListAdapter mAdapter;
    private CarMetaData cmData;

    //constructor to accept car data
    public FineRecordFragment(CarMetaData cmd) {
        this.cmData = cmd;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup ruleRoot = (ViewGroup) inflater.inflate(R.layout.fragment_fine_record, container, false);
        final ListView ruleListView = ruleRoot.findViewById(R.id.listview);
        Button btnSubmit = ruleRoot.findViewById(R.id.submit);

        final GlobalClass gc = (GlobalClass) getActivity().getApplicationContext();

        //method call to get rule list from database
        new FirebaseConnect().getRuleListData(new FirebaseConnect.RuleListCallback() {
            @Override
            public void onCallback(ArrayList<TrafficRule> dataList) {
                mAdapter = new RuleListAdapter(dataList, getActivity().getApplicationContext());
                ruleListView.setAdapter(mAdapter);
                ruleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Toast.makeText(getActivity().getApplicationContext(), "Item Clicked..." + position, Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int totalFine = 0;
                String fineStr="";
                // calculate total fine on the basis of selected checkboxes
                for (int i = 0; i < mAdapter.getCount(); i++) {
                    if (mAdapter.getItem(i).isChecked()) {
                        totalFine = totalFine + Integer.valueOf(mAdapter.getItem(i).getFineamt());
                        fineStr = fineStr + mAdapter.getItem(i).getRule_DESC()+";";
                    }
                }

                if (totalFine == 0) {
                    new UtilityClass().showToast(getActivity(),"Please select at least one violation to submit.");

                } else {

                    //call to get unique ticket number
                    String ticketNum = new UtilityClass().getTicketId(cmData.getOwner_MAIL(), cmData.getState());
                    //call to insert ticket fine to database
                    new FirebaseConnect().insertFineTicket(cmData.getCar_NUMBER(), totalFine, ticketNum, gc.getUsername(),gc.getUserid(),fineStr);

                    // send email to car owner
                    new SendMail(cmData.getOwner_MAIL(), totalFine, cmData.getState(), ticketNum, cmData.getOwner_NAME(), cmData.getCar_NUMBER(),fineStr).execute();

                    Toast.makeText(getActivity().getApplicationContext(),"Email has been sent to the vehicle owner.",Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(getActivity().getApplicationContext(), TicketList.class);// New activity
                    startActivity(intent);

                }
            }
        });

        return ruleRoot;
    }
}
