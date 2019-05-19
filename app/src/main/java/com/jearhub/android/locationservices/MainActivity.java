package com.jearhub.android.locationservices;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        final TextView txtLatitude = (TextView) findViewById ( R.id.txtLatitude );
        final TextView txtLongitude = (TextView) findViewById ( R.id.txtLongitude );
        final Button btnGetLocation = (Button) findViewById ( R.id.btnLocation );
        final FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient ( this );
        btnGetLocation.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                try {
                    if (ActivityCompat.checkSelfPermission ( this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED)
                        if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission ( this, Manifest.permission.ACCESS_COARSE_LOCATION )) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                    Task<Location> location = client.getLastLocation ();
                    location.addOnCompleteListener ( new OnCompleteListener<Location> () {
                        @Override
                        public void onComplete(@NonNull Task<Location> task) {
                            txtLatitude.setText ( Double.toString ( task.getResult ().getLatitude () ) );
                            txtLongitude.setText ( Double.toString ( task.getResult ().getLongitude () ) );
                            System.err.println ( task.getResult ().getLatitude () );
                        }
                    } );
                } catch (SecurityException ex) {
                    ex.getStackTrace ();
                }
            }
        } );

        LocationRequest req = new LocationRequest ();
        req.setInterval ( 2000 ); // 2 seconds
        req.setFastestInterval ( 500 ); //500 milliseconds
        req.setPriority ( LocationRequest.PRIORITY_HIGH_ACCURACY );

    }
}
