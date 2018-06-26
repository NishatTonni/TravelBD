package com.nwu.nishat.travelguidebd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mainToolbar;
    private List<Place> placeList;
    private PlacesRecyclerAdapter recyclerAdapter;
    private FirebaseFirestore firebaseFirestore;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.nwu.nishat.travelguidebd.R.layout.activity_main);

        // ActionBar Text
        mainToolbar = findViewById(com.nwu.nishat.travelguidebd.R.id.main_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("Top Destinations");

        // Initialize place array list
        placeList = new ArrayList<Place>();

        // Finding the recycler view
        recyclerView = findViewById(com.nwu.nishat.travelguidebd.R.id.placeRecycler);
        // Setting the list in the adapter
        recyclerAdapter = new PlacesRecyclerAdapter(placeList);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Get the fire base instance
        firebaseFirestore = FirebaseFirestore.getInstance();
        // Fetching the place data from Places table
        firebaseFirestore.collection("Places").addSnapshotListener(MainActivity.this, new EventListener<QuerySnapshot>() {
                    // Get the last visible document
                    @Override
                    public void onEvent(QuerySnapshot documentSnapshots, FirebaseFirestoreException e) {
                        // Check if There is any data in the cloud
                        if (!documentSnapshots.isEmpty()) {
                            // Take each data/place
                            for (DocumentChange doc : documentSnapshots.getDocumentChanges()) {
                                // Check if it was added previously
                                if (doc.getType() == DocumentChange.Type.ADDED) {
                                    // Add the place to place list
                                    // Update the recycler adapter
                                    Place place = doc.getDocument().toObject(Place.class);
                                    placeList.add(place);
                                    recyclerAdapter.notifyDataSetChanged();

                                }
                            }
                        }
                    }
                });

    }

}
