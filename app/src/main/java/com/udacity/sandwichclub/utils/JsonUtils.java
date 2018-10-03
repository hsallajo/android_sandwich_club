package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static final String KEY_NAME = "name";
    public static final String KEY_MAIN_NAME = "mainName";
    public static final String KEY_ALSO_KNOWN_AS = "alsoKnownAs";
    public static final String KEY_PLACE_OF_ORIGIN = "placeOfOrigin";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        Sandwich s = null;

        try {

            JSONObject jsonObject = new JSONObject(json);
            JSONObject nameObject = jsonObject.getJSONObject(KEY_NAME);

            String mainName = nameObject.optString(KEY_MAIN_NAME);

            JSONArray alsoKnownAsArray = nameObject.optJSONArray(KEY_ALSO_KNOWN_AS);
            List<String> alsoKnownAs = new ArrayList<String>();
            int i=0;

            while(alsoKnownAsArray != null
                    && !alsoKnownAsArray.isNull(i)){
                alsoKnownAs.add(alsoKnownAsArray.optString(i));
                i++;
            }

            String placeOfOrigin = jsonObject.optString(KEY_PLACE_OF_ORIGIN);

            String description = jsonObject.optString(KEY_DESCRIPTION);

            String image = jsonObject.optString(KEY_IMAGE);

            JSONArray ingredientsArray = jsonObject.optJSONArray(KEY_INGREDIENTS);
            List<String> ingredients = new ArrayList<String>();
            i=0;

            while(ingredientsArray != null
                    && !ingredientsArray.isNull(i)){
                ingredients.add(ingredientsArray.optString(i));
                i++;
            }

            s = new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing JSON results", e);
        }

        return s;
    }
}
