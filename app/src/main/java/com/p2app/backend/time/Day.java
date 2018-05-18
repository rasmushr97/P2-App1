package com.p2app.backend.time;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Day {
    private Set<Meal> meals = new HashSet<>();

    public Set<Meal> getMeals() {
        return meals;
    }

    public Meal getMeal(int recipeID){
        for(Meal m : meals){
            if(m.getRecipe().getID() == recipeID){
                return m;
            }
        }
        return null;
    }

    public void addMeal(Meal m){
        meals.add(m);
    }

    public void deleteMeal(Meal m){
        meals.remove(m);
    }

    public int getCalorieSum(){
        int sum = 0;
        for(Meal m : meals){
            sum += m.getRecipe().getCalories();
        }
        return sum;
    }

    public void setMeals(Set<Meal> meals) {
        this.meals = meals;
    }
}
