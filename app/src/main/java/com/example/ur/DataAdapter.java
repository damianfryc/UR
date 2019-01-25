package com.example.ur;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.MyViewHolder> {


    private ArrayList<String> dataZjazduList = new ArrayList<>();
    private ArrayList<String> planZjazduList = new ArrayList<>();
    private Activity mActivity;

    public DataAdapter(MainActivity activity, ArrayList<String> dataZjazduList,  ArrayList<String> planZjazduList) {
        this.mActivity = activity;
        this.dataZjazduList = dataZjazduList;
        this.planZjazduList = planZjazduList;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView data_zjazdu, plan_zjazdu;

        public MyViewHolder(View view) {
            super(view);
            data_zjazdu = (TextView) view.findViewById(R.id.data_zjazdu);
            plan_zjazdu = (TextView) view.findViewById(R.id.plan_zjazdu);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_data, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.data_zjazdu.setText(dataZjazduList.get(position));
        holder.plan_zjazdu.setText(planZjazduList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataZjazduList.size();
    }
}