package com.example.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.model.TrafficRule;
import com.example.trafficticket.R;

import java.util.ArrayList;

// adapter class to add traffic rule in list item in FineRecord activity using ArrayAdapter
public class RuleListAdapter extends ArrayAdapter<TrafficRule> {

    private TextView ruleStr, ruleFine;
    private CheckBox chkRule;

    /* constructor to accept two input parameters
    * ruleList - ArrayList<TrafficRule> of rules fetched from firebase database
    * mContext  - application context of activity
     */
    public RuleListAdapter(ArrayList<TrafficRule> ruleList, Context mContext) {
        super(mContext, R.layout.rule_list_item, ruleList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final TrafficRule ruleRow = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rule_list_item, parent, false);
        }
        // Lookup view for data population
        ruleStr = (TextView) convertView.findViewById(R.id.rowTextView);
        ruleFine = (TextView) convertView.findViewById(R.id.rowTextFine);
        chkRule = (CheckBox) convertView.findViewById(R.id.rowCheckBox);

        // Populate the data into the template view using the data object
        ruleStr.setText(ruleRow.getRule_DESC());
        ruleFine.setText(String.valueOf(ruleRow.getFineamt()));
        // Return the completed view to render on screen

        chkRule.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Populate the listInfo with check box status
                ruleRow.setChecked(isChecked);
            }
        });
        return convertView;
    }
}
