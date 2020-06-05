package com.example.acciofitness;

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

public class SymptomActivity extends NavigationActivity {
    AutoCompleteTextView symName;
    String name;
    TextView disResult;
    AsyncSearch asyncSearch=new AsyncSearch(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = findViewById(R.id.fragment_container);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_symptom, null, false);
        frameLayout.addView(activityView);
        symName = findViewById(R.id.autocomplete);
        disResult=findViewById(R.id.symResult);
        asyncSearch.execute("Symptom");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_spinner_item, asyncSearch.list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        symName.setThreshold(1);
        symName.setAdapter(dataAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_symptom, menu);
        return true;
    }

    public void displayDisease(View view) {
        name = symName.getText().toString();
        new AsyncTaskWork(this,disResult).execute("Symptom", name);
    }
    public void goUpdate(View view)
    {
        Intent intent = new Intent(getApplicationContext(),UpdateDiseaseActivity.class);
        startActivity(intent);
    }
}
