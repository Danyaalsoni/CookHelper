package com.example.danyaal.cookhelper;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.danyaal.cookhelper.recipe.Recipe;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.IOException;

public class AddRecipe extends AppCompatActivity {
    public EditText addRecipeName;
    public EditText addIngredient;
    public EditText addDescription;
    public EditText addDirections;
    public EditText addCookingTime;
    public EditText addPrepTime;
    public EditText addCalories;
    public MaterialSpinner addRecipeType;
    public MaterialSpinner addRecipeCategory;
    public ImageView recipeImageButton;
    public Button addButton;
    private RecipeDatabase db;
    private int PICK_IMAGE_REQUEST=1;
    String recipeType="";
    String recipeCategory="";
    String recipeImage ="";
    String recipeName="";
    String recipeDescription="";
    String recipeIngredients = "";
    String recipeDirections = "";
    ProgressDialog progressDialog;
    int cookingTime;
    int prepTime;
    int calories;
    Recipe recipe = new Recipe();
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] typeSpinner = {"ALL","Main Dish","Starter", "Appetizer", "Side Dish", "Dessert","Drink","Sauce"};
        String[] categorySpinner = {"ALL","Italian", "American", "Canadian", "Indian", "Chinese","Greek","Colombian"};

        addRecipeName=(EditText) findViewById(R.id.editRecipeName);
        addIngredient=(EditText)findViewById(R.id.addIngredient);
        addDirections=(EditText)findViewById(R.id.addDirections);
        addDescription=(EditText)findViewById(R.id.addDescription);
        addCalories=(EditText)findViewById(R.id.editCalories);
        addCookingTime=(EditText)findViewById(R.id.editCookingTime);
        addPrepTime=(EditText) findViewById(R.id.editPreparationTime);
        recipeImageButton=(ImageView)findViewById(R.id.addRecipeImageView);

        recipeImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/jpeg");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });


        addButton=(Button)findViewById(R.id.addButton);
        db=new RecipeDatabase(this);

        addRecipeType = (MaterialSpinner) findViewById(R.id.add_type_spinner);
        addRecipeType.setItems(typeSpinner);
        addRecipeType.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                recipeType=item;
            }
        });

        addRecipeCategory = (MaterialSpinner) findViewById(R.id.add_category_spinner);
        addRecipeCategory.setItems(categorySpinner);
        addRecipeCategory.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                recipeCategory=item;
            }
        });

        addIngredient.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;

            }
        });
        addDirections.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
        addDescription.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                }
                return false;
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recipeName=addRecipeName.getText().toString();
                recipeDescription=addDescription.getText().toString();
                recipeIngredients=addIngredient.getText().toString();
                recipeDirections=addDirections.getText().toString();
                if(!addCookingTime.getText().toString().equals("")) {
                    cookingTime = Integer.parseInt(addCookingTime.getText().toString());
                }else{
                    cookingTime=0;
                }
                if(!addPrepTime.getText().toString().equals("")) {
                    prepTime = Integer.parseInt(addPrepTime.getText().toString());
                }else {
                    prepTime=0;
                }
                if(!addCalories.getText().toString().equals("")) {
                    calories = Integer.parseInt(addCalories.getText().toString());
                }else{
                    calories=0;
                }
                recipeType=addRecipeType.getText().toString();
                recipeCategory=addRecipeCategory.getText().toString();
                if(recipeName==null||recipeName.equals("")){
                    AlertDialog.Builder dialog=new AlertDialog.Builder(AddRecipe.this);
                    dialog.setMessage("Please enter Recipe Name");
                    dialog.setTitle("Error");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            addRecipeName.requestFocus();
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.create().show();
                }
                else if(recipeIngredients==null||recipeIngredients.equals("")){
                    AlertDialog.Builder dialog=new AlertDialog.Builder(AddRecipe.this);
                    dialog.setMessage("Please add ingredients");
                    dialog.setTitle("Error");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            addIngredient.requestFocus();
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.create().show();

                }
                else if(recipeDirections==null||recipeDirections.equals("")){
                    AlertDialog.Builder dialog=new AlertDialog.Builder(AddRecipe.this);
                    dialog.setMessage("Please enter Recipe Directions");
                    dialog.setTitle("Error");
                    dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            addDirections.requestFocus();
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.create().show();

                }

                else if(recipeDescription==null||recipeDescription.equals("")){
                    recipeDescription="No description provided";
                }
                else if(!recipeName.equals("")&&!recipeDirections.equals("")&&!recipeIngredients.equals("")){
                    recipe.setRecipeName(recipeName);
                    recipe.setType(recipeType);
                    recipe.setCategory(recipeCategory);
                    recipe.setDirections(recipeDirections);
                    recipe.setIngredients(recipeIngredients);
                    recipe.setDescription(recipeDescription);
                    recipe.setCookingTime(cookingTime);
                    recipe.setPrepTime(prepTime);
                    recipe.setCalories(calories);
                    recipe.setThumbnail(recipeImage);

                    db.insertRecipe(recipe);
                    Snackbar.make(v, "Successfully added Recipe", Snackbar.LENGTH_LONG).setAction("Action", null).show();
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

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                ImageView imageButton=(ImageView)findViewById(R.id.addRecipeImageView);
                imageButton.setImageBitmap(bitmap);
                System.out.println(uri.toString());
                recipeImage=uri.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
