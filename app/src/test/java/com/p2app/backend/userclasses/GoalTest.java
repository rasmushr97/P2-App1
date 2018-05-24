package com.p2app.backend.userclasses;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class GoalTest {

    private LocalUser createUser(boolean male, boolean loseWeight){
        LocalUser user = new LocalUser();
        user.setAge(25);
        user.setHeight(180);
        user.setWeight(90);
        user.setMale(male);
        if(loseWeight){
            user.setGoalWeight(80);
            user.setWantLoseWeight(1);
        } else{
            user.setGoalWeight(100);
            user.setWantLoseWeight(0);
        }
        return user;
    }


    @Test
    public void firstDateTest01(){
        Goal goal = new Goal();
        goal.addUserWeight(LocalDate.of(2018,1,1), 80);
        goal.addUserWeight(LocalDate.of(2018,2,2), 80);
        goal.addUserWeight(LocalDate.of(2018,2,1), 80);
        goal.addUserWeight(LocalDate.of(2018,1,2), 80);
        assertEquals(LocalDate.of(2018,1,1), Goal.getFirstDate(Goal.getUserWeight()));
    }

    @Test
    public void firstDateTest02(){
        Goal goal = new Goal();
        Goal.getUserWeight().clear();
        goal.addUserWeight(LocalDate.of(2018,1,1), 80);
        goal.addUserWeight(LocalDate.of(2018,2,2), 80);
        goal.addUserWeight(LocalDate.of(2017,2,2), 80);
        goal.addUserWeight(LocalDate.of(2018,1,2), 80);
        assertEquals(LocalDate.of(2017,2,2), Goal.getFirstDate(Goal.getUserWeight()));
    }

    @Test
    public void lastDateTest01(){
        Goal goal = new Goal();
        goal.addUserWeight(LocalDate.of(2018,1,1), 80);
        goal.addUserWeight(LocalDate.of(2018,2,2), 80);
        goal.addUserWeight(LocalDate.of(2018,2,1), 80);
        goal.addUserWeight(LocalDate.of(2018,1,2), 80);
        assertEquals(LocalDate.of(2018,2,2), Goal.getLastDate(Goal.getUserWeight()));
    }

    @Test
    public void lastDateTest02(){
        Goal goal = new Goal();
        Goal.getUserWeight().clear();
        goal.addUserWeight(LocalDate.of(2018,1,1), 80);
        goal.addUserWeight(LocalDate.of(2017,2,2), 80);
        goal.addUserWeight(LocalDate.of(2017,2,1), 80);
        goal.addUserWeight(LocalDate.of(2017,1,2), 80);
        assertEquals(LocalDate.of(2018,1,1), Goal.getLastDate(Goal.getUserWeight()));
    }

    @Test
    public void calcGoalDateTest01(){
        LocalUser user = createUser(true, true);
        Goal.goalStartDate = LocalDate.of(2018,1,1);
        user.getGoal().addUserWeight(Goal.goalStartDate, 90);
        assertTrue(Goal.goalStartDate.isBefore(user.getGoal().calcGoalDate(user)));
    }

    @Test
    public void calcGoalDateTest02(){
        LocalUser user = createUser(true, false);
        Goal.getUserWeight().clear();
        Goal.goalStartDate = LocalDate.of(2018,1,1);
        user.getGoal().addUserWeight(Goal.goalStartDate, 90);
        assertTrue(Goal.goalStartDate.isBefore(user.getGoal().calcGoalDate(user)));
    }

    @Test
    public void caloriesPerDayTest01(){
        LocalUser user = createUser(true, false);
        user.getGoal().calcCaloriesPerDay(user);
        assertTrue(user.getCaloriesPerDay() > 500);
    }

    @Test
    public void caloriesPerDayTest02(){
        LocalUser user = createUser(true, true);
        user.getGoal().calcCaloriesPerDay(user);
        assertTrue(user.getCaloriesPerDay() > 0);
    }

    @Test
    public void caloriesPerDayTest03(){
        LocalUser user = createUser(true, true);
        user.setWeight(150);
        user.getGoal().calcCaloriesPerDay(user);
        assertTrue(user.getCalorieDeficit() == 1000);
    }

    @Test
    public void caloriesPerDayTest04(){
        LocalUser user = createUser(true, true);
        user.setWeight(80);
        user.setGoalWeight(80);
        user.getGoal().calcCaloriesPerDay(user);
        assertEquals(0, user.getCalorieDeficit());
    }

    @Test
    public void caloriesPerDayTest05(){
        LocalUser user = createUser(true, false);
        user.setWeight(80);
        user.setGoalWeight(80);
        double BMR = user.getGoal().RHB_Equation(user);
        user.getGoal().calcCaloriesPerDay(user);
        assertEquals((int)(BMR * user.getExerciseLvl()), user.getCaloriesPerDay());
    }

    @Test
    public void calDeficitTest01(){
        LocalUser user = createUser(true, true);
        assertEquals(1000, user.getGoal().calDeficit(35));
    }

    @Test
    public void calDeficitTest02(){
        LocalUser user = createUser(true, true);
        assertNotEquals(1000, user.getGoal().calDeficit(25));
    }

    @Test
    public void calDeficitTest03(){
        LocalUser user = createUser(true, true);
        assertTrue(500 > user.getGoal().calDeficit(22));
    }

    @Test //Can use in report
    public void RHB_EquationTest01(){
        LocalUser maleUser = createUser(true, true);
        double maleBMR = maleUser.getGoal().RHB_Equation(maleUser);
        LocalUser femaleUser = createUser(false, true);
        double femaleBMR = femaleUser.getGoal().RHB_Equation(femaleUser);
        assertTrue(maleBMR > femaleBMR);
    }

    @Test
    public void getGoalWeightTest01(){
        Goal.getGoalWeight().clear();
        assertTrue(Goal.getGoalWeight().isEmpty());
    }




}