package com.example.semana02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.semana02.adapter.UserAdapter;
import com.example.semana02.entity.Product;
import com.example.semana02.service.ServiceUser;
import com.example.semana02.util.ConnectionRest;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //ListView y Adapater
    GridView lstUser;
    ArrayList<Product> listaUser = new ArrayList<Product>();
    UserAdapter userAdapter;

    Button   btnFiltrar;

    //conecta al servicio REST
    ServiceUser serviceUser;

    private List<Product> listaTotalUsuarios;


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

        lstUser = findViewById(R.id.lstUsuarios);
        userAdapter = new UserAdapter(this, R.layout.product_item, listaUser);
        lstUser.setAdapter(userAdapter);


        //Relaciona las variables con las variables de la GUI
        btnFiltrar = findViewById(R.id.btnFiltrar);

        //Conecta al servicio REST
        serviceUser = ConnectionRest.getConnecion().create(ServiceUser.class);


        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargaUsuarios();
            }
        });

        lstUser.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Product obj =  listaUser.get(position);
                Intent intent = new Intent(MainActivity.this, MainViewDetail.class);
                intent.putExtra("var_product", obj);
                startActivity(intent);


            }
        });

    }

    void cargaUsuarios(){
        Call<List<Product>> call = serviceUser.listaProductos();
        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                   if (response.isSuccessful()){
                       listaTotalUsuarios = response.body();
                       listaUser.clear();
                       listaUser.addAll(listaTotalUsuarios);
                       userAdapter.notifyDataSetChanged();
                   }
            }
            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {

            }
        });

    }

}