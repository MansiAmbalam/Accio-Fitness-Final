package com.example.acciofitness;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.MultiAutoCompleteTextView;

public class UpdateDiseaseActivity extends NavigationActivity {
    MultiAutoCompleteTextView multiAutoCompleteTextView;
    AsyncSearch asyncSearchSymptom=new AsyncSearch(this);
    AsyncSearch asyncSearchDisease=new AsyncSearch(this);
    AutoCompleteTextView disName;
    String dname;
    String snames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout frameLayout = findViewById(R.id.fragment_container);
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_update_disease, null, false);
        frameLayout.addView(activityView);
        disName = findViewById(R.id.autocomplete1);
        asyncSearchDisease.execute("Diet");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
                (getApplicationContext(), android.R.layout.simple_spinner_item, asyncSearchDisease.list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        disName.setAdapter(dataAdapter);
        multiAutoCompleteTextView = findViewById(R.id.multiAutoCompleteTextViewEmail);
        multiAutoCompleteTextView.setPadding(15,15,15,15);
        asyncSearchSymptom.execute("Symptom");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, asyncSearchSymptom.list);
        multiAutoCompleteTextView.setAdapter(arrayAdapter);
        multiAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_update_disease, menu);
        return true;
    }
    public void updateDisease(View view) {
        dname = disName.getText().toString();
        snames= multiAutoCompleteTextView.getText().toString();
        new AsyncTaskWork(this).execute("UpdateDisease", dname,snames);
    }
    public void goSearch(View view)
    {
        Intent intent = new Intent(getApplicationContext(),SymptomActivity.class);
        startActivity(intent);
    }
}
