package com.example.acciofitness;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NavigationActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_symptom) {
            Intent intent = new Intent(getApplicationContext(),SymptomActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_dietfood) {
            Intent intent = new Intent(getApplicationContext(), DietActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_recipe) {
            Intent intent = new Intent(getApplicationContext(),RecipeActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_waterdrink) {
            Intent intent = new Intent(getApplicationContext(),SymptomActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_sleephour) {
            Intent intent = new Intent(getApplicationContext(),SymptomActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_exercise) {
            Intent intent = new Intent(getApplicationContext(),ExerciseActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_update_disease) {
            Intent intent = new Intent(getApplicationContext(),UpdateDiseaseActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_update_diet) {
            Intent intent = new Intent(getApplicationContext(),UpdateDietActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
