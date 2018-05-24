package com.p2app.frontend.ui.recipePages;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.p2app.R;
import com.p2app.backend.InRAM;
import com.p2app.backend.Recommender;
import com.p2app.backend.recipeclasses.Recipe;
import com.p2app.frontend.AppBackButtonActivity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PickMealActivity extends AppBackButtonActivity {
    ListView listView;
    LocalDate date = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_meal);
        setTitle("Pick Meal");

        if (getIntent().getExtras() != null) {
            String dateInput = getIntent().getExtras().getString("date");
            date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        // Get ListView object from xml
        listView = findViewById(R.id.listview1);

        // Defined Array values to show in ListView
        final String[] values = {
                "Breakfast",
                "Lunch",
                "Dinner",
                "Other"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values){
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.rgb(51,51,51));
                return view;
            }
        };

        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);
                // Set the intention for which page to switch to
                Intent intent = new Intent(PickMealActivity.this, PickRecipeActivity.class);

                InRAM.mealsToMake = new HashMap<>();
                InRAM.mealsToMake.put(values[position], date);

                // Switch to the pick recipe page (PickRecipeActivity)
                startActivity(intent);

                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }

        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Find the meal class without a recipe, and delete it
        InRAM.mealsToMake = new HashMap<>();
    }


}

