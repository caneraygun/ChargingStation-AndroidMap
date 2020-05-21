package com.example.haritaa1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback{

    GoogleMap map;

    String eklenenKonumAdi = "";


    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;

    private ActionBarDrawerToggle drawerToggle;

    // İzin İstek Sonucu


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        int izin1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int izin2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        if( izin1 == PackageManager.PERMISSION_GRANTED && izin2 == PackageManager.PERMISSION_GRANTED)
        {
            map.setMyLocationEnabled(true);





        }
        else
        {
            AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
            adb.setTitle("Konum Devre Dışı")
                    .setMessage("Konum Hizmetini Açmak İçin, Uygulamayı Baştan Başlatın veya İzinler Sekmesine Gidin")
                    .show();
        }

    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment smf = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.mapFragment);

        smf.getMapAsync(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);



    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        Log.e("x","Harita OK");
        map = googleMap;

        /*
            Eğer Bu Cihazın Android Sürümü 23 Üzeriyse veya Altıysa

         */
        if(Build.VERSION.SDK_INT >= 23)
        {
            int izin1 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
            int izin2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if( izin1 == PackageManager.PERMISSION_GRANTED && izin2 == PackageManager.PERMISSION_GRANTED)
            {
                map.setMyLocationEnabled(true);
            }
            else
            {
                // Kullanıcıdan Konum İzni İste
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        1234
                        );
            }
        }
        else
        {
            map.setMyLocationEnabled(true);
        }



        // 41.042294, 29.009547
        LatLng llBAU = new LatLng(41.042294,29.009547);

        LatLng llHiltonBomonti = new LatLng(41.058694, 28.979351);

        LatLng llTrump = new LatLng(41.068483, 28.992821);

        LatLng llMetroCity = new LatLng(41.076211, 29.012637);

        LatLng llBogazici = new LatLng(41.085244, 29.045548);

        //Haritaya Marker Ekleme
        MarkerOptions mo = new MarkerOptions()
                .title("Bahçesehir Üniversitesi")
                .snippet("Station Number: 1")
                .position(llBAU);

        MarkerOptions mo2 = new MarkerOptions()
                .title("Hilton Bomonti Hotel")
                .snippet("Station Number: 2")
                .position(llHiltonBomonti);

        MarkerOptions mo3 = new MarkerOptions()
                .title("Trump Mall")
                .snippet("Station Number: 3")
                .position(llTrump);

        MarkerOptions mo4 = new MarkerOptions()
                .title("MetroCity Business Center")
                .snippet("Station Number: 4")
                .position(llMetroCity);

        MarkerOptions mo5 = new MarkerOptions()
                .title("Bogazici University Station")
                .snippet("Station Number: 5")
                .position(llBogazici);

        map.addMarker(mo);
        map.addMarker(mo2);
        map.addMarker(mo3);
        map.addMarker(mo4);
        map.addMarker(mo5);

        // Camera Yonetimi
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(llBAU, 18));
        map.getUiSettings().setZoomControlsEnabled(true);

        // InfoWindow Click Listener
        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker)
            {
                String msg = marker.getTitle() + " -->" + marker.getSnippet();
                //double enlem = marker.getPosition().latitude;
                //double boylam = marker.getPosition().longitude;
                Toast.makeText(MainActivity.this, msg,Toast.LENGTH_SHORT)
                        .show();

            }
        });

        // OnLongClick Listener
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng)
            {
                /*
                MarkerOptions newMo = new MarkerOptions()
                        .position(latLng)
                        .title("Seçtiğiniz Nokta")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        .draggable(true);
                        map.addMarker(newMo);
                 */

                final EditText et = new EditText(MainActivity.this);
                AlertDialog.Builder adb = new AlertDialog.Builder(MainActivity.this);
                adb.setTitle("Konum Adı")
                        .setView(et)
                        .setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i)
                            {
                                dialogInterface.dismiss();
                                eklenenKonumAdi = et.getText().toString();
                                if(eklenenKonumAdi == "")
                                {

                                    return;
                                }

                            }
                        })
                        .show();





            }
        });

        // Marker Drag Listener
        map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

                Log.e("x","Started @"+marker.getPosition());
            }

            @Override
            public void onMarkerDrag(Marker marker) {

                Log.e("x","Currently @"+marker.getPosition());

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {

                Log.e("x","Dropped @"+marker.getPosition());

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Uydu").setShowAsAction (MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        menu.add("Arazi").setShowAsAction (MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        menu.add("Hibrit").setShowAsAction (MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
        menu.add("Normal").setShowAsAction (MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

       String ad = item.getTitle().toString();

        if (ad.equals("Uydu")) map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        if (ad.equals("Arazi")) map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        if (ad.equals("Hibrit")) map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        if (ad.equals("Normal")) map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        switch (item.getItemId()){
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
