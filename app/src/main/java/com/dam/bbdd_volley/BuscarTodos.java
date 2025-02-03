package com.dam.bbdd_volley;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BuscarTodos extends AppCompatActivity {
    ListView listView;
    List<Product> productArray;
    ArrayAdapter<Product> arrayAdapter;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_todos);

        listView = findViewById(R.id.listaLV);
        productArray = new ArrayList<>(); // Inicialización correcta

        // Configura el adaptador antes de la llamada Volley
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, productArray);
        listView.setAdapter(arrayAdapter);

        listarProducto();
    }

    public void listarProducto() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                "http://192.168.230.1/pruebaVolley/buscarTodos.php",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                Product product = new Product(
                                        jsonObject.getString("producto"),
                                        jsonObject.getString("fabricante"),
                                        jsonObject.getInt("codigo"),
                                        jsonObject.getDouble("precio")
                                );
                                productArray.add(product);
                            }
                            arrayAdapter.notifyDataSetChanged(); // Actualización segura
                        } catch (JSONException e) {
                            Log.e("JSONError", "Error parsing JSON: ", e);
                            Toast.makeText(getApplicationContext(), "Error al procesar los datos", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", "Error de conexión: ", error);
                        Toast.makeText(getApplicationContext(), "Error de conexión", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void goToMain(View v) {
        Intent intent = new Intent(BuscarTodos.this, MainActivity.class);
        startActivity(intent);
    }
}

