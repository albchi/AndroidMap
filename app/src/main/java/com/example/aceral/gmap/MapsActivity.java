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

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private TextView mLocationView;
    private LocationManager mManager; // gps
    private Location mCurrentLocation; // gps
    static int onMapReadyCnt = 0;


    // Location [] arrLoc; // 1/3 declare
    ArrayList<LatLng> arrLatLng; // 1/2  = new LatLng(28.410067, -81.583699);
    int ltnLngIdx = 0;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        // arrLoc = new Location[100]; // 2/3 instantiate
        arrLatLng = new ArrayList<LatLng>(); // 2/2

        // check to see if GPS is even in the damn tablet/phone

        PackageManager packageManager = this.getPackageManager();
        boolean hasGPS = packageManager.hasSystemFeature(PackageManager.FEATURE_LOCATION_GPS);
        if(hasGPS)
            Toast.makeText(this, "GPS detected!", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "GPS NOT detected!", Toast.LENGTH_SHORT).show();


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this); // will call OnMapReadyCallback.onMapReady(Googlemap)?
        mManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE); // gps
        // mLocationView = new TextView(this);
        mLocationView = (TextView)findViewById(R.id.mLocationView);
        mLocationView.setText("Hello from inside the app");

        /*
        LatLng tmp = new LatLng(40, -130);
        arrLatLng.add(tmp);
        tmp = new LatLng(41, -131);
        arrLatLng.add(tmp);
        tmp = new LatLng(42, -132);
        arrLatLng.add(tmp);
        */


    }


    @Override
    public void onMapReady(GoogleMap map) {

        Toast.makeText(this, "You have called onMapReady" + onMapReadyCnt,  Toast.LENGTH_SHORT).show();


        // LatLng disneySevenLagoon = new LatLng(28.410067, -81.583699);
          LatLng disneyMagicKingdom = new LatLng(28.418971, -81.581436);


        /*map.addMarker(new MarkerOptions()
                // .position(new LatLng(0,0))
                .position(disneySevenLagoon)
                .title("Marker"));
        */


        LatLngBounds.Builder bounds;

        bounds = new LatLngBounds.Builder();

        /*
        map.addMarker(new MarkerOptions()
                // .position(new LatLng(0,0))
                .position(disneySevenLagoon)
                .title("Lagoon"));
        bounds.include(new LatLng(28.410067, -81.583699));

        MarkerOptions markerOptions;
        markerOptions = new MarkerOptions();
        map.addMarker(new MarkerOptions()
                // .position(new LatLng(0,0))
                .position(disneyMagicKingdom)
                .title("Kingdom"));
        bounds.include(new LatLng(28.418971, -81.581436));
        */
        // my own marker
        // MarkerOptions markerOptions;
        // markerOptions = new MarkerOptions();
        // LatLng tmpLl = new LatLng(37, -121);
        // map.addMarker(new MarkerOptions().position(tmpLl).title("Home"));
        // bounds.include(tmpLl);

        // my own marker - but on 2nd call here
        // MarkerOptions markerOptions;
        // works!
        /*
        if (onMapReadyCnt >= 2) {
            markerOptions = new MarkerOptions();
            tmpLl = new LatLng(39, -124);
            map.addMarker(new MarkerOptions().position(tmpLl).title("Costco"));
            Toast.makeText(this, "Adding marker Costco! ", Toast.LENGTH_SHORT).show();

            bounds.include(tmpLl);
        }
        */
        // the one in the ArrayList
        for( LatLng tmpLntLng : arrLatLng) {
            map.addMarker(new MarkerOptions().position(tmpLntLng).title("Marked By You"));
            Toast.makeText(this, "Adding marker to map I hope! ", Toast.LENGTH_SHORT).show();
            bounds.include(tmpLntLng);

        }


        map.setMyLocationEnabled(true);

        // map.moveCamera(CameraUpdateFactory.newLatLng(disneySevenLagoon)); // disneySevenLagoon is type LatLng
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude())));
        // CameraUpdate cU = CameraUpdateFactory.newLatLngBounds(bounds.build(), 100, 100, 50);

        //map.moveCamera(cU);
        onMapReadyCnt++;

    } // onMapReady



   public void funcButtonMark(View view) {
       // Toast.makeText(this, "Marking!", Toast.LENGTH_SHORT).show();
       Log.d("XAC", "Marking");
       mCurrentLocation = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       String foo =  String.format("Your Location:\n%.2f, %.2f", mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());


        // more test - works
       /*
       LatLng tmp = new LatLng(50, -130);
       arrLatLng.add(tmp);
       tmp = new LatLng(51, -131);
       arrLatLng.add(tmp);
       tmp = new LatLng(52, -132);
       arrLatLng.add(tmp);
       */


       LatLng tmp = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
       arrLatLng.add(tmp);

       // Toast.makeText(this, "Marking! Now has elements : " + arrLatLng.size(), Toast.LENGTH_SHORT).show();

       Toast.makeText(this, foo, Toast.LENGTH_SHORT);


       SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
       mapFragment.getMapAsync(this); // force a call to onMapReady?


   }



   public void funcButtonPic(View view) {

       Log.d("XAC", "Click!");
       String foo = "";

       /*
       for(int i = 0; i<30;i++) {
            foo = foo + String.format("   +-+ %f", arrLatLng[0]);
       }
       */
       TextView tv = (TextView) findViewById(R.id.mDebugText);

       Toast.makeText(this, "Click! You stored" + foo, Toast.LENGTH_SHORT).show();
       mCurrentLocation = mManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       foo =  String.format("Your Location appears to be :\n%.2f, %.2f", mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());

       LatLng tmp = new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLatitude());
       foo = foo + "old arrLatLg was " + arrLatLng.size();
       arrLatLng.add(tmp);
       foo = foo + " arrLatLg is now  " + arrLatLng.size();

       tv.setText(foo);


       //


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
            // GPS updates quite often, sending a Toast every 5 seconds or so
            // Toast.makeText(MapsActivity.this, "GPS invoked onLocationChanged!", Toast.LENGTH_SHORT).show();
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