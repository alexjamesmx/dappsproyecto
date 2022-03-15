package com.example.calistheniks;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.calistheniks.databinding.ActivityRegisterBinding;

import java.util.regex.Pattern;

public class registerActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private Button btnregistrocatalogo;
    private ActivityRegisterBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        btnregistrocatalogo = findViewById(R.id.btn_register_catalog);
        binding.registerTietFirstname.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        binding.registerTietLastname.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
        binding.registerTietHomeaddress.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        //**************ACTION BAR PARA VOLVER UN SCREEN ATRAS******************************
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //********************************************

        //Registrarse-a-catalogo
        btnregistrocatalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validarCorreo(binding.registerTietEmail.getText().toString())){
                    startActivity(
                            new Intent(
                                    registerActivity.this,
                                    Catalogo_Activity.class
                            )
                    );
                }
            }
        });

        binding.registerTietFirstname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                validarFirstName(editable.toString());
            }
        });

        binding.registerTietLastname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                validarLastName(editable.toString());
            }
        });


    }


    public boolean validarFirstName(String texto) {
        if (texto.trim().length() < 3 ) {
            binding.registerTilFirstname.setError("just letters and 3 length minimum" );
            binding.registerTilFirstname.setErrorIconDrawable(null);
            return false;
        }
        else if(texto.trim().length() > 20 ){
            binding.registerTilFirstname.setError("Username must be 20 length maximum");
            binding.registerTilFirstname.setErrorIconDrawable(null);
            return false;
        } else {
            binding.registerTilFirstname.setErrorEnabled(false);
            return true;
        }
    }


    public boolean validarLastName(String texto) {
        if (texto.trim().length() < 3 ) {
            binding.registerTilLastname.setError("just letters and 3 length minimum" );
            binding.registerTilLastname.setErrorIconDrawable(null);
            return false;
        }
        else if(texto.trim().length() > 20 ){
            binding.registerTilLastname.setError("Username must be 20 length maximum");
            binding.registerTilLastname.setErrorIconDrawable(null);
            return false;
        } else {
            binding.registerTilLastname.setErrorEnabled(false);
            return true;
        }
    }
    public boolean validarCorreo(String texto){
        if(Patterns.EMAIL_ADDRESS.matcher(texto).matches()){
            binding.registerTilEmail.setErrorEnabled(false);
            return true;
        }
        else{
            binding.registerTilEmail.setError("Enter a valid email");
            binding.registerTilEmail.setErrorIconDrawable(null);
            return false;
        }
    }




























































    //USO DE ACTION BAR PARA VOLVER UN SCREEN ATRAS
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}