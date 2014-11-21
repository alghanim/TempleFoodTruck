package com.example.templefoodtruck;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity{

	private GoogleMap googleMap;
	Marker posMarker;
	TextView txtView;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        txtView = (TextView) findViewById(R.id.txtView);
        new getAllTrucksTask().execute(new ApiConnector());
        
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
		
		Criteria criteria = new Criteria();
		LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		String provider = locationManager.getBestProvider(criteria, true);
		Location myLocation = locationManager.getLastKnownLocation(provider);
		googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		double latitude = myLocation.getLatitude();
		double longitude = myLocation.getLongitude();
		
		Log.e("Laitude",Double.toString(latitude));
		Log.e("Longitued",Double.toString(longitude));
		
		LatLng latLng = new LatLng(latitude, longitude);
		googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
		
		
		this.posMarker = googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title("You are here!"));
	
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));

	}
	



	
class getAllTrucksTask extends AsyncTask<ApiConnector,Long,JSONArray>{

	@Override
	protected JSONArray doInBackground(ApiConnector... params) {
		return params[0].getAllTrucks();
	}
	
	protected void onPostExecute(JSONArray jsonArray){
		//setTextToTextView(jsonArray);
	}

}

}
   