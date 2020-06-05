package com.example.acciofitness;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.FrameLayout;
import android.widget.MultiAutoCompleteTextView;

public class UpdateDietActivity extends NavigationActivity {
    AsyncSearch asyncSearchDisease=new AsyncSearch(this);
    AsyncSearch asyncSearchBfFood=new AsyncSearch(this);
    AutoCompleteTextView disName;
    MultiAutoCompleteTextView multiBreakfast;
    MultiAutoCompleteTextView multiLunch;
    MultiAutoCompleteTextView multiDinner;
    String dname;
    String breakfast,lunch,dinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = findViewById(R.id.fragment_container);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_update_diet, null, false);
        frameLayout.addView(activityView);
        disName = findViewById(R.id.autocomplete1);
        asyncSearchDisease.execute("Diet");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_spinner_item, asyncSearchDisease.list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disName.setAdapter(dataAdapter);
        multiBreakfast = findViewById(R.id.breakfast);
        multiBreakfast.setPadding(15,15,15,15);
        asyncSearchBfFood.execute("Food");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, asyncSearchBfFood.list);
        multiBreakfast.setAdapter(arrayAdapter);
        multiBreakfast.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiLunch = findViewById(R.id.lunch);
        multiLunch.setPadding(15,15,15,15);
        multiLunch.setAdapter(arrayAdapter);
        multiLunch.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        multiDinner = findViewById(R.id.dinner);
        multiDinner.setPadding(15,15,15,15);
        multiDinner.setAdapter(arrayAdapter);
        multiDinner.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_update_diet, menu);
        return true;
    }
    public void updateDiet(View view) {
        dname = disName.getText().toString();
        breakfast= multiBreakfast.getText().toString();
        lunch= multiLunch.getText().toString();
        dinner= multiDinner.getText().toString();
        new AsyncTaskWork(this).execute("UpdateDiet", dname,breakfast,lunch,dinner);
    }
    public void goSearch(View view)
    {
        Intent intent = new Intent(getApplicationContext(),DietActivity.class);
        startActivity(intent);
    }
}
