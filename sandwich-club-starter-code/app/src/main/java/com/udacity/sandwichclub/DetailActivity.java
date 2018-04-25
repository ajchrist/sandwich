package com.udacity.sandwichclub;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.databinding.ActivityDetailBinding;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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
        ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        populateUI(sandwich, binding);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(binding.imageIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich, ActivityDetailBinding binding) {
        binding.descriptionTv.setText(sandwich.getDescription());
        binding.ingredientsTv.setText(sandwich.getIngredients().toString().replaceAll("\\[", "").replaceAll("]", ""));
        boolean bool = sandwich.getAlsoKnownAs().toString().equals("[]");
        if (bool) {
            binding.alsoKnownTv.setText(String.format("Only known as %s", sandwich.getMainName()));
        } else {
            binding.alsoKnownTv.setText(sandwich.getAlsoKnownAs().toString().replaceAll("\\[", "").replaceAll("]", ""));
        }
        bool = sandwich.getPlaceOfOrigin().equals("");
        if (bool) {
            binding.originTv.setText(R.string.unknown_origin);
        } else {
            binding.originTv.setText(sandwich.getPlaceOfOrigin());
        }
    }
}
