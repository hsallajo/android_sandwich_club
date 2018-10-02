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
import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_sandwich) ImageView ingredientsIv;
    @BindView((R.id.also_known_tv)) TextView alsoKnownTV;
    @BindView(R.id.also_known_tv_label) TextView alsoKnownTVLabel;
    @BindView(R.id.origin_tv) TextView placeOfOrigin;
    @BindView(R.id.origin_tv_label) TextView placeOfOriginLabel;
    @BindView(R.id.ingredients_tv) TextView incredientsTV;
    @BindView(R.id.description_tv) TextView description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);

        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
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

        if(alsoKnown.equals("")){
            alsoKnownTV.setVisibility(View.GONE);
            alsoKnownTVLabel.setVisibility(View.GONE);
        }
        else {
            alsoKnownTV.setVisibility(View.VISIBLE);
            alsoKnownTV.setText(alsoKnown);
        }

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

        incredientsTV.setText(incredients);

        description.setText(sandwich.getDescription());
    }
}
