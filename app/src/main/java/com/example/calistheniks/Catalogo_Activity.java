package com.example.calistheniks;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.calistheniks.databinding.ActivityCatalogoBinding;

public class Catalogo_Activity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityCatalogoBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //VINCULAR LA VISTA CON EL CONTROLADOR POR MEDIO DE BINDING
        binding = ActivityCatalogoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
           setSupportActionBar(binding.appBarCatalogo.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_catalogo, R.id.nav_account, R.id.nav_login)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_catalogo);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Uso de menu y su navegacion
        switch (item.getItemId()) {

            case R.id.action_favorite:
                Toast.makeText(
                        this,
                        "Favoritos",
                        Toast.LENGTH_SHORT
                ).show();
                Intent intent = new Intent(Catalogo_Activity.this, FavoritesActivity.class);
                startActivity(intent);
                break;

            case R.id.action_cart:
                Toast.makeText(
                        this,
                        "Shopping cart",
                        Toast.LENGTH_SHORT
                ).show();

                Intent intent2 = new Intent(Catalogo_Activity.this, CartActivity.class);
                startActivity(intent2);
                break;
        }
        return super.onOptionsItemSelected(item);
   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.catalogo_, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_catalogo);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}