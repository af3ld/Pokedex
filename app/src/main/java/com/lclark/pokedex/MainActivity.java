package com.lclark.pokedex;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.activity_main_listview) ListView listView;
    PokemonAdapter mAdapter;
    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()){
            Toast.makeText(this, R.string.networkError, Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, R.string.networkSuccess, Toast.LENGTH_SHORT).show();
        }
        Pokedex pokedex = new Pokedex();
        mAdapter = new PokemonAdapter(this, pokedex.getPokemen());
        listView.setAdapter(mAdapter);
    }


}
