package psm.edu.proj03;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements android.location.LocationListener{

    protected CCompass compass;
    private static SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor magnetometer;
    private SensorEventListener sensorListener = new SensorEventListener() {
        float[] grawit = new float[3];
        float[] geomag = new float[3];

        @Override
        public void onSensorChanged(SensorEvent event) {
            if(event.sensor.getType()==Sensor.TYPE_ACCELEROMETER){
                for(int i=0; i<3; i++) grawit[i] = event.values[i];
            }
            if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
                for(int i=0; i<3; i++) geomag[i] = event.values[i];
            if(grawit != null && geomag != null){
                float R[] = new float[9];
                float I[] = new float[9];
                boolean success = SensorManager.getRotationMatrix(R, I, grawit, geomag);
                if(success){
                    float orientation[] = new float[3];
                    SensorManager.getOrientation(R, orientation);
                    compass.updateDirection(orientation[0]);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {}
    };

    protected android.location.LocationManager locMan;
    protected TextView txtLat;
    protected TextView txtLon;
    protected TextView txtElevation;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        txtLon = findViewById(R.id.txtLon);
        txtLat = findViewById(R.id.txtLat);
        txtElevation = findViewById(R.id.txtElevation);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            }
            return;
        }

        compass = new CCompass(this);
        LinearLayout.LayoutParams par = new LinearLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
                );
        compass.setLayoutParams(par);
        TextView text = new TextView(this);
        text.setText(getString(R.string.compassName));
        text.setLayoutParams(par);
        LinearLayout lt = (LinearLayout) findViewById(R.id.internalLayout);
        lt.addView(text);
        lt.addView(compass);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
    }

    @Override
    public void onLocationChanged(Location location) {
        txtLat.setText(Location.convert(location.getLatitude(), Location.FORMAT_SECONDS));
        txtLon.setText(Location.convert(location.getLongitude(), Location.FORMAT_SECONDS));
        txtElevation.setText(Double.toString(location.getAltitude()));
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) return;
        locMan.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 20, this);
        sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(sensorListener, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause(){
        super.onPause();
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED) return;
        locMan.removeUpdates(this);
        sensorManager.unregisterListener(sensorListener, accelerometer);
        sensorManager.unregisterListener(sensorListener, magnetometer);
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case 1:
                if(grantResults.length==0||grantResults[0]!=PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(this, getString(R.string.noPermission), Toast.LENGTH_LONG).show();
                }
        }
    }
}
