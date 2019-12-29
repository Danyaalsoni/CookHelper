package com.example.danyaal.cookhelper;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import com.google.android.material.snackbar.Snackbar;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class recipeAdapter extends CursorAdapter {
    RecipeDatabase db;
    public TextView recipeTitle;
    public ImageView recipePic,overflow;
    public CardView cardView;
    public recipeAdapter(Context context, Cursor c,RecipeDatabase db) {
        super(context, c, 0);
        this.db=db;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.recipelayout,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        recipeTitle = (TextView) view.findViewById(R.id.info_text);
        recipePic = (ImageView) view.findViewById(R.id.recipeImage);
        overflow=(ImageView) view.findViewById(R.id.overflow);
        cardView=(CardView) view.findViewById(R.id.card_view);

        recipeTitle.setText(cursor.getString(cursor.getColumnIndex(db.RECIPE_COLUMN_NAME)));
        if(!cursor.getString(cursor.getColumnIndex(db.RECIPE_COLUMN_IMAGE)).equals("")) {
            Picasso.get().load(cursor.getString(cursor.getColumnIndex(db.RECIPE_COLUMN_IMAGE))).into(recipePic);
        }
        overflow.setTag((cursor.getInt(cursor.getColumnIndex(db.RECIPE_COLUMN_ID))));
        overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(overflow,(int)view.getTag());

            }
        });
        cardView.setTag((cursor.getInt(cursor.getColumnIndex(db.RECIPE_COLUMN_ID))));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,recipeInfo.class);
                intent.putExtra("key_id",(int)v.getTag());
                mContext.startActivity(intent);
            }
        });
        cardView.setTag((cursor.getInt(cursor.getColumnIndex(db.RECIPE_COLUMN_ID))));
        cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupMenu(overflow,(int)v.getTag());
                return true;
            }
        });
    }
    private void showPopupMenu(View view,int position) {
        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.recipe_menu, popup.getMenu());
        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        int numId;
        public MyMenuItemClickListener(int numId) {
            this.numId=numId;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.edit_recipe:

                    Intent intent = new Intent(mContext,EditRecipe.class);
                    intent.putExtra("key_id",numId);
                    mContext.startActivity(intent);


                    return true;
                case R.id.delete_recipe:
                    db.deleteRecipe(numId);
                    Snackbar.make(menuItem.getActionView(), "Successfully deleted Recipe", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                    Cursor newCursor=db.getAllRecipes();
                    swapCursor(newCursor);
                    notifyDataSetChanged();
                    return true;
                default:
            }
            return false;
        }
    }
}