package com.example.calistheniks.pub.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.calistheniks.Helper;
import com.example.calistheniks.R;
import com.example.calistheniks.databinding.FragmentLoginBinding;
import com.example.calistheniks.registerActivity;
import com.google.android.material.snackbar.Snackbar;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

//todos los fragmentos necesitan de un onCreate y un onCreateView
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginFragment viewModel;
    private NavController navController;
    boolean isAllFieldsChecked = false, isAllFieldsFilled = false;

    //VOLLEY
    private ProgressDialog progress;
    private RequestQueue conexionServ;
    private StringRequest peticionServ;
    private final String url = Helper.baseUrl() + "acceso/verficausuario";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//VINCULAR LA VISTA CON EL CONTROLADOR POR MEDIO DE BINDING
        binding = FragmentLoginBinding.inflate(inflater, container, false);


        conexionServ = Volley.newRequestQueue(getActivity());

        login();
        binding.srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                login();
            }
        });
        binding.loginFragmentRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Intent intent = new Intent(getActivity(),registerActivity.class);
            startActivity((intent));
                ((Activity) getActivity()).overridePendingTransition(0, 0);
            }
        });

        return binding.getRoot();
    }


    public boolean validarEmail(String texto) {
        if ((Patterns.EMAIL_ADDRESS.matcher(texto).matches()) || texto.equals("")) {
            binding.loginTilEmail.setErrorEnabled(false);
            return true;
        } else {
            binding.loginTilEmail.setError("Enter a valid email");
            binding.loginTilEmail.setErrorIconDrawable(null);
            return false;
        }
    }

    public boolean validarPassword(String texto) {
        if (texto.trim().length() < 8 || texto.contains(" ")) {
            if (texto.contains(" ")) {
                binding.loginTilPassword.setError("Dont leave white spaces");
                if (texto.equals("")) {
                    binding.loginTilPassword.setErrorEnabled(false);
                }
                return false;
            } else {
                binding.loginTilPassword.setError("Password must be 8 length minimum");
                binding.loginTilPassword.setErrorIconDrawable(null);
                if (texto.equals("")) {
                    binding.loginTilPassword.setErrorEnabled(false);
                }
                return false;
            }
        } else {
            binding.loginTilPassword.setError("");
            binding.loginTilPassword.setErrorEnabled(false);
            return true;
        }
    }

    private boolean CheckAllFields() {
        if (binding.loginTilPassword.isErrorEnabled()) {
            return false;
        } else if (binding.loginTilEmail.isErrorEnabled()) {
            return false;
        } else
            // after all validation return true.
            return true;
    }

    private boolean CheckAllFieldsVoid() {
        if (binding.loginTietPassword.getText().toString().equals("")) {
            binding.loginTilPassword.setErrorEnabled(false);
            return false;
        } else if (binding.loginTietEmail.getText().toString().equals("")) {
            binding.loginTilEmail.setErrorEnabled(false);
            return false;
        } else
            // after all validation return true.
            return true;
    }


    public void login() {

        binding.srl.setRefreshing(false);
        //Para obtener una referencia a la vista raíz, llama al método getRoot();

        //binding.loginTietEmail.setFilters(new InputFilter[]{new InputFilter.AllCaps()});

        //VALIDACIONES DEL LOGIN
        binding.loginTietPassword.addTextChangedListener(new TextWatcher() {
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
        binding.loginTietEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                validarEmail(editable.toString());
            }
        });
        //VALIDACIONES DEL LOGIN

        binding.loginFragmentCatalogo.setOnClickListener(view -> {
            isAllFieldsChecked = CheckAllFields();
            isAllFieldsFilled = CheckAllFieldsVoid();
            if (!isAllFieldsFilled && isAllFieldsChecked) {
                Toast.makeText(getActivity(), "Fill the form, please", Toast.LENGTH_LONG).show();
            } else if (isAllFieldsChecked && isAllFieldsFilled) {
                final ProgressDialog progress = new ProgressDialog(
                        getActivity()
                );

                final String email_cliente = binding.loginTietEmail.getText().toString();
                final String password_cliente = binding.loginTietPassword.getText().toString();
                progress.setTitle("Iniciando sesión");
                progress.setMessage("Por favor espera...");
                progress.setIndeterminate(true);
                progress.setCancelable(false);
                progress.show();


                peticionServ = new StringRequest(
                        Request.Method.POST, url,
                        response -> {
                            binding.srl.setRefreshing(false);
                            try {
                                JSONObject jsonObject = new JSONObject(response);

                                JSONObject jsonCliente =jsonObject.getJSONObject("cliente");

                                final JSONObject objCliente = jsonObject.getJSONObject("cliente");
                                final String token = jsonObject.getString("token");

                                if (jsonObject.getBoolean("resultado") == true) {

//                                    Bundle result = new Bundle();
//                                 String  idcliente =  jsonCliente.getString("idcliente");
//                                    result.putString("idcliente", idcliente);

                                    Toast.makeText(
                                            getActivity(),
                                            jsonObject.getString("mensaje"),
                                            Toast.LENGTH_LONG
                                    ).show();
                                    SharedPreferences sPrefs  = getActivity().getSharedPreferences(
                                            "calistenix",
                                            Context.MODE_PRIVATE
                                    );

                                    SharedPreferences.Editor sPrefsEditor = sPrefs.edit();
                                    sPrefsEditor.putString("uat", token);
                                    sPrefsEditor.putString("uai",
                                    Helper.MD5Hash(objCliente.getString("idcliente")));
                                    sPrefsEditor.putString(
                                            "idcliente",
                                            objCliente.getString("idcliente")
                                    );
                                    sPrefsEditor.commit();


                                    NavHostFragment.findNavController(LoginFragment.this)
                                            .navigate(R.id.nav_catalogo);
                                } else {
                                    Snackbar
                                            .make(view, jsonObject.getString("mensaje"), Snackbar.LENGTH_INDEFINITE)
                                            .setAction("Aceptar", viewSnack -> {
                                                binding.loginTietEmail.setText("");
                                                binding.loginTietPassword.setText("");
                                                binding.loginTietEmail.requestFocus();
                                            }).show();
                                }
                                progress.hide();
                            } catch (Exception e) {
                                Toast.makeText(
                                        getActivity(),
                                        e.getMessage(),
                                        Toast.LENGTH_LONG
                                ).show();

                                progress.hide();
                            }
                        },
                        errorResponse -> {
                            binding.srl.setRefreshing(false);
                            Toast.makeText(
                                    getActivity(),
                                    errorResponse.toString(),
                                    Toast.LENGTH_LONG
                            ).show();
                            progress.hide();
                        }
                ) {
                    @Nullable
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<>();
                        parametros.put("email_cliente", email_cliente);
                        parametros.put("password_cliente", password_cliente);
                        return parametros;
                    }
                };
                conexionServ.add(peticionServ);
            } else {
                Toast.makeText(getActivity(), "Something's wrong", Toast.LENGTH_LONG).show();
            }
        });
    }
}