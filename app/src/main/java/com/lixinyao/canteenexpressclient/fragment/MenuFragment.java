package com.lixinyao.canteenexpressclient.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.lixinyao.canteenexpressclient.R;

public class MenuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
      View view=inflater.inflate(R.layout.fragment_menu,container,false);
      return view;
    }
}
