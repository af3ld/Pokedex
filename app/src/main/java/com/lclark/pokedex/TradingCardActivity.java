package com.lclark.pokedex;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;

/**
 * Created by alexfeldman on 4/12/16.
 */
public class TradingCardActivity extends AppCompatActivity {

    public static final String TAG = TradingCardActivity.class.getSimpleName();
    public static final String ARG_identifier = "Pokemon, plz";
    private Pokemon pokemon;

    @Bind(R.id.tradingcard_name)
    TextView nameTextview;
    @Bind(R.id.tradingcard_id)
    TextView idTextview;
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
        JSONStringToPokemon(allData);

        Picasso.with(this).load(pokemon.getImageUrl()).fit().centerInside().into(pokemonImageview);
        nameTextview.setText(pokemon.getName(true));
        idTextview.setText(pokemon.getId());
        heightTextview.setText(getString(R.string.height_title, pokemon.getHeight()));
        weightTextview.setText(getString(R.string.weight_title, pokemon.getWeight()));

        ActionBar ab = getActionBar();
        if (ab != null) {
            ab.setTitle(pokemon.getName(true));
            ab.setSubtitle(pokemon.getSpeciesId());
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(ARG_identifier, pokemon.getName(true));
        setResult(RESULT_OK, intent);
        finish();
    }

    public String JSONStringToPokemon(String jsonString) {
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
                for (int i = 0; i < prePokemon.length; i++) {
                    sb.append(prePokemon[i]);
                    sb.append(',');
                }
                pokemon = new Pokemon(prePokemon.toString());
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getLocalizedMessage());
        }

    }
}
