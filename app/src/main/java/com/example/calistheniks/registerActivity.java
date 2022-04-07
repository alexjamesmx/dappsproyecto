package com.example.calistheniks;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.calistheniks.databinding.ActivityRegisterBinding;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class registerActivity extends AppCompatActivity {
    private ActionBar actionBar;
    private Button btnregistrocatalogo;
    private ActivityRegisterBinding binding;

    //VOLLEY
    private final String url = Helper.baseUrl() + "acceso/registrajugador/";
    private ProgressDialog progressDialog;
    private RequestQueue conexionServ;
    private StringRequest peticionServ;




    boolean isAllFieldsChecked = false, isAllFieldsFilled = false;

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
                isAllFieldsChecked = CheckAllFields();
                isAllFieldsFilled = CheckAllFieldsVoid();
                if (!isAllFieldsFilled && isAllFieldsChecked) {
                    Toast.makeText(registerActivity.this, "Fill the form, please", Toast.LENGTH_LONG).show();
                }
                else if (isAllFieldsChecked && isAllFieldsFilled ) {
                    progressDialog.setTitle("Creating an Account...");
                    progressDialog.setMessage("Please wait");
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCancelable(false);

                    final String apellidos_cliente = binding.registerTietLastname.getText().toString();
                    final String nombre_cliente = binding.registerTietFirstname.getText().toString();
                    final String telefono = binding.registerTietPhonenumber.getText().toString();
                    final String direccion = binding.registerTietHomeaddress.getText().toString();
                    final String password_cliente = binding.registerTietPassword.getText().toString();
                    final String email_cliente = binding.registerTietEmail.getText().toString();

                    peticionServ = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progressDialog.hide();
                            Toast.makeText(registerActivity.this, response, Toast.LENGTH_LONG).show();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String mensaje = jsonObject.getString("mensaje");
                                if (jsonObject.getBoolean("resultado")) {
                                    Snackbar.make(view, mensaje, Snackbar.LENGTH_INDEFINITE)
                                            .setAction("Go to catalog", view -> {
                                                startActivity(
                                                        new Intent(
                                                                registerActivity.this,
                                                                Catalogo_Activity.class
                                                        )
                                                );
                                            }).show();
                                } else {
                                    Snackbar.make(view, mensaje, Snackbar.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                Toast.makeText(
                                        registerActivity.this,
                                        e.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();
                                progressDialog.hide();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) { }
                    })
                    {
                        @Nullable
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                    /*Creamos un mapa con claves tipo String
                        y yalores tipo String*/

                            Map<String,String> parametros = new HashMap<>();

                        /*
                    Por medio de método put podemos agregar una clave
                    y valor
                        */
                            parametros.put("nombre_cliente",nombre_cliente);
                            parametros.put("apellidos_cliente",apellidos_cliente);
                            parametros.put("password_cliente",password_cliente);
                            parametros.put("email_cliente",email_cliente);
                            parametros.put("telefono",telefono);
                            parametros.put("direccion",direccion);

                            return parametros;
                        }
                    };
                    conexionServ.add(peticionServ);
                }
                else if (!isAllFieldsChecked){
                    Toast.makeText(registerActivity.this,  "Something's wrong", Toast.LENGTH_LONG).show();
                }
                }
        });

        progressDialog = new ProgressDialog(registerActivity.this);
        conexionServ = Volley.newRequestQueue(registerActivity.this);



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
        binding.registerTietEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
            validarCorreo(editable.toString());
            }
        });
        binding.registerTietPhonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
            validarTelefono(editable.toString());
            }
        });
        binding.registerTietPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                validarPassword(editable.toString());
            }
        });
        binding.registerTietHomeaddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void afterTextChanged(Editable editable) {
                validarHomeAddress(editable.toString());
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
            binding.registerTilLastname.setError("Last name must be 20 length maximum");
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
            binding.registerTilEmail.setError("Enter a valid email--> user@domain.com");
            binding.registerTilEmail.setErrorIconDrawable(null);
            return false;
        }
    }
    public boolean validarTelefono(String texto){
        if (Patterns.PHONE.matcher(texto).matches() && texto.length() == 10) {
           binding.registerTilPhonenumber.setErrorEnabled(false);
           return true;
        }
        else{
            binding.registerTilPhonenumber.setError("Enter a valid phone--> 10 digits");
            binding.registerTilPhonenumber.setErrorIconDrawable(null);
            return false;
        }
    }
    public boolean validarHomeAddress(String texto){
        if (texto.trim().length() < 10 ) {
            binding.registerTilHomeaddress.setError("Add more information about your addresss" );
            binding.registerTilHomeaddress.setErrorIconDrawable(null);
            return false;
        }
         else {
            binding.registerTilHomeaddress.setErrorEnabled(false);
            return true;
        }
    }
    public boolean validarPassword(String texto) {
        if (texto.trim().length() < 8 || texto.contains(" ")) {
            if (texto.contains(" ")) {
                binding.registerTilPassword.setError("Dont leave white spaces");
                if (texto.equals("")) {
                    binding.registerTilPassword.setErrorEnabled(false);
                }
                return false;
            } else {
                binding.registerTilPassword.setError("Password must be 8 length minimum");
                binding.registerTilPassword.setErrorIconDrawable(null);
                if (texto.equals("")) {
                    binding.registerTilPassword.setErrorEnabled(false);
                }
                return false;
            }
        } else {
            binding.registerTilPassword.setError("");
            binding.registerTilPassword.setErrorEnabled(false);
            return true;
        }
    }
    private boolean CheckAllFields() {
        if (    binding.registerTilFirstname.isErrorEnabled() ||
                binding.registerTilLastname.isErrorEnabled() ||
                binding.registerTilHomeaddress.isErrorEnabled() ||
                binding.registerTilPassword.isErrorEnabled() ||
                binding.registerTilEmail.isErrorEnabled() ||
                binding.registerTilPhonenumber.isErrorEnabled()){
            return false;
        }else
            // after all validation return true.
            return true;
    }
    private boolean CheckAllFieldsVoid() {
        if (    binding.registerTietFirstname.getText().toString().equals("") ||
                binding.registerTietLastname.getText().toString().equals("") ||
                binding.registerTietPassword.getText().toString().equals("") ||
                binding.registerTietEmail.getText().toString().equals("") ||
                binding.registerTietPhonenumber.getText().toString().equals("") ||
                binding.registerTietHomeaddress.getText().toString().equals("")) {
            return false;
        }
         else{
            return true;
    }
}

    //USO DE ACTION BAR PARA VOLVER UN SCREEN ATRAS
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }
}