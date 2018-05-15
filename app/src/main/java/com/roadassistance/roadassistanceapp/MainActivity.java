package com.roadassistance.roadassistanceapp;

import android.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.iid.FirebaseInstanceId;
import com.roadassistance.roadassistanceapp.presentation.searchinprogress.view.GmapFragment;

public class MainActivity extends AppCompatActivity {
    FragmentManager fM = getFragmentManager();
    SupportMapFragment sMF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sMF = SupportMapFragment.newInstance();
        fM.beginTransaction().replace(R.id.main_fragment, new GmapFragment()).commit();
        Log.d("DANTE", FirebaseInstanceId.getInstance().getToken());

    }
}
