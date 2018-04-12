package com.example.rasmus.menucomplete.UI.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rasmus.menucomplete.R;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;


public class MealFragment extends Fragment {
    private String meal = "Breakfast";
    private int calories = 0;
    private int image = 0;

    public void setMeal(String meal) {
        this.meal = meal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_meal, container, false);

        setHasOptionsMenu(true);

        // pick up the bundle sent from MainActivity
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            image = bundle.getInt("img");
            calories = bundle.getInt("calories");
            meal = bundle.getString("meal");
        }

        // Initialization of the text above the picture describing the meal
        TextView mealText = (TextView) view.findViewById(R.id.text_meal_1);
        mealText.setText(meal);

        // Bring the markup lines to front
        View line1 = (View) view.findViewById(R.id.h_line_1);
        line1.bringToFront();
        View line2 = (View) view.findViewById(R.id.h_line_2);
        line1.bringToFront();

        // Initialization of the pie chart beside the picture describing the meal
        PieChart chart = (PieChart) view.findViewById(R.id.home_chart_1);
        List<PieEntry> pieChartEntries = new ArrayList<>();


        int goalCalories = 2500 - calories;
        pieChartEntries.add(new PieEntry(calories, "Meal"));
        pieChartEntries.add(new PieEntry(goalCalories, "Total"));
        setupPieChart(chart, pieChartEntries);

        // Initialization of the recipe's image
        ImageButton imgbtn = (ImageButton) view.findViewById(R.id.imgbtn_meal_1);
        // Change image to the input image
        imgbtn.setImageResource(image);

        return view;
    }


    private void setupPieChart(PieChart chart, List<PieEntry> entryList) {
        // Colors scheme for the pie chart
        final int[] MY_COLORS = {Color.rgb(0,100,0), Color.rgb(0,175,0)};
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for(int c: MY_COLORS) colors.add(c);

        // Create dataset for the pie chart
        PieDataSet dataSet = new PieDataSet(entryList, "Calories");

        // Applying color scheme
        dataSet.setColors(colors);
        dataSet.setDrawValues(false);


        // Bundle the dataset together
        PieData data = new PieData(dataSet);
        // Change font size


        // Use data in chart
        chart.setData(data);
        // Cool starting animation for the creation of the pie chart
        chart.animateY(750, Easing.EasingOption.EaseInOutQuad);
        String centerText = calories + " / " + 2500;
        chart.setCenterText(centerText);
        // Some default design features that are disabled
        chart.setTransparentCircleAlpha(0);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setRotationEnabled(false);
        chart.setEntryLabelTextSize(12f);

        chart.invalidate();
    }

}
