package com.p2app.frontend.ui.recipePages;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.p2app.R;
import com.p2app.backend.InRAM;
import com.p2app.backend.recipeclasses.Ingredient;
import com.p2app.backend.recipeclasses.Recipe;
import com.p2app.backend.time.Day;
import com.p2app.backend.time.Meal;
import com.p2app.cloud.DBHandler;
import com.p2app.frontend.AppBackButtonActivity;
import com.p2app.frontend.adapters.DownloadImageTask;
import com.p2app.frontend.ui.homeScreenPages.MainActivity;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class RecipeClickedActivity extends AppBackButtonActivity {

    private int recipeID;
    private Recipe recipe;
    private boolean delete;
    private static DecimalFormat df = new DecimalFormat("#.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_clicked);
        setTitle("Recipe Picked");

        if (getIntent().getExtras() != null) {
            recipeID = getIntent().getExtras().getInt("id");
            delete = getIntent().getExtras().getBoolean("delete");
        }
        recipe = InRAM.recipesInRAM.get(recipeID);

        FloatingActionButton ratingBtn = (FloatingActionButton) findViewById(R.id.fabRate);
        ratingBtn.setOnClickListener(view -> {

            Dialog ratingDialog = new Dialog(RecipeClickedActivity.this, R.style.FullHeightDialog);
            ratingDialog.setContentView(R.layout.rating_dialog);
            ratingDialog.setCancelable(true);
            RatingBar ratingBar = (RatingBar) ratingDialog.findViewById(R.id.ratingbarDialog);

            if(InRAM.usersRatings.containsKey(recipeID)){
                ratingBar.setRating((float)InRAM.usersRatings.get(recipeID));
            }

            TextView text = (TextView) ratingDialog.findViewById(R.id.tvRatingDialog);
            text.setText(R.string.rate);

            Button updateButton = (Button) ratingDialog.findViewById(R.id.btnRatingDialog);
            updateButton.setOnClickListener(v -> {
                DBHandler.uploadRating(recipeID, (int)ratingBar.getRating());
                InRAM.usersRatings.put(recipeID, (int)ratingBar.getRating());
                ratingDialog.dismiss();
            });

            //now that the dialog is set up, it's time to show it
            ratingDialog.show();

        });

        new DownloadImageTask((ImageView) findViewById(R.id.RecipeImg)).execute(recipe.getPictureLink());

        TextView title = findViewById(R.id.recipeTitel);
        title.setText(recipe.getTitle());

        TextView rating = findViewById(R.id.recipeOverallRating);
        String ratingText = "Rating: " + df.format(recipe.getRating());
        System.out.println(recipe.getRating());
        rating.setText(ratingText);

        TextView calories = findViewById(R.id.RecipeCalories);
        String calorieText = "Calories: " + recipe.getCalories();
        calories.setText(calorieText);

        TextView time = findViewById(R.id.RecipeCookTime);
        String timeText = "Time: " + recipe.getTime().getReadyIn();
        time.setText(timeText);


        TextView ingredients = findViewById(R.id.RecipeItems);
        String ingredientsText = "";
        for (Ingredient ingredient : recipe.getIngredients()) {
            String inParentheses = "";
            if (!ingredient.getInParentheses().equals("")) {
                inParentheses = "(" + ingredient.getInParentheses() + ")";
            }
            ingredientsText = ingredientsText.concat(ingredient.getName() + " " + inParentheses + "\nAmount: " + df.format(ingredient.getAmount()) + " " + ingredient.getUnit() + "\n\n");
        }

        ingredientsText = ingredientsText.concat("\n\n");
        for (String direction : recipe.getDirections()) {
            ingredientsText = ingredientsText.concat(direction + "\n\n");
        }
        ingredients.setText(ingredientsText);


        // App bar back button and onClickListener
        FloatingActionButton infoButton = findViewById(R.id.btnInfo);
        infoButton.setOnClickListener(view -> {
            // Switches to the Description clicked page (activity)
            Intent intent = new Intent(RecipeClickedActivity.this, RecipeInfoAcitivty.class);
            intent.putExtra("id", recipeID);
            startActivity(intent);
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        });

        // Add recipe button and onClickListener
        FloatingActionButton addRecipeButton = findViewById(R.id.btnAddRecipe);
        if (delete) {
            addRecipeButton.setImageResource(R.drawable.ic_delete);
            addRecipeButton.setBackgroundTintList(ColorStateList.valueOf(Color
                    .parseColor("#aa0011")));
        } else {
            addRecipeButton.setImageResource(R.drawable.ic_add);
            addRecipeButton.setBackgroundTintList(ColorStateList.valueOf(Color
                    .parseColor("#D32F2F")));
        }
        addRecipeButton.setOnClickListener(view -> {

            if (delete) {
                Map<LocalDate, Day> daysInCalender = InRAM.calendar.getDates();

                String mealDate = getIntent().getExtras().getString("date");
                LocalDate date = LocalDate.parse(mealDate, DateTimeFormatter.ofPattern("d/M/yyyy"));

                InRAM.deleteMeal(date, daysInCalender.get(date).getMeal(recipeID));

                // Switches back to the home page (activity)
                Intent intent = new Intent(RecipeClickedActivity.this, MainActivity.class);
                // Clears all previous activities from the stack
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            } else {

                // TODO: throw an exception instead
                if (InRAM.mealsToMake.size() != 1) {
                    return;
                }

                String meal = "";
                for (String s : InRAM.mealsToMake.keySet()) {
                    meal = s;
                }
                LocalDate date = null;
                for (LocalDate d : InRAM.mealsToMake.values()) {
                    date = d;
                }
                InRAM.mealsToMake = new HashMap<>();

                InRAM.addMeal(date, new Meal(meal, InRAM.recipesInRAM.get(recipeID)));

                // Switches back to the home page (activity)
                Intent intent = new Intent(RecipeClickedActivity.this, MainActivity.class);
                // Clears all previous activities from the stack
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }

        });
    }
}
