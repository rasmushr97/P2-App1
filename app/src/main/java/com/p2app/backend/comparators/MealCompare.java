package com.p2app.backend.comparators;

import com.p2app.backend.time.Meal;

import java.util.Comparator;

public class MealCompare implements Comparator<Meal> {
    @Override
    public int compare(Meal meal1, Meal meal2) {
        String[] meals = {meal1.getMealName(), meal2.getMealName()};
        int[] mealValue = new int[2];

        // Give both meals a int a value based on the order the meals should show
        for(int i = 0; i < 2; i++){
            switch (meals[i]){
                case "Breakfast":
                    mealValue[i] = 1;
                    break;

                case "Lunch":
                    mealValue[i] = 2;
                    break;

                case "Dinner":
                    mealValue[i] = 3;
                    break;

                case "Other":
                    mealValue[i] = 4;
                    break;
            }
        }


        return mealValue[0] - mealValue[1];
    }
}
