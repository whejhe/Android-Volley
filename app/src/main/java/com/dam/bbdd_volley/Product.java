package com.dam.bbdd_volley;

class Product {
    String producto, fabricante;
    int codigo;
    double precio;

    public Product(String producto, String fabricante, int codigo, double precio) {
        this.producto = producto;
        this.fabricante = fabricante;
        this.codigo = codigo;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "producto: '" + producto + '\'' +
                ", fabricante: '" + fabricante + '\'' +
                ", codigo: " + codigo +
                ", precio: " + precio;
    }
}

