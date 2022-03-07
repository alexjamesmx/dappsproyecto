package com.example.calistheniks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class registerActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private Button btnregistrocatalogo;

    //private Binding registerActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnregistrocatalogo = findViewById(R.id.btn_register_catalog);

        //USO DE ACTION BAR PARA VOLVER UN SCREEN ATRAS
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //AL PULSAR EL BOTON REGISTARSE NAVEGAMOS AL CATALOGO
        btnregistrocatalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(
                                registerActivity.this,
                                Catalogo_Activity.class
                        )
                );
            }
        });
    }


    //USO DE ACTION BAR PARA VOLVER UN SCREEN ATRAS
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}