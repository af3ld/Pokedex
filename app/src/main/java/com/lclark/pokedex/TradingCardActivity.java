package com.lclark.pokedex;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
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

    @Bind(R.id.tradingcard_ab1)
    TextView ab1TextView;
    @Bind(R.id.tradingcard_ab2)
    TextView ab2TextView;
    @Bind(R.id.tradingcard_height)
    TextView heightTextview;
    @Bind(R.id.tradingcard_weight)
    TextView weightTextview;
    @Bind(R.id.tradingcard_image)
    ImageView pokemonImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradingcard);

        String allData = getIntent().getParcelableExtra(ARG_identifier);
        String[] abilities = JSONStringToPokemon(allData);

        android.support.v7.widget.Toolbar mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.activity_main_toolbar);
        mToolbar.setSubtitle(pokemon.getId());
        mToolbar.setTitle(pokemon.getName(true));
        mToolbar.setNavigationIcon(R.mipmap.pokeball);
        mToolbar.setTitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.pokédex_red));
        mToolbar.setSubtitleTextColor(ContextCompat.getColor(getApplicationContext(), R.color.pokédex_red));

        Picasso.with(this).load(pokemon.getImageUrl()).fit().centerInside().into(pokemonImageview);
        ab1TextView.setText(abilities[0]);
        ab2TextView.setText(abilities[1]);
        heightTextview.setText(getString(R.string.height_title, pokemon.getHeight()));
        weightTextview.setText(getString(R.string.weight_title, pokemon.getWeight()));
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
                        json.getJSONObject("id").opt("val").toString(),
                        json.getJSONObject("name").opt("val").toString(),
                        Integer.toString(0),
                        json.getJSONObject("height").opt("val").toString(),
                        json.getJSONObject("weight").opt("val").toString(),
                };

                StringBuilder sb = new StringBuilder();
                for (String aPrePokemon : prePokemon) {
                    sb.append(aPrePokemon);
                    sb.append(',');
                }
                pokemon = new Pokemon(Arrays.toString(prePokemon));

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
