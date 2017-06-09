package com.erroronserver.eventosdecaridade;

import android.content.Intent;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.location.Address;

import com.erroronserver.eventosdecaridade.controller.MapsController;
import com.erroronserver.eventosdecaridade.model.Evento;
import com.erroronserver.eventosdecaridade.util.Constantes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private LatLng latLng;
    private boolean podeMudarLocal = true;
    private Evento evento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void configMap(GoogleMap map, LatLng latLng, int type){

        map.setMapType(type);
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17.0f));

    }

    private MarkerOptions createMarker(LatLng position, String title, String snippet){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(position);
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher));
        markerOptions.position(position).title("Marcado em: " + title != null ? title : "Desconhecido");
        markerOptions.position(position).snippet(snippet);

        return markerOptions;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        evento = MapsController.getInstance().getEvento();
        String mensagemExibicao = null;
        LatLng byIntent = null;

        if(evento == null) {

            byIntent = new LatLng(-7.158825, -34.854829);
            podeMudarLocal = true;
            mensagemExibicao = "Você está em João Pessoa";

        }else{

            byIntent = new LatLng(evento.getLatitude(), evento.getLongitude());
            podeMudarLocal = false;
            mensagemExibicao = "Local do Evento";

        }

        mMap.addMarker(new MarkerOptions().position(byIntent).title(mensagemExibicao).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(byIntent));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(byIntent, 17.0f));
        mMap.setOnMapClickListener(this);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if(podeMudarLocal){
            this.latLng = latLng;
            new ReverseCode().execute();
        }
    }

    private class ReverseCode extends AsyncTask<Void, Void, Address>{

        @Override
        protected Address doInBackground(Void... voids) {
            Geocoder geocoder = new Geocoder(MapsActivity.this);
            try {
                List<android.location.Address> location = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
                return location.get(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Address address) {
            if(address != null){
                mMap.clear();
                mMap.addMarker(createMarker(latLng, address.getThoroughfare(), address.getLocality()));
                configMap(mMap, latLng, GoogleMap.MAP_TYPE_NORMAL);
            }
        }
    }
}
