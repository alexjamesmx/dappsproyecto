package com.example.calistheniks.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.calistheniks.R;
import com.example.calistheniks.databinding.FragmentLoginBinding;
import com.example.calistheniks.registerActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
//todos los fragmentos necesitan de un onCreate y un onCreateView
public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //VINCULAR LA VISTA CON EL CONTROLADOR POR MEDIO DE BINDING
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        //Para obtener una referencia a la vista raíz, llama al método getRoot();
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Al pulsar el boton "my Login" se navega a la actividad catalogo
        binding.loginFragmentCatalogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(
                        getActivity(),
                        R.id.nav_host_fragment_content_catalogo
                );
                navController.navigateUp();
                navController.navigate(R.id.nav_catalogo);
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

}