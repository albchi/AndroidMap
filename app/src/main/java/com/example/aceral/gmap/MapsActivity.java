package com.example.aceral.gmap;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private TextView mLocationView;
    private LocationManager mManager; // gps
    private Location mCurrentLocation; // gps

    // Location [] arrLoc; // 1/3 declare
    LatLng [] arrLatLng; // 1/3  = new LatLng(28.410067, -81.583699);



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // arrLoc = new Location[100]; // 2/3 instantiate
        arrLatLng = new LatLng[30]; // 2/3  = new LatLng(28.410067, -81.583699);
        for(int i=0; i<30;i++) {
            // arrLoc[i]  = new Location(); // 3/3 initialize
            arrLatLng[i] = new LatLng(0,0); // 3/3 - forced to
        }
        // check to see if GPS is even in the damn tablet/phone

        PackageManager packageManager = this.getPackageManager();
        boolean hasGPS = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
        if(hasGPS)
            Toast.makeText(this, "GPS detected!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "GPS NOT detected!", Toast.LENGTH_SHORT).show();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); // gps
        // mLocationView = new TextView(this);
        mLocationView = (TextView)findViewById(R.id.mLocationView);
        mLocationView.setText("Hello from inside the app");
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
        map.setMyLocationEnabled(true);

        // map.moveCamera(CameraUpdateFactory.newLatLng(disneySevenLagoon)); // disneySevenLagoon is type LatLng
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude())));
        // CameraUpdate cU = CameraUpdateFactory.newLatLngBounds(bounds.build(), 100, 100, 50);

        //map.moveCamera(cU);

    } // onMapReady



   public void funcButtonMark(View view) {
       // Toast.makeText(this, "Marking!", Toast.LENGTH_SHORT).show();
       Log.d("XAC", "Marking");
       mCurrentLocation = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       String foo =  String.format("Your Location:\n%.2f, %.2f", mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
       Toast.makeText(this, "Marking! " + foo, Toast.LENGTH_LONG).show();
   }



   public void funcButtonPic(View view) {
       Toast.makeText(this, "Click!", Toast.LENGTH_SHORT).show();
       Log.d("XAC", "Click!");

   }

    @Override
    protected void onResume() {
        super.onResume();
        if(!mManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //Ask the user to enable GPS
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Location Manager");
            builder.setMessage(
                    "We would like to use your location, "
                            + "but GPS is currently disabled.\n"
                            + "Would you like to change these settings "
                            + "now?");
            builder.setPositiveButton("Yes",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            //Launch settings, allowing user to change
                            Intent i = new Intent(Settings
                                    .ACTION_LOCATION_SOURCE_SETTINGS);
                            startActivity(i);
                        }
                    });
            builder.setNegativeButton("No",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            //No location service, no Activity
                            finish();
                        }
                    });
            builder.create().show();
        }
        //Get a cached location, if it exists
        mCurrentLocation = mManager.getLastKnownLocation(
                LocationManager.GPS_PROVIDER);
        updateDisplay();
        //Register for updates
        int minTime = 5000;
        float minDistance = 0;
        mManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime,
                minDistance,
                mListener);
    } // onResume

    @Override
    protected void onPause() {
        super.onPause();
        mManager.removeUpdates(mListener);
    }

    private void updateDisplay() {
        if(mCurrentLocation == null) {
            mLocationView.setText("Determining Your Location...");
        } else {
            mLocationView.setText(
                    String.format("Your Location:\n%.2f, %.2f",
                            mCurrentLocation.getLatitude(),
                            mCurrentLocation.getLongitude()));
        }
        // Map tmp;
        // tmp = Map.getMapAsync();
        // map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude())));

    } // updateDisplay

    private LocationListener mListener = new LocationListener() {
        //New location event
        @Override
        public void onLocationChanged(Location location) {
            mCurrentLocation = location;
            Toast.makeText(MapsActivity.this, "GPS invoked onLocationChanged!", Toast.LENGTH_SHORT).show();
            updateDisplay();
        }

        //The requested provider was disabled in settings
        @Override
        public void onProviderDisabled(String provider) { }

        //The requested provider was enabled in settings
        @Override
        public void onProviderEnabled(String provider) { }

        //Availability changes in the requested provider
        @Override
        public void onStatusChanged(String provider,
                                    int status, Bundle extras) { }

    };


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