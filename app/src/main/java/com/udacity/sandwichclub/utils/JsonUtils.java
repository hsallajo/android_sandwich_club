package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = "JsonUtils";

    public static Sandwich parseSandwichJson(String json) {
        Log.d(TAG, "parseSandwichJson: " + json);
        Sandwich s = null;

        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONObject nameObject = jsonObject.getJSONObject("name");

            // main name
            String mainName = nameObject.optString("mainName");

            // also known as
            JSONArray alsoKnownAsArray = nameObject.optJSONArray("alsoKnownAs");
            List<String> alsoKnownAs = new ArrayList<String>();
            int i=0;
            while(alsoKnownAsArray != null && !alsoKnownAsArray.isNull(i)){
                alsoKnownAs.add(alsoKnownAsArray.optString(i));
                i++;
            }

            // place of origin
            String placeOfOrigin = jsonObject.optString("placeOfOrigin");

            // description
            String description = jsonObject.optString("description");

            // image
            String image = jsonObject.optString("image");

            // ingredients
            JSONArray ingredientsArray = jsonObject.optJSONArray("ingredients");
            List<String> ingredients = new ArrayList<String>();
            i=0;
            while(ingredientsArray != null && !ingredientsArray.isNull(i)){
                ingredients.add(ingredientsArray.optString(i));
                i++;
            }

            s = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the sandwhich JSON results", e);
        }

        return s;
    }
}
