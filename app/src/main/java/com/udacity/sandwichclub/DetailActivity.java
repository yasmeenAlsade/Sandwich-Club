package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView textViewPlaceOfOrigin, textViewAlsoKnownAs, textViewIngredients, textViewDescription;
    private LinearLayout linearLayoutPlaceOfOrigin, linearLayoutAlsoKnownAs, linearLayoutIngredients, linearLayoutDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        textViewPlaceOfOrigin = findViewById(R.id.origin_tv);
        textViewAlsoKnownAs = findViewById(R.id.also_known_tv);
        textViewIngredients = findViewById(R.id.ingredients_tv);
        textViewDescription = findViewById(R.id.description_tv);
        linearLayoutPlaceOfOrigin = findViewById(R.id.origin_layout);
        linearLayoutAlsoKnownAs = findViewById(R.id.also_known_layout);
        linearLayoutIngredients = findViewById(R.id.ingredients_layout);
        linearLayoutIngredients = findViewById(R.id.ingredients_layout);

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
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        List<String> ingredients = sandwich.getIngredients();
        String description = sandwich.getDescription();

        if (placeOfOrigin.isEmpty() == true) {
            linearLayoutPlaceOfOrigin.setVisibility(View.GONE);
        } else {
            textViewPlaceOfOrigin.setText(" " + placeOfOrigin);
        }

        if (alsoKnownAs.size() == 0) {
            linearLayoutAlsoKnownAs.setVisibility(View.GONE);
        } else {
            textViewAlsoKnownAs.setText(" ");
            textViewAlsoKnownAs.append(alsoKnownAs.toString(), 1, alsoKnownAs.toString().length() - 1);
        }

        if (ingredients.size() == 0) {
            linearLayoutIngredients.setVisibility(View.GONE);
        } else {
            textViewIngredients.setText(" ");
            textViewIngredients.append(ingredients.toString(), 1, ingredients.toString().length() - 1);
        }

        if (description.isEmpty() == true) {
            linearLayoutDescription.setVisibility(View.GONE);
        } else {
            textViewDescription.setText(" " + description);
        }
    }
}
