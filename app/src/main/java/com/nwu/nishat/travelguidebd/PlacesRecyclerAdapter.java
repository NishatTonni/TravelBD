package com.nwu.nishat.travelguidebd;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.firestore.GeoPoint;
import com.yayandroid.parallaxrecyclerview.ParallaxViewHolder;

import java.util.List;

import static android.support.v4.content.ContextCompat.startActivity;

public class PlacesRecyclerAdapter extends RecyclerView.Adapter<PlacesRecyclerAdapter.ViewHolder> {

    private List<Place> placeList;
    private Context context;
    private Place place;

    public PlacesRecyclerAdapter(List<Place> placeList){
        this.placeList = placeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(com.nwu.nishat.travelguidebd.R.layout.list_item, parent, false);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.setIsRecyclable(false);
        final String nameData = placeList.get(position).getName();
        holder.setName(nameData);

        final String image = placeList.get(position).getImage();
        holder.setImage(image);

        final GeoPoint location = placeList.get(position).getLocation();

        holder.getBackgroundImage().reuse();

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String des = placeList.get(position).getDescription();

                Intent intent = new Intent(view.getContext() , PlaceDetails.class);
                intent.putExtra("name", nameData);
                intent.putExtra("description", des);
                intent.putExtra("image", image);
                intent.putExtra("lat", location.getLatitude());
                intent.putExtra("long", location.getLongitude());

                view.getContext().startActivity(intent);
            }
        });

    }



    @Override
    public int getItemCount() {
        return placeList.size();
    }

    public class ViewHolder extends ParallaxViewHolder{

        private TextView nameView;
        private View mView;
        private ImageView imageView;
        private ConstraintLayout parentLayout;

        @Override
        public int getParallaxImageId() {
            return R.id.place_image;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            parentLayout = itemView.findViewById(com.nwu.nishat.travelguidebd.R.id.parent_layout);
        }

        private void setName(String name){
            nameView = mView.findViewById(com.nwu.nishat.travelguidebd.R.id.place_name);
            nameView.setText(name);
        }

        private void setImage(final String imageUrl){
            imageView = mView.findViewById(com.nwu.nishat.travelguidebd.R.id.place_image);


                            RequestOptions options = new RequestOptions()
                                    .format(DecodeFormat.PREFER_RGB_565)
                                    .centerCrop()
                                    .override(600, 200)
                                    .placeholder(R.drawable.placeholder)
                                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);

                            Glide.with(context)
                                    .load(imageUrl)
                                    .apply(options)
                                    .into(imageView);

                }
            }


        }
