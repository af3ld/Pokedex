package com.lclark.pokedex;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
    private final ProgressDialog progressDialog;
    private boolean isRunning = true;
    private Context mContext;

    public Pokemon_JSON(Context context) {
        mContext = context;
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(true);
        progressDialog.setMessage(mContext.getString(R.string.catchphrase));
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancel(true);
            }
        });

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
        Log.d(TAG, mContext.getString(R.string.onStart));
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        isRunning = false;
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        super.onPostExecute(jsonObject);
        if (jsonObject == null) {
            Log.d(TAG, mContext.getString(R.string.onFail));
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
            if (!isRunning){
                connection.disconnect();
            }
            InputStreamReader inputStream = new InputStreamReader(connection.getInputStream());
            BufferedReader reader = new BufferedReader(inputStream);
            String line;
            while ((line = reader.readLine()) != null && isRunning) {
                response.append(line);
            }
            json = new JSONObject(response.toString());
        } catch (IOException | JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return json;
    }
}
