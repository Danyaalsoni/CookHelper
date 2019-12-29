package com.example.danyaal.cookhelper.recipe;

/**
 * Created by danyaal on 11/5/2016.
 */

public class Recipe {
    private String recipeName;
    private String ingredients;
    private String type,category;
    private String directions;
    private String description;
    private int calories;
    private int cookingTime;
    private int prepTime;
    private String thumbnail;
    public Recipe(String recipeName,String type,String category,String directions,String ingredients,String description,int calories,int cookingTime,int prepTime,String thumbnail){
        this.recipeName=recipeName;
        this.type=type;
        this.category=category;
        this.ingredients=ingredients;
        this.directions=directions;
        this.description=description;
        this.calories=calories;
        this.cookingTime=cookingTime;
        this.prepTime=prepTime;
        this.thumbnail=thumbnail;
    }
    public Recipe(){

    }
    public void setThumbnail(String thumbnail){
        this.thumbnail=thumbnail;
    }
    public String getImage(){
        return this.thumbnail;
    }
    public void setRecipeName(String recipeName){
        this.recipeName=recipeName;
    }
    public void setIngredients(String ingredients){
        this.ingredients=ingredients;
    }
    public void setDirections(String directions){
        this.directions=directions;
    }
    public void setType(String type){
        this.type=type;
    }
    public void setCategory(String category){
        this.category=category;
    }
    public void setDescription(String description){
        this.description=description;
    }
    public void setCalories(int calories){
        this.calories=calories;
    }
    public void setCookingTime(int cookingTime){
        this.cookingTime=cookingTime;
    }
    public void setPrepTime(int prepTime){
        this.prepTime=prepTime;
    }
    public String getRecipeName(){
        return recipeName;
    }
    public String getType(){
        return type;
    }
    public String getIngredients(){
        return ingredients;
    }
    public String getCategory(){
        return category;
    }
    public String getDirections(){
        return directions;
    }
    public String getDescription(){return description;}
    public int getCalories(){return calories;}
    public int getCookingTime(){return cookingTime;}
    public int getPrepTime(){return prepTime;}
}
