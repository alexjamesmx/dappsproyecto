package com.example.calistheniks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.calistheniks.databinding.ActivityFavoritesBinding;
import com.example.calistheniks.databinding.ActivityRegisterBinding;
import com.example.calistheniks.databinding.FragmentCatalogoBinding;
import com.example.calistheniks.databinding.ItemProductoBinding;
import com.example.calistheniks.pub.ProductoAdapter;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FavoritesActivity extends AppCompatActivity {
    private ActionBar actionBar;

    //VOLLEY
    private RequestQueue conServ;
    private ProductoAdapter adaptador;
    private ActivityFavoritesBinding binding;
    private ItemProductoBinding itembinding;
    private final String END_PONT = Helper.baseUrl() + "productos/getdeseos";
   private final String END_PONT_RD = Helper.baseUrl()  +"productos/removerdeseos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFavoritesBinding.inflate(getLayoutInflater());
        itembinding = ItemProductoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //USO DE ACTION BAR PARA VOLVER UN SCREEN ATRAS
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        conServ = Volley.newRequestQueue(FavoritesActivity.this);
        binding.srlFavoritos.post(() -> {
            actualizarDeseos();
        });
        binding.srlFavoritos.setOnRefreshListener(() -> {
            actualizarDeseos();
        });
   itembinding.favoritos.setOnClickListener(view -> {
       Toast.makeText(FavoritesActivity.this, "soy Favoritos", Toast.LENGTH_SHORT).show();
   });

        binding.lvProductos.setOnItemClickListener(((adapterView, view, i, l) -> {
            final SharedPreferences sPrefs =FavoritesActivity.this.getSharedPreferences(
                    "calistenix",
                    Context.MODE_PRIVATE
            );
            final String idcliente = sPrefs.getString("idcliente", null);
            //al darle click a un producto, tomamos su idProducto
            TextView tvIdprodcuto = view.findViewById(R.id.tv_idproducto);
            //pasamos como parámetro el string del id de producto
                    removerListaDeseos(
                    tvIdprodcuto.getText().toString(),
                    idcliente
            );
        }
        )

        );
    }

    private void removerListaDeseos(String idproducto, String idcliente) {
        final StringRequest petRemoverListaDeseos = new StringRequest(
                Request.Method.POST,
                END_PONT_RD,
                response -> {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        Toast.makeText(FavoritesActivity.this, jsonObject.getString("mensaje"), Toast.LENGTH_SHORT).show();
                    }
                    catch(Exception e){
                        Toast.makeText(FavoritesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(FavoritesActivity.this,  error.toString(), Toast.LENGTH_SHORT).show();
                }
        ) {
            @Nullable
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("idcliente", idcliente);
                parametros.put("idproducto", idproducto);
                return parametros;
            }
        };
        conServ.add(petRemoverListaDeseos);
    }


    public void actualizarDeseos(){
        binding.srlFavoritos.setRefreshing(true);
        final SharedPreferences sPrefs =FavoritesActivity.this.getSharedPreferences(
                "calistenix",
                Context.MODE_PRIVATE
        );
        final String idcliente = sPrefs.getString("idcliente", null);
        final String token = sPrefs.getString("uat", null);
        if (token == null || idcliente == null) {
            final AlertDialog.Builder alerta = new AlertDialog.Builder(
                    FavoritesActivity.this
            );
            alerta.setTitle("ERROR");
            alerta.setMessage("Inicia sesión para agregar a la lista de deseos");
            alerta.setPositiveButton(
                    "Iniciar sesión",
                    (dialogInterface, i) -> {
                        final NavController navController = Navigation.findNavController(
                                FavoritesActivity.this,
                                R.id.nav_host_fragment_content_catalogo
                        );
                        navController.navigateUp();
                        navController.navigate(R.id.nav_login);
                    }
            );
            alerta.setNegativeButton("Cancelar", null);
            alerta.setCancelable(false);
            alerta.show();
            return;
        }
        final StringRequest petServ = new StringRequest(
                Request.Method.POST,
                END_PONT,
                response -> {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if(jsonObject.getBoolean("resultado")){
                            adaptador = new ProductoAdapter(
                                    FavoritesActivity.this,
                                    jsonObject.getJSONArray("productos")
                            );
                            binding.lvProductos.setAdapter(adaptador);
                        }
                    }
                    catch (Exception e){ Toast.makeText(FavoritesActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show(); }
                    finally {
                        binding.srlFavoritos.setRefreshing(false);
                    }
                },
                error -> {
                    Toast.makeText(FavoritesActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    binding.srlFavoritos.setRefreshing(false);
                }
        ) {
            @Nullable
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("idcliente", idcliente);

                return parametros;
            }
        };
        conServ.add(petServ);
    }




    //USO DE ACTION BAR PARA VOLVER UN SCREEN ATRAS
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}