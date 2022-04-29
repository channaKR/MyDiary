package com.example.mydiary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.mydiary.databinding.ActivityMapLocationBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import org.jetbrains.annotations.NotNull;

public class map_location extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private ActivityMapLocationBinding binding;
    Button btnOk,back_cancel;
    private boolean grantLocation;
    private FusedLocationProviderClient myLocationProvider;
    private PlacesClient placesClient;
    private Location myLastLocation;

    String Locationz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );

        binding = ActivityMapLocationBinding.inflate ( getLayoutInflater () );
        setContentView ( binding.getRoot () );

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager ()
                .findFragmentById ( R.id.map );
        mapFragment.getMapAsync ( this );
        Toolbar toolbar=findViewById(R.id.maptoolbar);
        setSupportActionBar(toolbar);
        //setActionBar(toolbar);
        //setSupportActionBar(toolbar);


        String apiKey = getString(R.string.google_maps_key);
        Places.initialize(getApplicationContext(),apiKey);
        placesClient = Places.createClient(this);
        myLocationProvider = LocationServices.getFusedLocationProviderClient(this);
        btnOk=findViewById ( R.id.btn_selectlocation );
        back_cancel=findViewById ( R.id.Cancel_bt );
        btnOk.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {


                if(Locationz !=null){
                    Intent myintent=new Intent (getApplicationContext (),Add_Diary.class);
                    myintent.putExtra ( "locationreturn",Locationz );
                    setResult (0,myintent );

                    finish ();


                }
                else{

                    Toast.makeText ( map_location.this, "Select location ", Toast.LENGTH_LONG ).show ();
                }

            }
        } );

 back_cancel.setOnClickListener ( new View.OnClickListener () {
     @Override
     public void onClick(View v) {
         if(Locationz ==null){
             Intent myintent=new Intent (getApplicationContext (),Add_Diary.class);
             myintent.putExtra ( "locationreturn",Locationz );
             setResult (0,myintent );

             finish ();


         }
         else{

             Toast.makeText ( map_location.this, "Select location ", Toast.LENGTH_LONG ).show ();
         }
     }
 } );



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed ();
        Intent intent=new Intent (getApplicationContext (),Diary_Menu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        Toast.makeText ( this, "Back to main menu", Toast.LENGTH_SHORT ).show ();


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


        // Add a marker in Sydney and move the camera
        getLocationPermission ();
        mMap = googleMap;

        mMap.getUiSettings ().setZoomControlsEnabled ( true );



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate ( R.menu.menu, menu );
        return super.onCreateOptionsMenu ( menu );
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId ()) {
            case R.id.menupicklocation:

                pickCurrentPlace ();
                return true;
            case R.id.menupickdiarytlocation:

                mMap.setOnMapClickListener ( new GoogleMap.OnMapClickListener () {
                    @Override
                    public void onMapClick(@NonNull @NotNull LatLng latLng) {
                           MarkerOptions markerOptions=new MarkerOptions ();
                           markerOptions.position ( latLng );
                           markerOptions.title ( latLng.latitude+":"+latLng.longitude );
                           mMap.clear ();
                           mMap.animateCamera ( CameraUpdateFactory.newLatLng ( latLng ) );
                           mMap.addMarker ( markerOptions );
                           Locationz=markerOptions.getTitle ();

                    }
                } );


                return true;


        }

        return super.onOptionsItemSelected ( item );
    }

    private void getLocationPermission() {
        grantLocation = false;
        if (ContextCompat.checkSelfPermission ( this.getApplicationContext (), Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED) {
            grantLocation = true;
        } else {
            ActivityCompat.requestPermissions ( this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1 );
        }
    }


    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        grantLocation =false;
        switch (requestCode)
        {
            case 1:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    grantLocation = true;
                    GetDeviceLocation();
                }
            }
        }

    }



    private void GetDeviceLocation() {
        if (grantLocation) {



            @SuppressLint("MissingPermission") Task<Location> myLocations = myLocationProvider.getLastLocation ();
            myLocations.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    if(task.isSuccessful())
                    {
                        myLastLocation = task.getResult();
                        //LatLng myLanLon = new LatLng(myLastLocation.getLatitude(),myLastLocation.getLongitude());
                        LatLng myLanLon = new LatLng(7.258625957654444, 80.52105190739616);
                        mMap.addMarker(new MarkerOptions().position(myLanLon).title("Marker in Kandy"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLanLon));
                    }
                    else
                    {
                        LatLng sydney = new LatLng(33.86, 151.20);
                        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Kandy"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                    }

                }
            });
        }
    }
    private void pickCurrentPlace() {
        if (mMap == null) {
            return;
        }

        if (grantLocation) {
            GetDeviceLocation ();
        } else {
            LatLng sydney = new LatLng ( 33.86, 151.20 );
            mMap.addMarker ( new MarkerOptions ()
                    .title ( "Defalut location" )
                    .position ( sydney )
                    .snippet ( "Your Default location" ) );

            // Prompt the user for permission.
            getLocationPermission ();
        }
    }

}



















