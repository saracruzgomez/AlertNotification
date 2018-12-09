package com.example.saracruz.alertnotification;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    // Modelo
    List<Item> items;

    // Referencias a objetos de la pantalla
    private RecyclerView item_list_view;
    private Button btn_add;

    // Adaptador
    private RecyclerView.Adapter adapter;

    private void saveItemList() {
        try {
            FileOutputStream outputStream = openFileOutput("items_noti.txt", MODE_PRIVATE);
            //for (int i = 0; i < items.size(); i++) {
            for (Item item : items) {
                String line = String.format("%s;%b\n", item.getTitle(), item.getDay(),item.getHour(),item.getTipo());
                outputStream.write(line.getBytes());
            }
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "No se ha podido abrir el fichero", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Toast.makeText(this, "No se ha podido escribir", Toast.LENGTH_SHORT).show();
        }
    }
    private void readItemList() {
        try {
            FileInputStream inputStream = openFileInput("items_noti.txt");
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(";");
                items.add(new Item(parts[0], parts[1], parts[2]); // esto no esta claro ni acabado
            }
        } catch (FileNotFoundException e) {
            Log.e("ShoppingList", "No he podido abrir el fichero");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        saveItemList();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Creamos el modelo y leemos los ítems de fichero
        items = new ArrayList<>();
        readItemList();

        // Obtenemos referencias a objetos de la pantalla
        item_list_view = findViewById(R.id.lista_notificadores);
        btn_add = findViewById(R.id.nueva_actividad);

        // Detectamos los clicks en el botón de añadir
        btn_add.setOnClickListener(new View.OnClickListener() {

        });

    }
}
