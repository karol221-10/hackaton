package kielce.hackathon.pl.appka;

import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.geojson.GeoJsonLayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LatLng object1 = new LatLng(50.844157,20.644876);
    private LatLng object2 = new LatLng(50.863184,20.585875);
    private Marker mObject1;
    private Marker mObject2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
   //     mMap.addMarker(new MarkerOptions()
   //     .position(object1)
   //     .title("Przedszkole Samorządowe nr 36 - obwód nr 64"));
    //    mMap.addMarker(new MarkerOptions()
      //          .position(object2)
       //         .title("SZKOŁA PODSTAWOWA NR 31 Z ODDZIAŁAMI INTEGRACYJNYMI"));

        try
        {
            JSONObject json = new JSONObject(loadJSONFromAsset("wybory_lokale_point.geojson"));
            JSONArray features = json.getJSONArray("features");
            for(int i = 0;i<features.length();i++)
            {
                JSONObject object = features.getJSONObject(i);
                JSONObject object2 = object.getJSONObject("properties");
                String object_name = object2.getString("NAZWA");
                object_name += " Kielce";
                Geocoder gc = new Geocoder(getApplicationContext());
                try
                {
                    List<Address> addresses = gc.getFromLocationName(object_name,2);
                    LatLng objectcreated = new LatLng(addresses.get(0).getLatitude(),addresses.get(0).getLongitude());
                    mMap.addMarker(new MarkerOptions()
                                 .position(objectcreated)
                                 .title("Object "+i));
                    Log.d("DEB","Created object");
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }

            }
         //   JSONObject geometry = json.getJSONObject("geometry");
          //  GeoJsonLayer layer = new GeoJsonLayer(googleMap, json);
         //   layer.addLayerToMap();
        }
        catch(JSONException e)
        {
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
