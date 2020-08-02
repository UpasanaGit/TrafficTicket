package com.example.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.model.FineRecordObj;
import com.example.trafficticket.R;

import java.util.ArrayList;

// adapter class to add traffic ticket in list item in TicketList activity using RecyclerView
public class TicketListAdapter extends
        RecyclerView.Adapter<TicketListAdapter.TicketViewHolder> {
    private final ArrayList<FineRecordObj> mTicketList;
    private LayoutInflater mInflater;

    /* constructor to accept two input parameters
     * ticketList - ArrayList<FineRecordObj> of ticket id's fetched from firebase database
     * mContext  - application context of activity
     */
    public TicketListAdapter(Context context, ArrayList<FineRecordObj> ticketList) {
        mInflater = LayoutInflater.from(context);
        this.mTicketList = ticketList;
    }

    // inflate the view of a ticket item using the layout
    @NonNull
    @Override
    public TicketListAdapter.TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.ticket_item, parent, false);
        return new TicketViewHolder(mItemView, this);
    }

    // holder method to set the values on the layout components
    @Override
    public void onBindViewHolder(@NonNull TicketListAdapter.TicketViewHolder holder, int position) {
        String mCurrent = mTicketList.get(position).getFine_TICKETID();
        holder.wordItemView.setText(mCurrent  + " - $" + mTicketList.get(position).getFine_AMT());
        holder.txtStatus.setText(mTicketList.get(position).getStatus() +" - "+ mTicketList.get(position).getFine_DATE());
        holder.txtDetail.setText("License Plate - "+mTicketList.get(position).getCar_NUMBER());
    }

    // return item count
    @Override
    public int getItemCount() {
        return mTicketList.size();
    }

    // inner class to get instance of the layout components to set in the adapter
    class TicketViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView wordItemView, txtStatus,txtDetail;
        final TicketListAdapter mAdapter;

        public TicketViewHolder(View itemView, TicketListAdapter adapter) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.ticket);
            txtStatus = itemView.findViewById(R.id.txt_status);
            txtDetail = itemView.findViewById(R.id.txt_detail);
            this.mAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
