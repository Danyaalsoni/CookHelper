package com.example.danyaal.cookhelper;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class recipeInfo extends AppCompatActivity {
    RecipeDatabase db;
    int recipeId;
    String recipeName;
    String recipeImage;
    TextView ingredients;
    TextView description;
    TextView directions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recipeId=getIntent().getIntExtra("key_id",0);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),EditRecipe.class);
                intent.putExtra("key_id",recipeId);
                startActivity(intent);
            }
        });




        ingredients=(TextView)findViewById(R.id.ingredientsText);
        description=(TextView)findViewById(R.id.cookingInfoText);
        directions=(TextView)findViewById(R.id.descriptionText);
        db=new RecipeDatabase(this);
        Cursor rs=db.getRecipe(recipeId);
        rs.moveToFirst();
        recipeName=rs.getString(rs.getColumnIndex(db.RECIPE_COLUMN_NAME));
        recipeImage=rs.getString(rs.getColumnIndex(db.RECIPE_COLUMN_IMAGE));
        String recipeIngredients=rs.getString(rs.getColumnIndex(db.RECIPE_COLUMN_INGREDIENTS));
        String recipeDirections=rs.getString(rs.getColumnIndex(db.RECIPE_COLUMN_DIRECTIONS));
        String recipeDescription=rs.getString(rs.getColumnIndex(db.RECIPE_COLUMN_OVERVIEW));
        String recipeType=rs.getString(rs.getColumnIndex(db.RECIPE_COLUMN_TYPE));
        String recipeCategory=rs.getString(rs.getColumnIndex(db.RECIPE_COLUMN_CATEGORY));
        int recipeCookTime=rs.getInt(rs.getColumnIndex(db.RECIPE_COLUMN_COOKTIME));
        int recipePrepTime=rs.getInt(rs.getColumnIndex(db.RECIPE_COLUMN_PREPTIME));
        int recipeCalories=rs.getInt(rs.getColumnIndex(db.RECIPE_COLUMN_CALORIES));
        ingredients.setText(recipeIngredients);
        String cookinginfo="Cooking Time: " + Integer.toString(recipeCookTime)+" minutes"+"\n"+
                "Preparation Time: "+Integer.toString(recipePrepTime)+" minutes"+"\n"+
                "Calories: " +Integer.toString(recipeCalories)+"\n"+
                "Type: "+recipeType+"\n"+
                "Category: "+recipeCategory+"\n\n"+"Recipe Description:"+"\n"+
                recipeDescription;
        description.setText(cookinginfo);
        directions.setText(recipeDirections);
        if(!rs.isClosed()){
            rs.close();
        }


        initCollapsingToolbar();

    }
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbar.setTitle(recipeName);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.setExpanded(true);
        ImageView recipeback=(ImageView) findViewById(R.id.backgroundpic);
            try{
                Picasso.get().load(recipeImage).into(recipeback);
            }catch(Exception exception) {
                exception.printStackTrace();
            }




    }
}
