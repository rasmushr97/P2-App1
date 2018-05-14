package com.example.rasmus.p2app.frontend.ui.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rasmus.p2app.backend.InRAM;
import com.example.rasmus.p2app.backend.recipeclasses.Recipe;
import com.example.rasmus.p2app.frontend.AppBackButtonActivity;
import com.example.rasmus.p2app.R;
import com.example.rasmus.p2app.frontend.adapters.DownloadImageTask;

public class DescriptionRecipeClickedActivity extends AppBackButtonActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_recipe_clicked);
        setTitle("Description");

        int recipeID = getIntent().getExtras().getInt("id");
        Recipe recipe = InRAM.recipesInRAM.get(recipeID);

        new DownloadImageTask((ImageView) findViewById(R.id.ImageFromClickedRecipe)).execute(recipe.getPictureLink());
        TextView descriptionText = (TextView) findViewById(R.id.description_text);
        descriptionText.setText(recipe.getDescription());
        setTitle(recipe.getTitle());
    }
}
