package com.example.calistheniks.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.calistheniks.R;
import com.example.calistheniks.databinding.FragmentLoginBinding;
import com.example.calistheniks.registerActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.Objects;

//todos los fragmentos necesitan de un onCreate y un onCreateView
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    boolean isAllFieldsChecked = false, isAllFieldsFilled = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //VINCULAR LA VISTA CON EL CONTROLADOR POR MEDIO DE BINDING
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        //Para obtener una referencia a la vista raíz, llama al método getRoot();
        View root = binding.getRoot();
        binding.loginTietEmail.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
//        binding.loginTietEmail.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                //   binding.loginTietUsername.setFilters(new InputFilter[]{new InputFilter.AllCaps()});
//                validarNombre(editable.toString());
//            }
//        });

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

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Al pulsar el boton "my Login" se navega a la actividad catalogo
        binding.loginFragmentCatalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                isAllFieldsChecked = CheckAllFields();
                isAllFieldsFilled = CheckAllFieldsVoid();

                if (!isAllFieldsFilled && isAllFieldsChecked) {
                    Toast.makeText(getActivity(), "Fill the form, please", Toast.LENGTH_LONG).show();
                } else if (isAllFieldsChecked && isAllFieldsFilled &&validarNombre(binding.loginTietEmail.getText().toString())) {
                    NavController navController = Navigation.findNavController(
                            getActivity(),
                            R.id.nav_host_fragment_content_catalogo
                    );
                    navController.navigateUp();
                    navController.navigate(R.id.nav_catalogo);
                } else {
                    Toast.makeText(getActivity(), "Something's wrong", Toast.LENGTH_LONG).show();
                }
            }
        });


        //Al pulsar el boton "register" se navega a la actividad register
        binding.loginFragmentRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(
                                getActivity(),
                                registerActivity.class
                        )
                );
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }


//
//    public boolean validarNombre(String texto) {
//        if (texto.trim().length() < 3) {
//            binding.loginTilUsername.setError("Username must be 3 length minimum");
//            binding.loginTilUsername.setErrorIconDrawable(null);
//            return false;
//        }
////        else if(texto.trim().length() > 6 ){
////            binding.loginTilUsername.setError("Username must be 6 length");
////            binding.loginTilUsername.setErrorIconDrawable(null);
////            return false;
////        }
//        else {
//            binding.loginTilUsername.setError("");
//            binding.loginTilUsername.setErrorEnabled(false);
//            return true;
//        }
//    }

    public boolean validarNombre(String texto){
        if(Patterns.EMAIL_ADDRESS.matcher(texto).matches()){
            binding.loginTilEmail.setErrorEnabled(false);
            return true;
        }
        else{
            binding.loginTilEmail.setError("Enter a valid email");
            binding.loginTilEmail.setErrorIconDrawable(null);
            return false;
        }
    }


    public boolean validarPassword(String texto) {
        if (texto.trim().length() < 8 || texto.contains(" ")) {
            if (texto.contains(" ")) {
                binding.loginTilPassword.setError("Dont leave white spaces");
                return false;
            } else {
                binding.loginTilPassword.setError("Password must be 8 length minimum");
                binding.loginTilPassword.setErrorIconDrawable(null);
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
            return false;
        } else if (binding.loginTietEmail.getText().toString().equals("")) {
            return false;
        } else
            // after all validation return true.
            return true;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}