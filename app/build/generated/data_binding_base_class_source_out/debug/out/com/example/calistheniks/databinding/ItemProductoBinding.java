// Generated by view binder compiler. Do not edit!
package com.example.calistheniks.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.calistheniks.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemProductoBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView favoritos;

  @NonNull
  public final ImageView ivImgProducto;

  @NonNull
  public final TextView tvExist;

  @NonNull
  public final TextView tvIdproducto;

  @NonNull
  public final TextView tvNomproducto;

  @NonNull
  public final TextView tvPrecio;

  private ItemProductoBinding(@NonNull LinearLayout rootView, @NonNull ImageView favoritos,
      @NonNull ImageView ivImgProducto, @NonNull TextView tvExist, @NonNull TextView tvIdproducto,
      @NonNull TextView tvNomproducto, @NonNull TextView tvPrecio) {
    this.rootView = rootView;
    this.favoritos = favoritos;
    this.ivImgProducto = ivImgProducto;
    this.tvExist = tvExist;
    this.tvIdproducto = tvIdproducto;
    this.tvNomproducto = tvNomproducto;
    this.tvPrecio = tvPrecio;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemProductoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemProductoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_producto, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemProductoBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.favoritos;
      ImageView favoritos = ViewBindings.findChildViewById(rootView, id);
      if (favoritos == null) {
        break missingId;
      }

      id = R.id.iv_img_producto;
      ImageView ivImgProducto = ViewBindings.findChildViewById(rootView, id);
      if (ivImgProducto == null) {
        break missingId;
      }

      id = R.id.tv_exist;
      TextView tvExist = ViewBindings.findChildViewById(rootView, id);
      if (tvExist == null) {
        break missingId;
      }

      id = R.id.tv_idproducto;
      TextView tvIdproducto = ViewBindings.findChildViewById(rootView, id);
      if (tvIdproducto == null) {
        break missingId;
      }

      id = R.id.tv_nomproducto;
      TextView tvNomproducto = ViewBindings.findChildViewById(rootView, id);
      if (tvNomproducto == null) {
        break missingId;
      }

      id = R.id.tv_precio;
      TextView tvPrecio = ViewBindings.findChildViewById(rootView, id);
      if (tvPrecio == null) {
        break missingId;
      }

      return new ItemProductoBinding((LinearLayout) rootView, favoritos, ivImgProducto, tvExist,
          tvIdproducto, tvNomproducto, tvPrecio);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
