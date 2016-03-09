package com.ibititec.ldapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.ibititec.ldapp.helpers.JsonHelper;
import com.ibititec.ldapp.models.Comerciante;

import java.util.HashMap;
import java.util.List;

public class MapaActivity extends AppCompatActivity implements OnMapReadyCallback {
    public static final String TAG = "LOG-MAPA";
    public static final int REQUEST_CODE_MY_LOCATION = 42;
    GoogleMap mMap;
    private double latitude = 0;
    private double longitude = 0;
    private float zoom = 2;
    private List<String> siglas;

        private List<Comerciante> comerciantes;

        private HashMap<Marker, Comerciante> patiosPorMarker;
        private HashMap<String, Marker> markersPorSigla;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_mapa);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });

            setupPatios();
            setupFab();
            setupMapa();

            lePosicao();
            restauraPosicao();
        }

        private void setupMapa() {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }

        private void setupPatios() {
            String json = leJsonPatios();

            if (json.isEmpty()) {
                Log.i(TAG, "Baixou patios");
                //baixarPatios();
            } else {
                Log.i(TAG, "Carregou patios salvos");
                carregaPatios(json);
                //marcaPatios();
            }
        }

        private void setupFab() {
            FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final AutoCompleteTextView actv = new AutoCompleteTextView(MapaActivity.this);
                    actv.setSingleLine();
                    actv.setThreshold(1);
                    actv.setAllCaps(true);

                    LinearLayout.LayoutParams layoutParams =
                            new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT);
                    actv.setLayoutParams(layoutParams);

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(MapaActivity.this,
                            android.R.layout.simple_list_item_1,
                            siglas);
                    actv.setAdapter(adapter);

                    actv.setImeOptions(EditorInfo.IME_ACTION_SEARCH);

                    //Fazer a busca de pátio
                    final AlertDialog alertDialog = new AlertDialog.Builder(MapaActivity.this)
                            .setTitle("Busca")
                            .setPositiveButton("Busca", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    buscaPatio(actv.getText().toString().trim().toUpperCase());
                                }
                            })
                            .setNegativeButton("Cancela", null)
                            .setView(actv)
                            .show();

                    actv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            buscaPatio(actv.getText().toString().trim().toUpperCase());
                            alertDialog.dismiss();

                            return true;
                        }
                    });

                }
            });
        }

        private void buscaPatio(String sigla) {
            Log.i(TAG, String.format("Busca: %s", sigla));

            if (sigla.isEmpty()) {
                Snackbar.make(findViewById(R.id.fab), "Busca vazia!", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (!markersPorSigla.containsKey(sigla)) {
                Snackbar.make(findViewById(R.id.fab), "Pátio não encontrado.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            Marker marker = markersPorSigla.get(sigla);
            mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            marker.showInfoWindow();
        }

//        private void baixarPatios() {
//            (new AsyncTask<String, Void, String>() {
//
//                private ProgressDialog progressDialog;
//
//                @Override
//                protected void onPreExecute() {
//                    super.onPreExecute();
//                    progressDialog = ProgressDialog.show(MapaActivity.this, "Aguarde", "Baixando pátios");
//                }
//
//                @Override
//                protected String doInBackground(String... params) {
//                    String json = null;
//
//                    try {
//                        String url = params[0];
//                        json = HttpHelper.downloadFromURL(url);
//                        Log.i(TAG, json);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                        Log.e(TAG, String.format(getString(R.string.msg_erro_json), e.getMessage()));
//                    }
//
//                    return json;
//                }
//
//                @Override
//                protected void onPostExecute(String json) {
//                    super.onPostExecute(json);
//
//                    progressDialog.dismiss();
//
//                    if (json == null) {
//                        Log.w(TAG, "JSON veio nulo!");
//                        return;
//                    }
//
//                    PreferenceManager.getDefaultSharedPreferences(MapaActivity.this).edit()
//                            .putString("patios.json", json)
//                            .apply();
//
//                    carregaPatios(json);
//                    marcaPatios();
//                }
//            }).execute(getString(R.string.patios));
//        }

        private void lePosicao(){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MapaActivity.this);

            latitude = prefs.getFloat("latitude", 0);
            longitude = prefs.getFloat("longitude", 0);
            zoom = prefs.getFloat("zoom", 2);

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

        private String leJsonPatios() {
            String json = PreferenceManager.getDefaultSharedPreferences(MapaActivity.this)
                    .getString("patios.json", "");
            Log.i(TAG, "Lendo preferences: " + json);
            return json;
        }

        private void carregaPatios(String json) {
            comerciantes = JsonHelper.getList(json, Comerciante[].class);

            Snackbar.make(findViewById(R.id.fab), String.format("%d pátios carregados.", comerciantes.size()), Snackbar.LENGTH_SHORT).show();
        }

//        private void marcaPatios() {
//            if (mMap != null && comerciantes != null) {
//                patiosPorMarker = new HashMap<>();
//                markersPorSigla = new HashMap<>();
//
//                for (Comerciante comerciante : comerciantes) {
//
//                    Marker marker = mMap.addMarker(new MarkerOptions()
//                            .position(new LatLng(comerciante.getLatitude(), comerciante.getLongitude()))
//                            .title(comerciante.getSigla())
//                            .snippet(comerciante.getNome())
//                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//
//                    patiosPorMarker.put(marker, comerciante);
//                    markersPorSigla.put(comerciante.getSigla(), marker);
//                }
//
//                siglas = new ArrayList<>(markersPorSigla.keySet());
//            }
//        }

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

            //noinspection SimplifiableIfStatement
            if (id == R.id.action_atualizar) {

                new AlertDialog.Builder(this)
                        .setTitle("Download")
                        .setMessage("Deseja atualizar os dados dos pátios?")
                        .setPositiveButton("Sim, baixar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i(TAG, "Usuário pediu atualização.");
                              //  baixarPatios();
                            }
                        })
                        .setNegativeButton("Cancelar", null)
                        .show();

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

            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

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

                    if (!patiosPorMarker.containsKey(marker)) {
                        Log.e(TAG, "Pátio não localizado!");
                        Snackbar.make(findViewById(R.id.fab), "Erro ao abrir informações do pátio!",
                                Snackbar.LENGTH_SHORT).show();
                        return;
                    }

                    Comerciante comerciante = patiosPorMarker.get(marker);


                }
            });

            restauraPosicao();

            //marcaPatios();
        }

}
