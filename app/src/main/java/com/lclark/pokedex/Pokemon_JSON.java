package com.lclark.pokedex;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by alexfeldman on 4/12/16.
 */
public class Pokemon_JSON extends AsyncTask<String, Integer, JSONObject> {

    public static final String TAG = Pokemon_JSON.class.getSimpleName();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Log.d(TAG, "Started Pokédex");
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        if (jsonObject == null) {
            Log.e(TAG, "JSONObject is null");
        } else {
            Log.d(TAG, "It worked!");
        }
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        StringBuilder response = new StringBuilder();
        JSONObject json = null;
        URL url;
        String id = params[0];
        try {
            url = new URL("http://pokeapi.co/api/v2/pokemon/" + id + "/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStream);
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            json = new JSONObject(response.toString());
        } catch (IOException | JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return json;
    }
}
