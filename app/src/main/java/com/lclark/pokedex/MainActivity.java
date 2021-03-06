package com.lclark.pokedex;


import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    PokemonAdapter mAdapter;
    public JSONObject json;
    private boolean hasInternet = true;
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar mToolbar = (android.support.v7.widget.Toolbar)
                findViewById(R.id.activity_main_toolbar);
        mToolbar.setSubtitle(R.string.author);
        mToolbar.setTitle(getString(R.string.app_name));
        mToolbar.setNavigationIcon(R.mipmap.pokeball);
        mToolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.pokédex_red));
        mToolbar.setSubtitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.pokédex_red));
        setSupportActionBar(mToolbar);


        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            Toast.makeText(this, R.string.networkError, Toast.LENGTH_LONG).show();
            hasInternet = false;
        } else {
            Toast.makeText(this, R.string.networkSuccess, Toast.LENGTH_SHORT).show();
            hasInternet = true;
        }
        Pokedex pokedex = new Pokedex();
        mAdapter = new PokemonAdapter(this, pokedex.getPokemen());
        ListView listView = (ListView) findViewById(R.id.activity_main_listview);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (hasInternet) {
                    position++;
                    Log.d(TAG, getString(R.string.itemClicked) + position);
                    try {
                        json = new Pokemon_JSON(MainActivity.this)
                                .execute(Integer.toString(position)).get();
                        if (json != null) {
                            Log.d(TAG, json.toString());
                            Intent myIntent = new Intent(getApplicationContext(), TradingCardActivity.class);
                            myIntent.putExtra(TradingCardActivity.ARG_identifier, json.toString());
                            startActivity(myIntent);
                        }
                    } catch (InterruptedException | ExecutionException e) {
                        Log.e(TAG, e.getLocalizedMessage());
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            R.string.networkError, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
