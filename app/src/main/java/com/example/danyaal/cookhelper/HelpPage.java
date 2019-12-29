package com.example.danyaal.cookhelper;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HelpPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_page);

        Button button = (Button) findViewById(R.id.RecipeAddButton);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent = new Intent(getApplicationContext(),HelpAdd.class);
                startActivity(intent);

            }
        });

        Button button1 = (Button) findViewById(R.id.RecipeDeleteButton);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent1 = new Intent(getApplicationContext(), HelpDelete.class);
                startActivity(intent1);
            }
        });
        Button button2 = (Button) findViewById(R.id.RecipeSearchButton);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent2 = new Intent(getApplicationContext(), HelpSearch.class);
                startActivity(intent2);
            }
        });
        Button button3 = (Button) findViewById(R.id.RecipeEditButton);
        button3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent intent3 = new Intent(getApplicationContext(), HelpEdit.class);
                startActivity(intent3);
            }
        });

    }
}
