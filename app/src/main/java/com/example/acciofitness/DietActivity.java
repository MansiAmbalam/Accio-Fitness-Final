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
import android.widget.TextView;

public class DietActivity extends NavigationActivity {
    AutoCompleteTextView disName;
    TextView bf;
    TextView lu;
    TextView di;
    String name;
    AsyncSearch asyncSearch=new AsyncSearch(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = findViewById(R.id.fragment_container);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_diet, null, false);
        frameLayout.addView(activityView);
        disName = findViewById(R.id.autocomplete1);
        bf=findViewById(R.id.breakfast);
        lu=findViewById(R.id.lunch);
        di=findViewById(R.id.dinner);
        asyncSearch.execute("Diet");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_spinner_item, asyncSearch.list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disName.setThreshold(1);
        disName.setAdapter(dataAdapter);
    }
    public void displayDisease(View view)
    {
        name=disName.getText().toString();
        new AsyncTaskWork(this,bf,lu,di).execute("Diet", name);
    }
    public void goUpdate(View view)
    {
        Intent intent = new Intent(getApplicationContext(),UpdateDietActivity.class);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_diet, menu);
        return true;
    }
}
