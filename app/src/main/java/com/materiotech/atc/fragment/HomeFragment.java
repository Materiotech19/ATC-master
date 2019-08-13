package com.materiotech.atc.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.materiotech.atc.adapter.HomeFragmentAdapter;
import com.materiotech.atc.model.Home;
import com.materiotech.atc.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    public View view;
    private RecyclerView recyclerView_Home;
    private ArrayList<Home> list_Home;
    private HomeFragmentAdapter homeFragmentAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getList();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        getWidget();
        return view;
    }


    private void getWidget(){
        recyclerView_Home = (RecyclerView)view.findViewById(R.id.recycler_view);

        homeFragmentAdapter = new HomeFragmentAdapter(getActivity(),list_Home);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView_Home.setLayoutManager(mLayoutManager);
        recyclerView_Home.setAdapter(homeFragmentAdapter);
    }

    private void getList(){
        list_Home= new ArrayList<>();
        list_Home.clear();

        list_Home.add(new Home("1","About Us"));
        list_Home.add(new Home("2","Services"));
        list_Home.add(new Home("3","Kids & Youth"));
        list_Home.add(new Home("4","Contacts"));

    }

}
