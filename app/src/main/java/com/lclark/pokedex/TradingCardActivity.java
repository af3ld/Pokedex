package com.lclark.pokedex;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.Bind;

/**
 * Created by alexfeldman on 4/12/16.
 */
public class TradingCardActivity extends AppCompatActivity {
    public static final String ARG_identifier = "Pokemon, plz";
    private Pokemon pokemon;

    @Bind(R.id.tradingcard_name) TextView nameTextview;
    @Bind(R.id.tradingcard_id) TextView idTextview;
    @Bind(R.id.tradingcard_height) TextView heightTextview;
    @Bind(R.id.tradingcard_weight) TextView weightTextview;
    @Bind(R.id.tradingcard_image) ImageView pokemonImageview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tradingcard);
        pokemon = getIntent().getParcelableExtra(ARG_identifier);
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
}
