package com.example.calistheniks.pub;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;
import com.example.calistheniks.R;
import com.example.calistheniks.Helper;

public class ProductoAdapter extends BaseAdapter {

    public JSONArray datos;
    public LayoutInflater inflater;

    public ProductoAdapter (Context contexto, JSONArray datos) {
        this.datos = datos;
        inflater = LayoutInflater.from(contexto);
    }

    @Override
    public int getCount() {
        return datos.length();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = inflater.inflate(R.layout.item_producto, null);
            try {
                JSONObject objProducto = datos.getJSONObject(i);

                    TextView tvIdProducto = view.findViewById(R.id.tv_idproducto);
                    tvIdProducto.setText(
                            objProducto.getString("idproducto")
                    );
                    ImageView ivImagen = view.findViewById(R.id.iv_img_producto);
                    Picasso.get()
                            .load(
                                    Helper.baseUrl() +
                                            "../front/static/assets/img/build/img/calistenia/" + objProducto.getString("imagen_producto")
                            ).placeholder(R.drawable.ic_menu_camera)
                            .into(ivImagen);
                    TextView tvNombre = view.findViewById(
                            R.id.tv_nomproducto
                    );
                    tvNombre.setText(objProducto.getString("nombre_producto"));
                    TextView tvPrecio = view.findViewById(
                            R.id.tv_precio
                    );
                    tvPrecio.setText(
                            "$" +
                                    objProducto.getString("precio_producto") +
                                    " MXN"
                    );
                    TextView tvExistencias = view.findViewById(
                            R.id.tv_exist
                    );
                    tvExistencias.setText(
                            objProducto.getString("cantidad")
                                    + " pzs."
                    );

            }
            catch(Exception e) {
                Log.e("Error lista prod", e.getMessage());
            }
        }
        return view;
    }
}