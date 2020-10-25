package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ActivityDetailBinding viewBinding;
    private Sandwich sandwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewBinding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

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
        sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        bindUI();
        loadImage();
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void loadImage() {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(viewBinding.sandwichImage);
    }

    private void bindUI() {
        bindAlsoKnownAs();
        bindIngredients();
        bindOrigin();
        bindDescription();
    }

    private void bindAlsoKnownAs() {
        final List<String> alsoKnownAs = sandwich.getAlsoKnownAs();
        if (alsoKnownAs.isEmpty()) {
            viewBinding.alsoKnownAsLabel.setVisibility(View.GONE);
            viewBinding.alsoKnownAsText.setVisibility(View.GONE);
        } else
            viewBinding.alsoKnownAsText.setText(
                    formatListOfText(alsoKnownAs, R.string.formatted_dashed_text));
    }

    private void bindIngredients() {
        final List<String> ingredients = sandwich.getIngredients();
        if (ingredients.isEmpty()) {
            viewBinding.ingredientsLabel.setVisibility(View.GONE);
            viewBinding.ingredientsText.setVisibility(View.GONE);
        } else
            viewBinding.ingredientsText.setText(
                    formatListOfText(ingredients, R.string.formatted_bullet_text));
    }

    private String formatListOfText(List<String> listOfText, @StringRes int stringRes) {
        final StringBuilder stringBuilder = new StringBuilder();
        for (String text : listOfText)
            stringBuilder.append(getString(stringRes, text));
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("\n"));
        return stringBuilder.toString();
    }

    private void bindOrigin() {
        final String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin.isEmpty()) {
            viewBinding.originLabel.setVisibility(View.GONE);
            viewBinding.originText.setVisibility(View.GONE);
        } else
            viewBinding.originText.setText(placeOfOrigin);
    }

    private void bindDescription() {
        final String description = sandwich.getDescription();
        if (description.isEmpty()) {
            viewBinding.descriptionLabel.setVisibility(View.GONE);
            viewBinding.descriptionText.setVisibility(View.GONE);
        } else
            viewBinding.descriptionText.setText(description);
    }
}
