package com.example.calistheniks.entities;

public class Producto {

    private int idProducto;
    private String categoria_producto;
    private String nombre_Producto;
    private String descripcion_producto;
    private String imagen_producto;
    private double precio_producto;
    private int cantidad;

    public int getIdProducto() {
        return idProducto;
    }

    public String getCategoria_producto() {
        return categoria_producto;
    }

    public String getNombre_Producto() {
        return nombre_Producto;
    }

    public String getDescripcion_producto() {
        return descripcion_producto;
    }

    public String getImagen_producto() {
        return imagen_producto;
    }

    public double getPrecio_producto() {
        return precio_producto;
    }

    public int getCantidad() {
        return cantidad;
    }
}
