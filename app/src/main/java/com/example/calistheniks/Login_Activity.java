package com.example.calistheniks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Login_Activity extends AppCompatActivity {
private Button btn_loginRegistro;
private Button btn_loginHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_loginRegistro = findViewById(R.id.btn_register);
        btn_loginHome = findViewById(R.id.login_home);

        btn_loginRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent navLoginRegister = new Intent(
                        Login_Activity.this,
                        registerActivity.class
                );
                        startActivity(navLoginRegister);
            }
        });

        btn_loginHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent navLoginHome = new Intent(
                        Login_Activity.this,
                        Catalogo_Activity.class
                );
                startActivity(navLoginHome);
            }
        });

    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
////        if(item.getItemId() == android.R.id.home){
////            finish();
////        }
//        if(item.getItemId() == R.id.menu_m1){
//            Toast.makeText(this, "menu 1", Toast.LENGTH_SHORT).show();
//        }
//        if(item.getItemId() == R.id.menu_m2){
//            Toast.makeText(this, "menu 2", Toast.LENGTH_SHORT).show();
//        }
//        if(item.getItemId() == R.id.menu_m3){
//            Toast.makeText(this, "menu 3", Toast.LENGTH_SHORT).show();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }
}