package com.lclark.pokedex;

import android.os.Parcel;
import android.os.Parcelable;

import java.lang.reflect.Field;
import java.util.regex.Pattern;

/**
 * Created by alexfeldman on 4/12/16.
 */
public class Pokemon implements Parcelable {

    public String mPokemon_name;
    public String mPokemon_id;
    public String mPokemon_speciesId;
    public String mPokemon_height;
    public String mPokemon_weigth;

    public Pokemon(String csvString) {
        String[] split = csvString.trim().split(",");

        this.mPokemon_name = split[0];
        this.mPokemon_id = split[1];
        this.mPokemon_speciesId = split[2];
        this.mPokemon_height = split[3];
        this.mPokemon_weigth = split[4];
    }

    public String getName(boolean toUpper) {
        return (toUpper) ? titlize(mPokemon_name) : mPokemon_name;
    }

    public void setName(String mPokemon_name) {
        this.mPokemon_name = mPokemon_name;
    }

    public String getId() {
        return mPokemon_id;
    }

    public void setId(String mPokemon_id) {
        this.mPokemon_id = mPokemon_id;
    }

    public String getSpeciesId() {
        return mPokemon_speciesId;
    }

    public void setSpeciesId(String mPokemon_speciesId) {
        this.mPokemon_speciesId = mPokemon_speciesId;
    }

    public String getHeight() {
        return mPokemon_height;
    }

    public void setHeight(String mPokemon_height) {
        this.mPokemon_height = mPokemon_height;
    }

    public String getWeigth() {
        return mPokemon_weigth;
    }

    public void setWeigth(String mPokemon_weigth) {
        this.mPokemon_weigth = mPokemon_weigth;
    }

    public String getImageUrl() {
        return "http://img.pokemondb.net/artwork/" + getName(false) + ".jpg";
    }

    private static String titlize(String str) {

        if (str == null) {
            return null;
        }
        boolean isFirstLetterUpper = false;
        StringBuilder builder = new StringBuilder(str);
        final int len = builder.length();

        for (int i = 0; i < len; ++i) {
            char c = builder.charAt(i);
            if (!isFirstLetterUpper) {
                if (!Character.isWhitespace(c)) {
                    builder.setCharAt(i, Character.toUpperCase(c));
                    isFirstLetterUpper = true;
                }
            } else if (Character.isWhitespace(c) || (Pattern.matches("\\p{Punct}", "c"))) {
                isFirstLetterUpper = false;
            } else {
                builder.setCharAt(i, Character.toLowerCase(c));
            }
        }
        return builder.toString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mPokemon_name);
        dest.writeString(mPokemon_id);
        dest.writeString(mPokemon_speciesId);
        dest.writeString(mPokemon_height);
        dest.writeString(mPokemon_weigth);
    }

    public static final Parcelable.Creator<Pokemon> CREATOR = new Creator<Pokemon>(){
        @Override
        public Pokemon createFromParcel(Parcel source) {
            return new Pokemon(source);
        }

        @Override
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

    public Pokemon(Parcel source) {
        mPokemon_name = source.readString();
        mPokemon_id = source.readString();
        mPokemon_speciesId = source.readString();
        mPokemon_height = source.readString();
        mPokemon_weigth = source.readString();
    }
}
