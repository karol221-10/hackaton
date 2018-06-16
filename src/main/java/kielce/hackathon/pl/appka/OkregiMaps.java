package kielce.hackathon.pl.appka;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.maps.android.geometry.Bounds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class OkregiMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    private Activity activity = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okregi_maps);
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
        try {
            JSONObject json = new JSONObject(loadJSONFromAsset("wybory_okregi_polyg.geojson"));
            JSONArray features = json.getJSONArray("features");
            for (int i = 0; i < features.length(); i++) {
                JSONObject object = features.getJSONObject(i);
                JSONObject properties = object.getJSONObject("properties");
                JSONObject geometry = object.getJSONObject("geometry");
                JSONObject geometry2 = object.getJSONObject("geometry");
                JSONArray coordinates = geometry.getJSONArray("coordinates");
                JSONArray new_coordinates = coordinates.getJSONArray(0);
                double sumx = 0;
                double sumy = 0;
                PolygonOptions po = new PolygonOptions();
                String okreg_name = properties.getString("NAZWA");
                for (int j = 0; j < new_coordinates.length(); j++) {
                    JSONArray newer_coordinates = new_coordinates.getJSONArray(j);
                    double valuex = newer_coordinates.getDouble(0);
                    valuex -= 2386000;
                    double valuey = newer_coordinates.getDouble(1);
                    valuey -= 3574000;
                    valuex /= 100000;
                    valuey /= 100000;
                    LatLng objectcreated = new LatLng(valuex, valuey);
                    po.add(objectcreated);
                    sumx += valuex;
                    sumy += valuey;
                    po.fillColor(Color.argb(32, 255, 0, 0));
                }
                sumx /= new_coordinates.length();
                sumy /= new_coordinates.length();
                MarkerOptions mo = new MarkerOptions().position(new LatLng(sumx, sumy)).title(okreg_name);
                Marker marker = mMap.addMarker(mo);
                mo.anchor(0f, 0.5f);
                marker.showInfoWindow();
                mMap.addPolygon(po);
            }
            //   JSONObject geometry = json.getJSONObject("geometry");
            //  GeoJsonLayer layer = new GeoJsonLayer(googleMap, json);
            //   layer.addLayerToMap();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent new_activity = new Intent(activity,KandydaciUselesss.class);
                int numer_okregu = marker.getTitle().toString().charAt(6)-48;
                Bundle b = new Bundle();
                b.putInt("okreg",numer_okregu);
                new_activity.putExtras(b);
                Log.d("Tag"," "+numer_okregu);
                startActivity(new_activity);
                return false;
            }
        });
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
