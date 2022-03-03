package com.example.calistheniks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class FavoritesActivity extends AppCompatActivity {
    private ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);

        actionBar = getSupportActionBar();






        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.action_cart:
                Toast.makeText(
                        this,
                        "Favoritos",
                        Toast.LENGTH_SHORT
                ).show();

                Intent intent = new Intent(FavoritesActivity.this, CartActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_favorites, menu);
        return super.onCreateOptionsMenu(menu);
    }
}