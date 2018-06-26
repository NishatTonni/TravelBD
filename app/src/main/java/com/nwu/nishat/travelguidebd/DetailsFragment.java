package com.nwu.nishat.travelguidebd;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.nwu.nishat.travelguidebd.R;

import static java.security.AccessController.getContext;

public class DetailsFragment extends Fragment {
    int color;
    public String placeName;
    public String placeDescription;

    public TextView placeNameTextView;
    public TextView descriptionTextView;

    public DetailsFragment() {
    }

    @SuppressLint("ValidFragment")
    public DetailsFragment(int color) {
        this.color = color;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);


        placeNameTextView = view.findViewById(R.id.place_name_text_view);
        descriptionTextView = view.findViewById(R.id.description_place_text_view);

        if(getArguments() != null){
            placeName = getArguments().getString("name");
            placeDescription = getArguments().getString("description");

            placeNameTextView.setText(placeName);
            descriptionTextView.setText(placeDescription);
        }

        return view;
    }
}