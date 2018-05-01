package com.example.rasmus.p2app.backend;

import com.example.rasmus.p2app.cloud.DBHandler;
import com.example.rasmus.p2app.backend.recipeclasses.Recipe;
import com.example.rasmus.p2app.backend.time.Calendar;
import com.example.rasmus.p2app.backend.time.Day;
import com.example.rasmus.p2app.backend.userclasses.User;
import com.example.rasmus.p2app.frontend.exception.NoDBConnectionException;
import com.example.rasmus.p2app.frontend.exception.NoUserException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InRAM {

    public static Calendar calendar;
    public static User user;
    public static Day today;
    public static Map<Integer, Recipe> recipesInRAM = new HashMap<>();


    //TODO: login method

    public static void initializeUser(int ID) {
        user = DBHandler.getUser(ID);
    }

    public static void initializeTodaysRecipes() {

        if (user == null) {
            throw new NoUserException();
        }

        // TODO: get recipe from databaseHandler based on the dates
        // maybe get recipes from the recommender systems
        try{
            calendar = DBHandler.getCalender(user.getID());
            Map<LocalDate, Day> days = calendar.getDates();
            today = days.get(LocalDate.now());
        }catch (NoDBConnectionException e){
            System.out.println("no connection");
        }
    }

    public static void addRecipesToRam(List<Integer> IDList) {
        List<Recipe> recipes = DBHandler.getRecipesFromIDs(IDList);
        for (Recipe r : recipes) {
            recipesInRAM.put(r.getID(), r);
        }
    }


}
