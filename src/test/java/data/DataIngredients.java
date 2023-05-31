package data;

import java.util.ArrayList;

public class DataIngredients {
    private ArrayList<String> ingredients;

    public DataIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }

    public DataIngredients() {
    }

    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
}