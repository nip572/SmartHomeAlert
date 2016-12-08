package com.example.nipun.smarthomealert;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.*;
import android.location.Location;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static android.media.CamcorderProfile.get;
import static com.example.nipun.smarthomealert.AdapterIngredientsList.groceryList;

/**
 * Created by Nipun on 12/5/16.
 */

public class AdapterNearbyGroceryStores extends BaseAdapter {

    Context mContext;
    List<Result> resultList;
    TextView tvName;
    TextView tvAddress;
    TextView buttonDrive;
    String latitude;
    String longitude;



    public AdapterNearbyGroceryStores(Context mContext , List<Result> rl ) {

        this.mContext = mContext;
        this.resultList= rl;

    }
    @Override
    public int getCount() {
        return resultList.size();
    }

    @Override
    public Object getItem(int position) {
        return resultList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = View.inflate(mContext, R.layout.nearby_item, null);
        tvName = (TextView) v.findViewById(R.id.nearby_name);
        tvAddress = (TextView) v.findViewById(R.id.nearby_address);
        buttonDrive = (TextView) v.findViewById(R.id.nearby_button);

        Location location = resultList.get(0).getGeometry().getLocation();
        Log.d(location.getLatitude()+"", "getView: ");
        Double LatDouble = resultList.get(position).getGeometry().getLocation().getLatitude();
        Double LongDouble = resultList.get(position).getGeometry().getLocation().getLongitude();
        latitude = LatDouble.toString();
        longitude= LongDouble.toString();



        tvName.setText(resultList.get(position).getName());
        tvAddress.setText(resultList.get(position).getVicinity());
        buttonDrive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d(latitude + " "+ longitude, "onClick: DRIVE");
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                mapIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                mContext.startActivity(mapIntent);


            }
        });


        return v;
    }

}
