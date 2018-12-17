package com.example.saracruz.alertnotification;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.BreakIterator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    List<Item> items;
    private RecyclerView notificaciones_view;
    private Adapter adapter;
    public static final int EDIT_NOTIFICATION = 0;
    public static final int NEW_NOTIFICATION = 1;
    private TextView titlenoti_view;



    /*
    private void saveItemList() {
        try {
            FileOutputStream outputStream = openFileOutput("items_noti.txt", MODE_PRIVATE);
            //for (int i = 0; i < items.size(); i++) {
            for (Item item : items) {
                String line = String.format("%s;%s;%s;%d\n", item.getTitle(), item.getDay(), item.getHour(), item.getTipo());
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
                items.add(new Item(parts[0], parts[1], parts[2], Integer.valueOf(parts[3]))); //Te lo he acabado ;)
            }
        } catch (FileNotFoundException e) {
            Log.e("ShoppingList", "No he podido abrir el fichero");
        }
    }
    */

    @Override
    protected void onStop() {
        super.onStop();
        // saveItemList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("QueMeAvisesJoder");

        // Meto un comentario
        // 2o comentario


        titlenoti_view = findViewById(R.id.titlenoti_view);
        //titlenoti_view.setText(title); falta añadirloo!!!


        items = new ArrayList<>();
        // readItemList();
        items.add(new Item("Cumpleaños Amante", new Date(), 0,"shhhhhh"));
        items.add(new Item("Cumpleaños del Jefe", new Date(), 0,"shhhhh"));

        notificaciones_view = findViewById(R.id.notificaciones_view);
        // Cinfiguramos el RecyclerView con un Layout Manager y un Adaptador
        notificaciones_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter();
        notificaciones_view.setAdapter(adapter);
    }

    public void onClickAdd(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        startActivityForResult(intent, NEW_NOTIFICATION);
    }

    public void onClickItem(int pos ) {
        Toast.makeText(this, "Has clicado el item " + pos, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AddActivity.class);
        Item item = items.get(pos);
        intent.putExtra("titulo", item.getTitle());
        intent.putExtra("descripcion", item.getDescr());
        intent.putExtra("fecha", item.getDate());
        startActivityForResult(intent, EDIT_NOTIFICATION);
    }

    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        switch(requestCode){
            case NEW_NOTIFICATION: //éste me peta, debería de mostrar el nuevo item y no lo hace (preguntar al profe)
                if(resultCode == RESULT_OK){
                    String titulo = data.getStringExtra("titulo");
                    Date date = (Date) data.getSerializableExtra("fecha");
                    String descr = data.getStringExtra("descripcion");
                    items.add(new Item(titulo, date, 0, descr));
                    adapter.notifyItemInserted(items.size()-1);

                }
                break;

            case EDIT_NOTIFICATION:
                if(resultCode == RESULT_OK){
                    String titulo = data.getStringExtra("titulo");
                    Date date = (Date) data.getSerializableExtra("fecha");
                    String descr =  data.getStringExtra("descripcion");



                    titlenoti_view.setText(titulo); // necesito guardar y mostrar los cambios y no acabo de entender como hacerlo, pensaba que era así pero no detecta el texto
                    //date_view.setDate(date);
                   //descripcio_noti.setText(descr);
                }
                break;

        }

    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView titlenoti_view;
        TextView daynoti_view;
        TextView hournoti_view;
        ImageView iconoti_view;

        public ViewHolder (View itemView){
            super(itemView);
            this.titlenoti_view = itemView.findViewById(R.id.titlenoti_view);
            this.daynoti_view = itemView.findViewById(R.id.daynoti_view);
            this.hournoti_view = itemView.findViewById(R.id.hournoti_view);
            this.iconoti_view = itemView.findViewById(R.id.iconoti_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    onClickItem(pos);
                }
            });
        }
    }

    class Adapter extends RecyclerView.Adapter<ViewHolder>{
        @Override public int getItemCount() {return items.size();}


        @NonNull
        @Override

        public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent, int ViewType){
            // Creamos un item de la pantalla a partid del layout
            View itemView = getLayoutInflater().
                    inflate(R.layout.item_layout,parent,false);
            // Creamos (y retornamos) el ViewHolder asociado
            return new ViewHolder(itemView);

        }

        @Override

        public void onBindViewHolder(@NonNull ViewHolder holder, int position){
            // Vamos al modelo y obtenemos el valor en la posición que nos pasan
            Item item = items.get(position);
            holder.titlenoti_view.setText(item.getTitle());

            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            String fecha = formatDate.format(item.getDate());
            holder.daynoti_view.setText(fecha);

            SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm");
            String hora = formatHour.format(item.getDate());
            holder.hournoti_view.setText(hora);
        }
    }



}
