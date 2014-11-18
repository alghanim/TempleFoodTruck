package com.example.templefoodtruck;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity{

	private GoogleMap googleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        getMap();
        UserCurrentLocation();
    }

	private void getMap() {
		FragmentManager fm = getSupportFragmentManager();
		SupportMapFragment fragment = (SupportMapFragment) fm.findFragmentById(R.id.map);
		googleMap = fragment.getMap();      	
	}

	private void UserCurrentLocation() {
		googleMap.setMyLocationEnabled(true);
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);
		Location myLocation = locationManager.getLastKnownLocation(provider);
		googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		double latitude = myLocation.getLatitude();
		double longitude = myLocation.getLongitude();
		LatLng latLng = new LatLng(latitude, longitude);
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));		
		googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!"));
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
	}


}
