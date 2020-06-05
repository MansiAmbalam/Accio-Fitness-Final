package com.example.acciofitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.TextView;

public class RecipeActivity extends NavigationActivity {
    AutoCompleteTextView recName;
    TextView nm;
    TextView ing;
    TextView des;
    String name;
    AsyncSearch asyncSearch=new AsyncSearch(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = findViewById(R.id.fragment_container);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_recipe, null, false);
        frameLayout.addView(activityView);
        recName = findViewById(R.id.autocomplete2);
        nm=findViewById(R.id.recipeName);
        ing=findViewById(R.id.recipeIngredients);
        des=findViewById(R.id.recipeDescription);
        asyncSearch.execute("Recipe");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_spinner_item, asyncSearch.list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        recName.setThreshold(1);
        recName.setAdapter(dataAdapter);
    }
    public void displayRecipe(View view)
    {
        name=recName.getText().toString();
        new AsyncTaskWork(this,nm,ing,des).execute("Recipe", name);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_recipe, menu);
        return true;
    }
}
