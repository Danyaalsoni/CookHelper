package com.example.danyaal.cookhelper;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

public class MainActivity extends AppCompatActivity {
    RecipeDatabase db;
    FloatingActionButton fab;
    MaterialSearchView searchView;
    String[] typeSpinner = {"ALL","Main Dish","Starter", "Appetizer", "Side Dish", "Dessert","Drink","Sauce"};
    String[] categorySpinner = {"ALL","Italian", "American", "Canadian", "Indian", "Chinese","Greek","Colombian"};
    private ListView listView;
    private recipeAdapter adapter;
    String recipeType, recipeCategory;
    Cursor cursor;
    Button helpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Search for Recipe");
        toolbar.setTitleTextColor(Color.parseColor("#FFFFFF"));
        searchView = (MaterialSearchView) findViewById(R.id.search_view);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddRecipe.class);
                startActivity(intent);
            }
        });
        helpButton=(Button)findViewById(R.id.helpButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),HelpPage.class);
                startActivity(intent);
            }
        });
        db=new RecipeDatabase(this);
        cursor=db.getAllRecipes();
        cursor.moveToFirst();
        listView=(ListView)findViewById(R.id.list_view);

        adapter = new recipeAdapter(this,cursor,db);
        listView.setAdapter(adapter);


        MaterialSpinner typeMaterialspinner = (MaterialSpinner) findViewById(R.id.type_spinner);
        typeMaterialspinner.setItems(typeSpinner);
        recipeType = (String) typeMaterialspinner.getText();
        typeMaterialspinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                recipeType = item;
            }
        });
        MaterialSpinner categoryMaterialspinner = (MaterialSpinner) findViewById(R.id.category_spinner);
        categoryMaterialspinner.setItems(categorySpinner);
        recipeCategory = (String) categoryMaterialspinner.getText();
        categoryMaterialspinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                recipeCategory = item;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                adapter.swapCursor(cursor);
                listView.setAdapter(adapter);
            }
        });

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Cursor result=db.searchRecipe(query);
                adapter.swapCursor(result);
                    if (result == null) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setMessage("Recipe Not Found");
                        dialog.setTitle("Recipe Not Found");
                        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.setCancelable(false);
                        dialog.create().show();
                    }
                    return true;

            }
            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
