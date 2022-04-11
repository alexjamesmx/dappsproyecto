package com.example.calistheniks.pub.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.calistheniks.Helper;
import com.example.calistheniks.databinding.FragmentCatalogoBinding;
import com.example.calistheniks.pub.ProductoAdapter;
import com.example.calistheniks.R;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class CatalogoFragment extends Fragment {

    private FragmentCatalogoBinding binding;
    private RequestQueue conServ;
    private final String END_POINT = Helper.baseUrl() + "productos/getproductos";
    private final String END_POINT_LD = Helper.baseUrl() + "productos/insertardeseos";
//    private final String END_POINT_LD = Helper.baseUrl() + "back/carrito/agregadeseo";
    private ProductoAdapter adaptador;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       binding = FragmentCatalogoBinding.inflate(inflater, container, false);
//        String str = "";
//        String latitude = getArguments().getString("idcliente") ;
//         String cliente = b.getString(b);
//        str =cliente.getString("idcliente");
//        Toast.makeText(getActivity(), cliente, Toast.LENGTH_LONG).show();

        conServ = Volley.newRequestQueue(getActivity());
        binding.srlProductos.post(() -> {
            actualizaProductos();
        });
        binding.srlProductos.setOnRefreshListener(() -> {
            actualizaProductos();
        });

//     //Click de cualquier elemento del ListView de productos
        binding.lvProductos.setOnItemClickListener((adapterView, view, i, l) -> {
            //al darle click a un producto, tomamos su idProducto
            TextView tvIdprodcuto = view.findViewById(R.id.tv_idproducto);
            //pasamos como parámetro el string del id de producto
            agregarListaDeseos(
                tvIdprodcuto.getText().toString()
            );
        });

        return binding.getRoot();
    }

    public void actualizaProductos() {
        binding.srlProductos.setRefreshing(true);
        final StringRequest petServ = new StringRequest(
                Request.Method.POST,
                END_POINT,
                response -> {
                    try {
                        JSONObject objRespuesta = new JSONObject(response);

                        if (objRespuesta.getBoolean("resultado")) {
                            adaptador = new ProductoAdapter(
                                    getActivity(),
                                    objRespuesta.getJSONArray("productos")
                            );
                            binding.lvProductos.setAdapter(adaptador);
                        }
                    }

                    catch (Exception e) { Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show(); }

                    finally {
                        binding.srlProductos.setRefreshing(false);
                    }
                },
                error -> {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    binding.srlProductos.setRefreshing(false);
                }
        );
//        {
//            @Nullable
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> parametros = new HashMap<>();
//                parametros.put("categoria_producto", idTipoproducto);
//
//                return parametros;
//            }
//        };
        conServ.add(petServ);
    }

    public void agregarListaDeseos(String idProducto) {
                final SharedPreferences sPrefs = getActivity().getSharedPreferences(
                        "calistenix",
                        Context.MODE_PRIVATE
                );
                final String token = sPrefs.getString("uat", null);
                final String idcliente = sPrefs.getString("idcliente", null);
        if (token == null || idcliente == null) {
            final AlertDialog.Builder alerta = new AlertDialog.Builder(
                    getActivity()
            );
            alerta.setTitle("ERROR");
            alerta.setMessage("Inicia sesión para agregar a la lista de deseos");
            alerta.setPositiveButton(
                    "Iniciar sesión",
                    (dialogInterface,i) -> {
                        final NavController navController = Navigation.findNavController(
                                getActivity(),
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

               final StringRequest petAgregaListaDeseos = new StringRequest(
                Request.Method.POST,
                END_POINT_LD,
                response -> {
                    //intentamos convertir la respuesta del servicio
                    //a un objeto json
                    try {
                        JSONObject objRespuesta = new JSONObject(response);
                        //Mostramos el mensaje del servicio
                        Toast.makeText(getActivity(), objRespuesta.getString("mensaje"), Toast.LENGTH_SHORT).show();
                    }
                    catch(Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                }
        ) {
            @Nullable
            @Override

            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("idcliente", idcliente);
                parametros.put("idproducto", idProducto);
                parametros.put("token", token);

                return parametros;
            }
        };
//        Ejecutamos el servicio
        conServ.add(petAgregaListaDeseos);
    }
}
