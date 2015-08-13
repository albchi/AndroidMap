package com.example.aceral.gmap;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {

        LatLng disneySevenLagoon = new LatLng(28.410067, -81.583699);
        LatLng disneyMagicKingdom = new LatLng(28.418971, -81.581436);


        /*map.addMarker(new MarkerOptions()
                // .position(new LatLng(0,0))
                .position(disneySevenLagoon)
                .title("Marker"));
*/


        LatLngBounds.Builder bounds;

        bounds = new LatLngBounds.Builder();

        map.addMarker(new MarkerOptions()
                // .position(new LatLng(0,0))
                .position(disneySevenLagoon)
                .title("Lagoon"));
        bounds.include(new LatLng(28.410067, -81.583699));

        map.addMarker(new MarkerOptions()
                // .position(new LatLng(0,0))
                .position(disneyMagicKingdom)
                .title("Kingdom"));
        bounds.include(new LatLng(28.418971, -81.581436));


        map.moveCamera(CameraUpdateFactory.newLatLng(disneySevenLagoon));
        // CameraUpdate cU = CameraUpdateFactory.newLatLngBounds(bounds.build(), 100, 100, 50);

        //map.moveCamera(cU);

    } // onMapReady



   public void funcButtonMark(View view) {
       Toast.makeText(this, "Marking!", Toast.LENGTH_SHORT).show();
       Log.d("XAC", "Marking");

   }


   public void funcButtonPic(View view) {
       Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();
       Log.d("XAC", "Click!");

   }


    // Add a marker in Sydney, Australia, and move the camera.
        // LatLng sydney = new LatLng(-34, 151);
        // map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // map.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // another marker from the book
        /*
        LatLng disneyMagicKingdom = new LatLng(28.418971, -81.581436);
        LatLng disneySevenLagoon = new LatLng(28.410067, -81.583699);
        */
        /*
        MarkerOptions markerOptions = new MarkerOptions()
                .draggable(false)
                .flat(false)
                .position(disneyMagicKingdom)
                .title("Magic Kingdom")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        map.addMarker(markerOptions);
        */
        /*
        Marker marker1 = map.addMarker(new MarkerOptions() .position(new LatLng(10,10)) .title("Hello World"));
        Marker marker2 = map.addMarker(new MarkerOptions() .position(new LatLng(28.410067, -81.583699)) .title("Black Lagoon was filed here"));

        LatLngBounds.Builder builder = new LatLngBounds.Builder(); // multi mark
        //for (Marker marker : map) {// multi mar
        builder.include(marker1.getPosition());// multi mar
        builder.include(marker2.getPosition());// multi mar

        //builder.include(disneyMagicKingdom);// multi mar

       // }// multi mark
        LatLngBounds bounds = builder.build();
        int padding = 0;

        // CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        //map.moveCamera(cu);
        map.moveCamera(CameraUpdateFactory.newLatLng(disneyMagicKingdom));





    }
*/
} // MapsActivity