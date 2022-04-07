package com.example.calistheniks.pub.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
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
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CatalogoFragment extends Fragment {

    private FragmentCatalogoBinding binding;
    private RequestQueue conServ;
    private String idTipoproducto;
    private final String END_POINT = Helper.baseUrl() + "productos/getproductos";
    private final String END_POINT_LD = Helper.baseUrl() + "productos/getdeseos";
//    private final String END_POINT_LD = Helper.baseUrl() + "back/carrito/agregadeseo";
    private ProductoAdapter adaptador;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCatalogoBinding.inflate(inflater, container, false);

//        final Bundle datos = getArguments();
//        idTipoproducto = datos.getString("categoria_producto");
        conServ = Volley.newRequestQueue(getActivity());

        binding.srlProductos.post(() -> {
            actualizaProductos();
        });

        binding.srlProductos.setOnRefreshListener(() -> {
            actualizaProductos();
        });

//        Click de cualquier elemento del ListView de productos
            binding.lvProductos.setOnItemClickListener((adapterView, view, i, l) -> {
            //al darle click a un producto, tomamos su idProducto
            TextView tvIdprodcuto = view.findViewById(R.id.tv_idproducto);
            Toast.makeText(getActivity(),"ME PICASTE",Toast.LENGTH_LONG).show();

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

    /*
    Creamos un metodo para agregar a la lista de deseos el producto seleccionado
     */
    public void agregarListaDeseos(String idProducto) {
        //Buscamos los valores guardados de token y id
        final SharedPreferences sPrefs = getActivity().getSharedPreferences(
                "dapps_201",
                Context.MODE_PRIVATE
        );

        /*
        Tomamos el token y el idusuario de las preferencias,
        si no lo encontramos guardamos un nulo
         */
        final String token = sPrefs.getString("uat", null);
        final String idusario = sPrefs.getString("idusuario", null);

        /*
        Si el token o el idusuario es nulo significa que no ha iniciado
        sesión
         */
        if (token == null || idusario == null) {
            final AlertDialog.Builder alerta = new AlertDialog.Builder(
                    getActivity()
            );

            /*
            Mostramos una alerta
             */
            alerta.setTitle("ERROR");
            alerta.setMessage("Inicia sesión para agregar a la lista de deseos");
            alerta.setPositiveButton(
                    "Iniciar sesión",
                    (dialogInterface,i) -> {
                        final NavController navController = Navigation.findNavController(
                                getActivity(),
                                R.id.host_public_fragments
                        );
                        navController.navigateUp();
                        navController.navigate(R.id.LoginFragment);
                    }
            );
            alerta.setNegativeButton("Cancelar", null);
            alerta.setCancelable(false);
            alerta.show();

            //Terminamos la ejecucion del método
            return;
        }

        /*
        Insertamos el producto en la lista de deseos, para ello
        necesitamos:
        idproducto
        idusuario
        token
         */
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
            /*
            Mandamos los parámetros POST que necesita el
            servicio
             */
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("idusuario", idusario);
                parametros.put("idproducto", idProducto);
                parametros.put("token", token);

                return parametros;
            }
        };
        //Ejecutamos el servicio
        conServ.add(petAgregaListaDeseos);
    }
}