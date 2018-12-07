package com.example.saracruz.alertnotification;

import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Item> items;
    private RecyclerView notificaciones_view;
    private Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("QueMeAvisesJoder");

        // Meto un comentario
        // 2o comentario

        items = new ArrayList<>();
        items.add(new Item("Cumpleaños Amante", "13/13/2018", "13:13", 0));
        items.add(new Item("Cumpleaños del Jefe", "06/06/2018", "06:06", 0));

        notificaciones_view = findViewById(R.id.notificaciones_view);
        // Cinfiguramos el RecyclerView con un Layout Manager y un Adaprador
        notificaciones_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter();
        notificaciones_view.setAdapter(adapter);


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
            holder.daynoti_view.setText(item.getDay());
            holder.hournoti_view.setText(item.getHour());



        }


    }
}
