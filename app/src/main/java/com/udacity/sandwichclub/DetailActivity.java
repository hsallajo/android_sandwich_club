package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.ListIterator;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    public static final String TAG = "DetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_sandwich);

        Intent intent = getIntent();
        if (intent == null) {
            Log.d(TAG, "Intent has null value");
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        Log.d(TAG, "onCreate position: " + position);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            Log.d(TAG, "EXTRA_POSITION not found in intent");
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            Log.d(TAG, "Sandwich data not available.");
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .placeholder(R.drawable.ic_baseline_broken_image_24px)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        String alsoKnown = "";
        if(!sandwich.getAlsoKnownAs().isEmpty() && sandwich.getAlsoKnownAs() != null){
            StringBuilder b = new StringBuilder();
            ListIterator<String> i = sandwich.getAlsoKnownAs().listIterator();
            while(i.hasNext()){
                b.append(i.next());
                if(i.hasNext())
                    b.append(", ");
            }

            alsoKnown = b.toString();
        }

        TextView alsoKnownTV = findViewById(R.id.also_known_tv);
        TextView alsoKnownTVLabel = findViewById(R.id.also_known_tv_label);
        if(alsoKnown.equals("")){
            alsoKnownTV.setVisibility(View.GONE);
            alsoKnownTVLabel.setVisibility(View.GONE);
        }
        else {
            alsoKnownTV.setVisibility(View.VISIBLE);
            alsoKnownTV.setText(alsoKnown);
        }

        TextView placeOfOrigin = findViewById(R.id.origin_tv);
        TextView placeOfOriginLabel = findViewById(R.id.origin_tv_label);
        if(sandwich.getPlaceOfOrigin().equals("")) {
            placeOfOrigin.setVisibility(View.GONE);
            placeOfOriginLabel.setVisibility(View.GONE);
        }
        else{
            placeOfOrigin.setVisibility(View.VISIBLE);
            placeOfOrigin.setText(sandwich.getPlaceOfOrigin());
        }

        StringBuilder incredientsBuilder = new StringBuilder();
        String incredients = "";
        if(!sandwich.getIngredients().isEmpty() && sandwich.getIngredients() != null){
            ListIterator<String> i = sandwich.getIngredients().listIterator();
            while(i.hasNext()){
                incredientsBuilder.append(i.next());
                if(i.hasNext())
                    incredientsBuilder.append(", ");
            }

            incredients = incredientsBuilder.toString();
        }

        TextView incredientsTV = findViewById(R.id.ingredients_tv);
        incredientsTV.setText(incredients);

        TextView description = findViewById(R.id.description_tv);
        description.setText(sandwich.getDescription());
    }
}
