package com.example.calistheniks.ui.catalogo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.calistheniks.databinding.FragmentCatalogoBinding;

//todos los fragmentos necesitan de un onCreate y un onCreateView
public class CatalogoFragment extends Fragment {

    private FragmentCatalogoBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //VINCULAR LA VISTA CON EL CONTROLADOR POR MEDIO DE BINDING
        binding = FragmentCatalogoBinding.inflate(inflater, container, false);
        //Para obtener una referencia a la vista raíz, llama al método getRoot();
        View root = binding.getRoot();
        return root;
    }

}