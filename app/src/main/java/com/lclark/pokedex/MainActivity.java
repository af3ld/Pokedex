package com.lclark.pokedex;

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.Toolbar;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

//    @Bind(R.id.activity_main_listview) ListView listView;
    PokemonAdapter mAdapter;
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.activity_main_toolbar);
        mToolbar.setSubtitle(R.string.author);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.mipmap.pokeball);
        mToolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.pokédex_red));
        mToolbar.setSubtitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.pokédex_red));
        setSupportActionBar(mToolbar);


        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()){
            Toast.makeText(this, R.string.networkError, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.networkSuccess, Toast.LENGTH_SHORT).show();
        }
        Pokedex pokedex = new Pokedex();
        mAdapter = new PokemonAdapter(this, pokedex.getPokemen());
        ListView listView = (ListView) findViewById(R.id.activity_main_listview);
        listView.setAdapter(mAdapter);


    }

}
