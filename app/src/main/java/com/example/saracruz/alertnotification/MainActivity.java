package com.example.saracruz.alertnotification;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.BreakIterator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    List<Item> items;
    private RecyclerView notificaciones_view;
    private Adapter adapter;
    public static final int EDIT_NOTIFICATION = 0;
    public static final int NEW_NOTIFICATION = 1;
    private Button btn_cancel;
    private TextView textview;





    //le indicamos donde guardará y de donde leerá las notificaciones
    private void saveItemList() {
        try {
            FileOutputStream outputStream = openFileOutput("items_noti.txt", MODE_PRIVATE);
            //for (int i = 0; i < items.size(); i++) {
            for (Item item : items) {
                String line = String.format("%s,%D,%s,%s", item.getTitle(), item.getDay(), item.getTipo(),item.getDescr());
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
                items.add(new Item(parts[0], new Date(parts[1]), Integer.valueOf(parts[2]), parts[3]));
            }
        } catch (FileNotFoundException e) {
            Log.e("AlertNotification", "No he podido abrir el fichero");
        }
    }




    @Override
    protected void onStop() {
        super.onStop();
        //  saveItemList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        setTitle("QueMeAvisesJoder");





        items = new ArrayList<>();
        //readItemList();
        //Rellenamos la lista de notificaciones con valores iniciales
        items.add(new Item("Sara",new Date(),0,"Necesito un baño caliente"));
        items.add(new Item("Joan",new Date(),0,"Necesita un baño de agua fría"));
        items.add(new Item("Joan pepe",new Date(),0,"Necesita un baño de agua fría"));
        items.add(new Item("Caducidad",new Date(),0,"Necesita un baño de agua fría"));
        items.add(new Item("Joan",new Date(),0,"Necesita un baño de agua fría"));
        items.add(new Item("Reutilizar",new Date(),0,"Necesita un baño de agua fría"));
        items.add(new Item("Joan",new Date(),0,"Necesita un baño de agua fría"));
        items.add(new Item("Sara",new Date(),0,"Necesito un baño caliente"));
        items.add(new Item("Joan",new Date(),0,"Necesita un baño de agua fría"));
        items.add(new Item("Joan pepe",new Date(),0,"Necesita un baño de agua fría"));
        notificaciones_view = findViewById(R.id.notificaciones_view);

        // Configuramos el RecyclerView con un Layout Manager y un Adaptador
        notificaciones_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter();
        notificaciones_view.setAdapter(adapter);

        textview = findViewById(R.id.StateView);

    }

    //Creamos un Intent que nos llevará a la pantalla para crear una nueva notificación: Paso 1
    public void onClickAdd(View view) {
        Intent intent = new Intent(this, AddActivity.class);
        Date date_default = new Date();
        intent.putExtra("fecha",date_default);
        startActivityForResult(intent, NEW_NOTIFICATION);

    }

    //Creamos un Intent que nos llevará a la pantalla para editar la notificación junto con los parámetros que queremos editar: Paso 1
    public void onClickItem(int pos ) {
        Toast.makeText(this, "Has clicado el item " + pos, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AddActivity.class);
        Item item = items.get(pos);
        intent.putExtra("titulo", item.getTitle());
        intent.putExtra("descripcion", item.getDescr());
        intent.putExtra("fecha", item.getDay());
        intent.putExtra("posicion",pos);
        //intent.putExtra("tipoNoti","EDIT");
        startActivityForResult(intent, EDIT_NOTIFICATION);
    }

    public void onClickItemR(int pos ) {
        Toast.makeText(this, "Has clicado el item " + pos, Toast.LENGTH_SHORT).show();
        Item item = items.get(pos);
        adapter.notifyItemRemoved(pos);
    }

    @SuppressLint("RestrictedApi")
    public void setSupportActionBar(){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDefaultDisplayHomeAsUpEnabled(true);

        }

    }
    //Paso4: refrescamos la pantalla
    public void onActivityResult ( int requestCode, int resultCode, Intent data){
        switch(requestCode){
            //refrescamos la pantalla  de la lista de notificaciones con la nueva notificación
            case NEW_NOTIFICATION:

                if(resultCode == RESULT_OK){
                    String titulo = data.getStringExtra("titulo");
                    Date fecha = (Date) data.getSerializableExtra("fecha");
                    String descr = data.getStringExtra("descripcion");

                    items.add(new Item(titulo,fecha, 0, descr));
                    adapter.notifyItemInserted(items.size()-1);
                }

                break;
            //refrescamos la pantalla  de la lista de notificaciones con los nuevos valores editados
            case EDIT_NOTIFICATION:

                if(resultCode == RESULT_OK){


                    String title = data.getStringExtra("titulo");
                    Date fecha = (Date) data.getSerializableExtra("fecha");
                    String descr =  data.getStringExtra("descripcion");
                    int pos = data.getIntExtra("posicion",0);

                    items.set(pos,new Item(title,fecha,0,descr));
                    adapter.notifyItemChanged(pos);
                }
                //Eliminamos item
                else if(resultCode == RESULT_OK*2) {
                    int pos = data.getIntExtra("posicion",0);
                    items.remove(pos);
                    adapter.notifyItemRemoved(pos);
                }
                break;
        }

    }


    class ViewHolder extends RecyclerView.ViewHolder{
        TextView titlenoti_view;
        TextView daynoti_view;
        TextView hournoti_view;
        ImageView iconoti_view;

        public ViewHolder (final View itemView){
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
            Date fecha = item.getDay();

            holder.titlenoti_view.setText(item.getTitle());

            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            String date = formatDate.format(fecha);
            holder.daynoti_view.setText(date);

            SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm");
            String hora = formatHour.format(fecha);
            holder.hournoti_view.setText(hora);



        }

    }


}

