package com.techtator.berdie.thankYou;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techtator.berdie.R;

public class ThankYouFragment extends Fragment {

    public ThankYouFragment() {
    }

    public static ThankYouFragment newInstance() {
        ThankYouFragment fragment = new ThankYouFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thank_you, container, false);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }



}
