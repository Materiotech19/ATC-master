package com.materiotech.atc.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.materiotech.atc.R;

public class InviteFriendFragment extends Fragment {

    public View view;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_refer_it,container,false);
        getWidget();
        return view;
    }

    public void getWidget(){
        onInviteClicked();
    }

    public void onInviteClicked(){

    }
}
