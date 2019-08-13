package com.materiotech.atc.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.materiotech.atc.R;
import com.materiotech.atc.model.Home;

import java.util.ArrayList;

public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.MyViewHolder> {


    private Context context;
    private ArrayList<Home> list_home = new ArrayList<>();

    public HomeFragmentAdapter(Context mContext, ArrayList<Home> mList_Home){

        this.context= mContext;
        this.list_home = mList_Home;
    }

    @NonNull
    @Override
    public HomeFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout_home, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeFragmentAdapter.MyViewHolder myViewHolder, int i) {
        final Home model = list_home.get(i);

        myViewHolder.txtTitle.setText(model.getName());
    }

    @Override
    public int getItemCount() {
        return (null != list_home ? list_home.size() : 0);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.textView);

        }
    }
}
