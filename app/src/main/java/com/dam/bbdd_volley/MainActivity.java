package com.dam.bbdd_volley;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText codigoET, productoET, precioET, fabricanteET;
    Button buscarBtton, agregarBtton, editarBtton, eliminarBtton;
    //De la librería de Volley que hemos implementado.
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //Relacionamos las variables de instancia con los componentes
        codigoET = findViewById(R.id.codigoET);
        productoET = findViewById(R.id.productoET);
        precioET = findViewById(R.id.precioET);
        fabricanteET = findViewById(R.id.fabricanteET);

        buscarBtton = findViewById(R.id.buscarButton);
        agregarBtton = findViewById(R.id.agregarButton);
        editarBtton = findViewById(R.id.editarButton);
        eliminarBtton = findViewById(R.id.eliminarButton);

    }

    /**
     * 10.0.2.2 es correcto si estás ejecutando el servidor local en el host (PC) y
     * usando el emulador de Android. Si estás usando un dispositivo físico, necesitarás reemplazarlo
     * con la IP del host en la misma red.
     * @param view
     */
    public void insertarProducto(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.230.1/pruebaVolley/insertar_producto.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Operacion exitosa", Toast.LENGTH_SHORT).show();
                limpiarFormulario();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                //Estas son las $variables del microservicio
                parametros.put("codigo", codigoET.getText().toString());
                parametros.put("producto", productoET.getText().toString());
                parametros.put("precio", precioET.getText().toString());
                parametros.put("fabricante", fabricanteET.getText().toString());

                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void buscarProducto(View view) {
        //Recibimos un Json
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("http://192.168.230.1/pruebaVolley/buscar_producto.php?codigo=" + codigoET.getText().toString(), new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        productoET.setText(jsonObject.getString("producto"));
                        precioET.setText(String.valueOf(jsonObject.getDouble("precio")));
                        fabricanteET.setText(jsonObject.getString("fabricante"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void editarProducto(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.230.1/pruebaVolley/editar_producto.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Producto editado", Toast.LENGTH_SHORT).show();
                limpiarFormulario();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("codigo", codigoET.getText().toString());
                parametros.put("producto", productoET.getText().toString());
                parametros.put("precio", precioET.getText().toString());
                parametros.put("fabricante", fabricanteET.getText().toString());

                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void eliminarProducto(View view) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.230.1/pruebaVolley/eliminar_producto.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Producto Eliminado", Toast.LENGTH_SHORT).show();
                limpiarFormulario();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("codigo", codigoET.getText().toString());

                return parametros;
            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void limpiarFormulario() {
        codigoET.setText("");
        productoET.setText("");
        precioET.setText("");
        fabricanteET.setText("");
    }

    public void goToList(View v) {
        Intent intent = new Intent(MainActivity.this, BuscarTodos.class);
        startActivity(intent);
    }
}