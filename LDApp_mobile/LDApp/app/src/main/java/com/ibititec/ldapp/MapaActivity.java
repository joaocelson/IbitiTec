package com.ibititec.ldapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ibititec.ldapp.helpers.AlertMensage;
import com.ibititec.ldapp.models.Comerciante;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String TAG = "LOG-MAPA";
    public static final int REQUEST_CODE_MY_LOCATION = 42;
    GoogleMap mMap;
    private double latitude = 0;
    private double longitude = 0;
    private float zoom = 15;
    private List<String> empresa;
    private Comerciante comerciante;

    private HashMap<Marker, Comerciante> empresaPorMarker;
    private HashMap<String, Marker> markersPorEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupMapa();
        lerIntent();
        lePosicao();
        permissaoMapa();
    }

    private void lerIntent() {
        Intent intent = getIntent();
        comerciante = (Comerciante) intent.getSerializableExtra("comerciante");
    }

    private void setupMapa() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void buscaPatio(String sigla) {
        Log.i(TAG, String.format("Busca: %s", sigla));

        if (sigla.isEmpty()) {
            Snackbar.make(findViewById(R.id.fab), "Busca vazia!", Snackbar.LENGTH_SHORT).show();
            return;
        }

        if (!markersPorEmpresa.containsKey(sigla)) {
            Snackbar.make(findViewById(R.id.fab), "Pátio não encontrado.", Snackbar.LENGTH_SHORT).show();
            return;
        }

        Marker marker = markersPorEmpresa.get(sigla);
        mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));

        marker.showInfoWindow();
    }

    private void permissaoMapa() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_MY_LOCATION);
        }
    }

    private void lePosicao() {
        // SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MapaActivity.this);

        latitude = comerciante.getLatitude();
        longitude = comerciante.getLongitude();

        Log.i(TAG, String.format("Leu posição: (%f, %f) @ %f", latitude, longitude, zoom));
    }

    private void restauraPosicao() {
        if (mMap != null) {
            Log.i(TAG, String.format("Restaurou posição: (%f, %f) @ %f", latitude, longitude, zoom));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), zoom));
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.i(TAG, String.format("Pause: (%f, %f) @ %f", latitude, longitude, zoom));

        PreferenceManager.getDefaultSharedPreferences(MapaActivity.this).edit()
                .putFloat("latitude", (float) latitude)
                .putFloat("longitude", (float) longitude)
                .putFloat("zoom", zoom)
                .apply();
    }

    private void marcarEmpresa() {

        try {
            if (mMap != null && comerciante != null) {
                empresaPorMarker = new HashMap<>();
                markersPorEmpresa = new HashMap<>();

                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(Double.parseDouble(comerciante.getEnderecos().get(0).getLatitude()),
                                Double.parseDouble(comerciante.getEnderecos().get(0).getLongitude())))
                        .title(comerciante.getNome())
                        .snippet(comerciante.getNome())
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

                empresaPorMarker.put(marker, comerciante);
                markersPorEmpresa.put(comerciante.getNome(), marker);

                empresa = new ArrayList<>(markersPorEmpresa.keySet());
                restauraPosicao();
            }
        } catch (Exception ex) {
            Log.i(TAG, ex.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        int id = item.getItemId();
        switch (id) {
            // Id correspondente ao botão Up/Home da actionbar
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_MY_LOCATION: {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mMap.setMyLocationEnabled(true);
                break;
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //   mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_CODE_MY_LOCATION);
        } else {
            mMap.setMyLocationEnabled(true);
        }

        mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                latitude = cameraPosition.target.latitude;
                longitude = cameraPosition.target.longitude;
                zoom = cameraPosition.zoom;

                Log.i(TAG, String.format("CameraChange: (%f, %f) @ %f", latitude, longitude, zoom));
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.i(TAG, String.format("Marker %s apertado.", marker.getTitle()));

                if (!empresaPorMarker.containsKey(marker)) {

                    Log.e(TAG, "Pátio não localizado!");
                    AlertMensage.setMessageAlert("Erro ao abrir informações do pátio!", MapaActivity.this, "Atenção");

                    return;
                }

                Comerciante comerciante = empresaPorMarker.get(marker);
            }
        });

        marcarEmpresa();
    }

}
