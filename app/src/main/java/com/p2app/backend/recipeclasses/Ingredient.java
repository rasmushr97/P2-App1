package com.p2app.backend.recipeclasses;

import java.util.Objects;

public class Ingredient {
    private int ID;
    private String name;
    private double amount;
    private String unit;
    // Some times the ingredients have something in parentheses e.g. "1 can (300g) of beans
    private String inParentheses;

    public Ingredient(int ID, String name, double amount, String unit, String inParentheses) {
        this.ID = ID;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.inParentheses = inParentheses;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", amount=" + amount +
                ", unit='" + unit + '\'' +
                ", inParentheses='" + inParentheses + '\'' +
                '}';
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getInParentheses() {
        return inParentheses;
    }

    public void setInParentheses(String inParentheses) {
        this.inParentheses = inParentheses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
