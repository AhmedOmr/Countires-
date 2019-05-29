package com.mecodroid.myfirebaseauthgoogle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mecodroid.myfirebaseauthgoogle.Model.Country;

import java.util.ArrayList;

public class CountryadapterList extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<Country> countryList;
    viewHolder holders;

    public CountryadapterList(Context context, ArrayList<Country> countryList) {
        this.context = context;
        this.countryList = countryList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_rowcountry, viewGroup, false);
        return new viewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        holders = (viewHolder) viewHolder;
        holders.txtco.setText(countryList.get(i).getName());
        holders.txtcap.setText(countryList.get(i).getCapital());
        holders.txtreg.setText(countryList.get(i).getRegion());
        holders.txtalpha.setText(countryList.get(i).getAlpha2Code());
        holders.txtpop.setText(String.valueOf(countryList.get(i).getPopulation()));
    }

    @Override
    public int getItemCount() {
        return countryList != null ? countryList.size() : 0;
    }


    class viewHolder extends RecyclerView.ViewHolder {
        TextView txtco, txtcap, txtreg, txtalpha, txtcall, txtpop;
        ImageView vflag;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            txtco = itemView.findViewById(R.id.count);
            txtcap = itemView.findViewById(R.id.capt);
            txtreg = itemView.findViewById(R.id.regio);
            txtalpha = itemView.findViewById(R.id.alphaco);
            txtpop = itemView.findViewById(R.id.poulatio);


        }
    }

}
