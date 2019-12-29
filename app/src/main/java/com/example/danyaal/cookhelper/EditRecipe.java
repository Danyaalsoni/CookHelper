package com.example.danyaal.cookhelper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.danyaal.cookhelper.recipe.Recipe;
import com.jaredrummler.materialspinner.MaterialSpinner;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class EditRecipe extends AppCompatActivity {
    int recipeId;
    RecipeDatabase db;
    String recipeType,recipeCategory;
    String[] typeSpinner = {"ALL","Main Dish","Starter", "Appetizer", "Side Dish", "Dessert","Drink","Sauce"};
    String[] categorySpinner = {"ALL","Italian", "American", "Canadian", "Indian", "Chinese","Greek","Colombian"};

    String recipeImage;
    Button deleteButton,updateButton;
    String recipeName;
    String recipeIngredients;
    String recipeDirections;
    String recipeDescription;
    int recipeCookTime;
    int recipePrepTime;
    int recipeCalories;
    EditText updateRecipeName ;
    EditText updateRecipeDescription;
    EditText updateRecipeIngredients;
    EditText updateRecipePrepTime;
    EditText updateRecipeCalories;
    EditText updateRecipeDirections;
    EditText updateRecipeCookingTime;
    MaterialSpinner updateRecipeType;
    MaterialSpinner updateRecipeCategory;
    Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);
        recipeId=getIntent().getIntExtra("key_id",0);
        db=new RecipeDatabase(this);
        Cursor rs=db.getRecipe(recipeId);
        rs.moveToFirst();
         recipeName=rs.getString(rs.getColumnIndex(db.RECIPE_COLUMN_NAME));
         recipeImage=rs.getString(rs.getColumnIndex(db.RECIPE_COLUMN_IMAGE));
         recipeIngredients=rs.getString(rs.getColumnIndex(db.RECIPE_COLUMN_INGREDIENTS));
         recipeDirections=rs.getString(rs.getColumnIndex(db.RECIPE_COLUMN_DIRECTIONS));
         recipeDescription=rs.getString(rs.getColumnIndex(db.RECIPE_COLUMN_OVERVIEW));
        recipeType=rs.getString(rs.getColumnIndex(db.RECIPE_COLUMN_TYPE));
        recipeCategory=rs.getString(rs.getColumnIndex(db.RECIPE_COLUMN_CATEGORY));
        recipeCookTime=rs.getInt(rs.getColumnIndex(db.RECIPE_COLUMN_COOKTIME));
        recipePrepTime=rs.getInt(rs.getColumnIndex(db.RECIPE_COLUMN_PREPTIME));
        recipeCalories=rs.getInt(rs.getColumnIndex(db.RECIPE_COLUMN_CALORIES));
        if(!rs.isClosed()){
            rs.close();
        }
        recipe=new Recipe(recipeName,recipeType,recipeCategory,recipeDirections,recipeIngredients,recipeDescription,recipeCalories,recipeCookTime,recipePrepTime,recipeImage);

        updateRecipeName = findViewById(R.id.updateRecipeName);
        updateRecipeDescription=findViewById(R.id.updateDescription);
        updateRecipeDirections= findViewById(R.id.updateDirections);
        updateRecipeIngredients= findViewById(R.id.updateIngredient);
        updateRecipeCookingTime= findViewById(R.id.updateCookingTime);
        updateRecipePrepTime= findViewById(R.id.updatePreparationTime);
        updateRecipeCalories= findViewById(R.id.updateCalories);
        updateRecipeType=findViewById(R.id.update_type_spinner);
        updateRecipeType.setItems(typeSpinner);
        updateRecipeType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                recipeType= item;
            }
        });
        updateRecipeCategory= findViewById(R.id.update_category_spinner);
        updateRecipeCategory.setItems(categorySpinner);
        updateRecipeCategory.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                recipeCategory= item;
            }
        });
        ImageView updateRecipeImage = findViewById(R.id.updateRecipeImage);
        updateRecipeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/jpeg");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
        updateRecipeName.setText(recipeName);
        updateRecipeDescription.setText(recipeDescription);
        updateRecipeCalories.setText(Integer.toString(recipeCalories));
        updateRecipeCategory.setText(recipeCategory);
        updateRecipeType.setText(recipeType);
        updateRecipeDirections.setText(recipeDirections);
        updateRecipePrepTime.setText(Integer.toString(recipePrepTime));
        updateRecipeCookingTime.setText(Integer.toString(recipeCookTime));
        updateRecipeIngredients.setText(recipeIngredients);
        Picasso.get().load(recipeImage).into(updateRecipeImage);

        deleteButton=findViewById(R.id.deleteRecipeButton);
        updateButton=findViewById(R.id.updateButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteRecipe(recipeId);
                Snackbar.make(v, "Successfully deleted Recipe", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeName=updateRecipeName.getText().toString();
                recipeIngredients=updateRecipeIngredients.getText().toString();
                recipeDirections=updateRecipeDirections.getText().toString();
                recipeDescription=updateRecipeDescription.getText().toString();
                recipeType=updateRecipeType.getText().toString();
                recipeCategory=updateRecipeCategory.getText().toString();
                recipeCookTime=Integer.parseInt(updateRecipeCookingTime.getText().toString());
                recipePrepTime=Integer.parseInt(updateRecipePrepTime.getText().toString());
                recipeCalories=Integer.parseInt(updateRecipeCalories.getText().toString());
                if(!updateRecipeCookingTime.getText().toString().equals("")) {
                    recipeCookTime = Integer.parseInt(updateRecipeCookingTime.getText().toString());
                }else{
                    recipeCookTime=0;
                }
                if(!updateRecipePrepTime.getText().toString().equals("")) {
                    recipePrepTime= Integer.parseInt(updateRecipePrepTime.getText().toString());
                }else {
                    recipePrepTime=0;
                }
                if(!updateRecipeCalories.getText().toString().equals("")) {
                    recipeCalories = Integer.parseInt(updateRecipeCalories.getText().toString());
                }else{
                    recipeCalories=0;
                }
                if(recipeName==null||recipeName.equals("")){
                    AlertDialog.Builder dialog=new AlertDialog.Builder(EditRecipe.this);
                    dialog.setMessage("Please enter Recipe Name");
                    dialog.setTitle("Error");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateRecipeName.requestFocus();
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.create().show();
                }
                else if(recipeIngredients==null||recipeIngredients.equals("")){
                    AlertDialog.Builder dialog=new AlertDialog.Builder(EditRecipe.this);
                    dialog.setMessage("Please add ingredients");
                    dialog.setTitle("Error");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateRecipeIngredients.requestFocus();
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.create().show();

                }
                else if(recipeDirections==null||recipeDirections.equals("")){
                    AlertDialog.Builder dialog=new AlertDialog.Builder(EditRecipe.this);
                    dialog.setMessage("Please enter Recipe Directions");
                    dialog.setTitle("Error");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            updateRecipeDirections.requestFocus();
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.create().show();

                }

                else if(recipeDescription==null||recipeDescription.equals("")){
                    recipeDescription="No description provided";
                }
                else if(!recipeName.equals("") && !recipeDirections.equals("") && !recipeIngredients.equals("")) {
                    recipe.setRecipeName(recipeName);
                    recipe.setType(recipeType);
                    recipe.setCategory(recipeCategory);
                    recipe.setDirections(recipeDirections);
                    recipe.setIngredients(recipeIngredients);
                    recipe.setDescription(recipeDescription);
                    recipe.setCookingTime(recipeCookTime);
                    recipe.setPrepTime(recipePrepTime);
                    recipe.setCalories(recipeCalories);
                    recipe.setThumbnail(recipeImage);
                    //db.updateRecipe(recipeId, recipeName, recipeIngredients, recipeDirections, recipeDescription, recipeType, recipeCategory, recipeCookTime, recipePrepTime, recipeCalories, recipeImage);
                    db.updateRecipe(recipeId,recipe);
                    Snackbar.make(v, "Successfully updated Recipe", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageButton=(ImageView)findViewById(R.id.updateRecipeImage);
                imageButton.setImageBitmap(bitmap);

                recipeImage=uri.toString();
                //recipeImage=stream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
