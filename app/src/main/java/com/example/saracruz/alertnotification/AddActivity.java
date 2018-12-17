package com.example.saracruz.alertnotification;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AddActivity extends AppCompatActivity {

    private EditText titulo_noti;
    private EditText descripcio_noti;
    //private Date date;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        setTitle("QueMeAvisesJoder");

        Intent intent = getIntent();
        if(intent != null) {
            String titulo = intent.getStringExtra("titulo");
            String descripcion = intent.getStringExtra("descripcion");
            //Date date = (Date) intent.getSerializableExtra("fechahora"); Pídele ayuda al profe para el tema de la modificación de la fecha y la hora



            titulo_noti = findViewById(R.id.titulo_noti);
            titulo_noti.setText(titulo);
            descripcio_noti = findViewById(R.id.descripcio_noti);
            descripcio_noti.setText(descripcion);
            //date = findViewById(R.id.fecha);
            //date.setDate(date);
        }
    }


    public void onClickfecha(View view) {

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText(AddActivity.this, "Has cambiado la fecha...", Toast.LENGTH_SHORT).show();
            }
        }, 2018, 12, 10);

        dialog.show();
    }


    public void onClickhora(View view) {

        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(AddActivity.this,"Has cambiado la hora...", Toast.LENGTH_SHORT).show();
            }
        }, 13, 18,true);

        dialog.show();
    }

    public void onClickvolver(View view) {
        String titulo_editado = titulo_noti.getText().toString();
        String descripcion_editada = descripcio_noti.getText().toString();
        //Date fechahora = date.getDate().toString(); No estoy segura dde como pasarlo a String

        Intent data = new Intent();

        data.putExtra("titulo",titulo_editado);
        data.putExtra("descripcion",descripcion_editada);
        //data.putExtra("fechahora", fechahora);
        setResult(RESULT_OK,data);
        finish();
    }


}
