package com.lclark.pokedex;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

/**
 * Created by alexfeldman on 4/12/16.
 */
public class PokemonAdapter extends BaseAdapter {
    public static class ViewHolder {
        TextView name, id, weight, height;
        ImageView image;
    }

    private ArrayList<Pokemon> mPokemons;
    private Context mContext;


    public PokemonAdapter(Context context, ArrayList<Pokemon> pokemons) {
        mPokemons = pokemons;
        mContext = context;
    }

    @Override
    public int getCount() {
        return (mPokemons != null) ? mPokemons.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mPokemons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.pokemon_individ_row, parent, false);
            TextView nameTextView = (TextView) convertView.findViewById(R.id.poke_row_name_textview);
            TextView idTextView = (TextView) convertView.findViewById(R.id.poke_row_id_textview);
            TextView weightTextView = (TextView) convertView.findViewById(R.id.poke_row_weight_textview);
            TextView heightTextView = (TextView) convertView.findViewById(R.id.poke_row_height_textview);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.poke_row_image);

            viewHolder = new ViewHolder();
            viewHolder.name = nameTextView;
            viewHolder.id = idTextView;
            viewHolder.height = heightTextView;
            viewHolder.weight = weightTextView;
            viewHolder.image = imageView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Pokemon pokemon = mPokemons.get(position);

        Picasso.with(mContext).load(pokemon.getImageUrl()).fit().centerInside().into(viewHolder.image);
        viewHolder.name.setText(pokemon.getName(true));
        viewHolder.id.setText(pokemon.getId());

        String weight = mContext.getString(R.string.weightString, pokemon.getWeigth());

        viewHolder.weight.setText(weight);
        String height = mContext.getString(R.string.heightString, pokemon.getHeight());
        viewHolder.height.setText(height);


        return convertView;
    }
}
