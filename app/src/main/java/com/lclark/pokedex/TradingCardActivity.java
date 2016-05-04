package com.lclark.pokedex;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.Arrays;

import butterknife.Bind;

/**
 * Created by alexfeldman on 4/12/16.
 */
public class TradingCardActivity extends AppCompatActivity {

    public static final String TAG = TradingCardActivity.class.getSimpleName();
    public static final String ARG_identifier = "Pokemon, plz";
    private Pokemon pokemon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradingcard);

        String allData = getIntent().getStringExtra(ARG_identifier);

        String[] abilities = JSONStringToPokemon(allData);
        if (pokemon != null) {
            Log.d(TAG, pokemon.toString());
        }


        android.support.v7.widget.Toolbar mToolbar = (android.support.v7.widget.Toolbar)
                findViewById(R.id.tradingcard_toolbar);
        mToolbar.setSubtitle(pokemon.getId());
        mToolbar.setTitle(pokemon.getName(true));
        mToolbar.setNavigationIcon(R.mipmap.pokeball);
        mToolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.pokédex_red));
        mToolbar.setSubtitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.pokédex_red));
        TextView ab1textView = (TextView) findViewById(R.id.tradingcard_ab1);
        TextView ab2TextView = (TextView) findViewById(R.id.tradingcard_ab2);
        TextView heightTextView = (TextView) findViewById(R.id.tradingcard_height);
        TextView weightTextView = (TextView) findViewById(R.id.tradingcard_weight);
        ImageView pokemonImageview = (ImageView) findViewById(R.id.tradingcard_image);


        Picasso.with(this).load(pokemon.getImageUrl()).fit().centerInside().into(pokemonImageview);
        ab1textView.setText(abilities[0]);
        ab2TextView.setText(abilities[1]);
        heightTextView.setText(getString(R.string.height_title, pokemon.getHeight()));
        weightTextView.setText(getString(R.string.weight_title, pokemon.getWeight()));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(ARG_identifier, pokemon.getName(true));
        setResult(RESULT_OK, intent);
        finish();
    }

    public String[] JSONStringToPokemon(String jsonString) {
        String[] talent;
        try {
            JSONObject json = new JSONObject(jsonString);
            if (json.length() != 0) {
                String[] prePokemon = {
                        Integer.toString(json.getInt("id")),
                        json.getString("name"),
                        "",
                        json.getString("height"),
                        json.getString("weight"),
                };

                StringBuilder sb = new StringBuilder();
                for (String aPrePokemon : prePokemon) {
                    sb.append(aPrePokemon);
                    sb.append(',');
                }
                pokemon = new Pokemon(sb.toString());
                if (pokemon != null){
                    Log.d(TAG, pokemon.getId() + " " + pokemon.getName(false)
                    + " " + pokemon.getHeight() + " " + pokemon.getWeight());
                }
                JSONArray abilities = json.getJSONArray("abilities");
                talent = new String[abilities.length()];
                for (int i = 0; i < abilities.length(); i++) {
                    talent[i] = abilities.getJSONObject(i)
                            .getJSONObject("ability").getString("name");
                }
                return talent;
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }
        talent = new String[0];
        return talent;
    }
}
