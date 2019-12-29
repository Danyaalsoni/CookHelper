package com.example.danyaal.cookhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.danyaal.cookhelper.recipe.Recipe;

/**
 * Created by danya on 11/18/2016.
 */

public class RecipeDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="recipeDatabase";
    public static final String RECIPE_TABLE_NAME="Recipe";
    public static final String RECIPE_COLUMN_ID="_id";
    public static final String RECIPE_COLUMN_NAME="RecipeName";
    public static final String RECIPE_COLUMN_TYPE="RecipeType";
    public static final String RECIPE_COLUMN_CATEGORY="RecipeCategory";
    public static final String RECIPE_COLUMN_COOKTIME="CookingTime";
    public static final String RECIPE_COLUMN_PREPTIME="PreparationTime";
    public static final String RECIPE_COLUMN_CALORIES="Calories";
    public static final String RECIPE_COLUMN_IMAGE="RecipeImage";
    public static final String RECIPE_COLUMN_OVERVIEW="RecipeOverview";
    public static final String RECIPE_COLUMN_DIRECTIONS="RecipeDirections";
    public static final String RECIPE_COLUMN_INGREDIENTS="RecipeIngredients";

    public RecipeDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //adding table with all the values
        db.execSQL("CREATE TABLE " +RECIPE_TABLE_NAME+"("+
        RECIPE_COLUMN_ID+" INTEGER PRIMARY KEY,"+
        RECIPE_COLUMN_NAME+" TEXT, "+
        RECIPE_COLUMN_OVERVIEW+" TEXT, "+
        RECIPE_COLUMN_TYPE+" TEXT, "+
        RECIPE_COLUMN_CATEGORY+" TEXT, "+
        RECIPE_COLUMN_COOKTIME+" INTEGER, "+
        RECIPE_COLUMN_PREPTIME+" INTEGER, "+
        RECIPE_COLUMN_CALORIES+" INTEGER, " +
        RECIPE_COLUMN_DIRECTIONS+" TEXT, "+
        RECIPE_COLUMN_INGREDIENTS+" TEXT, "+
        RECIPE_COLUMN_IMAGE+" TEXT"+");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+RECIPE_TABLE_NAME);
        onCreate(db);
    }
    public boolean insertRecipe(Recipe recipe){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(RECIPE_COLUMN_NAME,recipe.getRecipeName());
        contentValues.put(RECIPE_COLUMN_OVERVIEW,recipe.getDescription());
        contentValues.put(RECIPE_COLUMN_TYPE,recipe.getType());
        contentValues.put(RECIPE_COLUMN_CATEGORY,recipe.getCategory());
        contentValues.put(RECIPE_COLUMN_COOKTIME,recipe.getCookingTime());
        contentValues.put(RECIPE_COLUMN_PREPTIME,recipe.getPrepTime());
        contentValues.put(RECIPE_COLUMN_CALORIES,recipe.getCalories());
        contentValues.put(RECIPE_COLUMN_DIRECTIONS,recipe.getDirections());
        contentValues.put(RECIPE_COLUMN_INGREDIENTS,recipe.getIngredients());
        contentValues.put(RECIPE_COLUMN_IMAGE,recipe.getImage());
        db.insert(RECIPE_TABLE_NAME,null,contentValues);
        return true;
    }
    public boolean updateRecipe(Integer id, Recipe recipe){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(RECIPE_COLUMN_NAME,recipe.getRecipeName());
        contentValues.put(RECIPE_COLUMN_OVERVIEW,recipe.getDescription());
        contentValues.put(RECIPE_COLUMN_TYPE,recipe.getType());
        contentValues.put(RECIPE_COLUMN_CATEGORY,recipe.getCategory());
        contentValues.put(RECIPE_COLUMN_COOKTIME,recipe.getCookingTime());
        contentValues.put(RECIPE_COLUMN_PREPTIME,recipe.getPrepTime());
        contentValues.put(RECIPE_COLUMN_CALORIES,recipe.getCalories());
        contentValues.put(RECIPE_COLUMN_INGREDIENTS,recipe.getIngredients());
        contentValues.put(RECIPE_COLUMN_DIRECTIONS,recipe.getDirections());
        contentValues.put(RECIPE_COLUMN_IMAGE,recipe.getImage());

        db.update(RECIPE_TABLE_NAME,contentValues,RECIPE_COLUMN_ID +" = ? ",new String[]{Integer.toString(id)});

        return true;
    }
    public Cursor getRecipe(int id){
        SQLiteDatabase db=getReadableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM "+RECIPE_TABLE_NAME+" WHERE "+RECIPE_COLUMN_ID +"=?",new String[]{Integer.toString(id)});
        return res;
    }
    public Cursor getAllRecipes(){
        SQLiteDatabase db=getReadableDatabase();
        Cursor res=db.rawQuery("SELECT * FROM " +RECIPE_TABLE_NAME,null);
        return res;
    }
    public Cursor searchRecipe(String s){
        SQLiteDatabase db=getReadableDatabase();
        String query="SELECT * FROM "+RECIPE_TABLE_NAME+" WHERE "+RECIPE_COLUMN_INGREDIENTS +"  LIKE  '%" +s+"%' ";
        Cursor res=db.rawQuery(query,null);
        if(res==null){
            return null;
        }else if(!res.moveToFirst()){
            res.close();
            return null;
        }
        return res;
    }

    public Integer deleteRecipe(Integer id){
        SQLiteDatabase db=getWritableDatabase();
        return db.delete(RECIPE_TABLE_NAME,RECIPE_COLUMN_ID+" = ? ",new String[]{Integer.toString(id)});
    }
}
