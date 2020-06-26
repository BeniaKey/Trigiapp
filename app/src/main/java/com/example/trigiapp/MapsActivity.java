package com.example.trigiapp;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{

  private GoogleMap mMap;

       @Override
       protected void onCreate(Bundle savedInstanceState)
       {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_maps);
            // crea un mapFragments
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
            .findFragmentById(R.id.map);
            //tramite questo metodo acquisiamo l'oggetto googlemap
            mapFragment.getMapAsync(this);
        }


        @Override
        public void onMapReady(GoogleMap googleMap)
        {
            mMap = googleMap;
            //troviamo la latitudine e longitudine dell'ospedale di pescara
            LatLng ospedalepe = new LatLng(42.463654, 14.197467);
            //inseriamo un marker nella mappa posizionato esattamente sull'ospedale
            mMap.addMarker(new MarkerOptions().position(ospedalepe).title("Marker in Ospedale Pescara"));
          //  mMap.moveCamera(CameraUpdateFactory.newLatLng(ospedalepe));

           //zoomiamo sull'ospedale di pescara
            mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(ospedalepe , 14.0f) );
        }
}
