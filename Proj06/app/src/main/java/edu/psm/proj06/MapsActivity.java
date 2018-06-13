package edu.psm.proj06;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_BLUE;
import static com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_GREEN;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    double lon=Double.parseDouble(((EditText) findViewById(
                            R.id.editText1)).getText().toString());
                    double lat=Double.parseDouble(((EditText) findViewById(
                            R.id.editText2)).getText().toString());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                            new LatLng(lat,lon),11.0f));
                }catch(Exception e){
                    Toast.makeText(MapsActivity.this,"Błędne współrzędne!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                mMap.clear();
                for(MarkerOptions m: markerList) mMap.addMarker(m);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                        new LatLng(50.062885, 19.939515), 12.0f));
        }});
        findViewById(R.id.radioButton1).setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                if(((RadioButton)findViewById(
                        R.id.radioButton1)).isChecked())
                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }});

        findViewById(R.id.radioButton2).setOnClickListener(new View.OnClickListener(){
            @Override public void onClick(View v){
                if(((RadioButton)findViewById(
                        R.id.radioButton2)).isChecked())
                    mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }});
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
    public void onMapReady(GoogleMap googleMap){
        initMarkers();
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                new LatLng(50.075225, 19.995153),11.0f));
    }
    private List<MarkerOptions> markerList;
    private void initMarkers(){
        markerList = new ArrayList<>();
        markerList.add(new MarkerOptions().position(new LatLng(50.075225,19.995153)).title("PK Czyżyny").snippet("Wydział mechaniczny")
                .icon(BitmapDescriptorFactory.defaultMarker(HUE_BLUE))
        );

        markerList.add(new MarkerOptions().position(new LatLng(50.071500,19.943962)).title("PK Warszawska").snippet("REKTORAT")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pk))
        );

        markerList.add(new MarkerOptions().position(new LatLng(50.075523,19.909229)).title("PK Podchorążych").snippet("Wydział Mat.Fiz.Inf.")
                .icon(BitmapDescriptorFactory.defaultMarker(HUE_GREEN))
        );
    }
}
