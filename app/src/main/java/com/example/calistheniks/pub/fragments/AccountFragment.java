package com.example.calistheniks.pub.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;

import com.example.calistheniks.Myshopping_Activity;
import com.example.calistheniks.databinding.FragmentAccountBinding;

//todos los fragmentos necesitan de un onCreate y un onCreateView
public class AccountFragment extends Fragment {
    private FragmentAccountBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //VINCULAR LA VISTA CON EL CONTROLADOR POR MEDIO DE BINDING
        binding = FragmentAccountBinding.inflate(inflater, container, false);


        getParentFragmentManager().setFragmentResultListener("dataFrom1", this, new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                String email = result.getString("email");
                binding.tvemail.setText(email);
            }
        });

        //Para obtener una referencia a la vista raíz, llama al método getRoot();
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        //Al pulsar el boton "my shopping" se navega a la actividad myshopping
        binding.btnAccountMyshopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(
                        new Intent(
                                getActivity(),
                                Myshopping_Activity.class
                        )
                );
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }
}