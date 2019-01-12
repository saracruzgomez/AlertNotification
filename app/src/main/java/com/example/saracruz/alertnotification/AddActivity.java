package com.example.saracruz.alertnotification;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class AddActivity extends AppCompatActivity {

    private EditText titulo_noti;
    private EditText descripcio_noti;
    private TextView fecha_view;
    private TextView hora_view;
    private Date fecha;
    private int pos_i;
    private TextView stateview;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;


    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        Intent intent = getIntent();
        if(intent != null) {
            String titulo = intent.getStringExtra("titulo");
            String descripcion = intent.getStringExtra("descripcion");
            fecha = (Date) intent.getSerializableExtra("fecha");
            int pos = intent.getIntExtra("posicion",0);
            //String tipo_noti = intent.getStringExtra("tipoNoti");


            titulo_noti = findViewById(R.id.titulo_noti);
            titulo_noti.setText(titulo);

            descripcio_noti = findViewById(R.id.descripcio_noti);
            descripcio_noti.setText(descripcion);

            fecha_view = findViewById(R.id.fechaview);
            hora_view = findViewById(R.id.horaview);
            stateview = findViewById(R.id.StateView);
            pos_i=pos;

            SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm");
            String hora = formatHour.format(fecha);
            hora_view.setText(hora);
            SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
            String date = formatDate.format(fecha);
            fecha_view.setText(date);
        }


        findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelAlarm();
            }
        });



        setTitle("QueMeAvisesJoder");
        findViewById(R.id.btn_set).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar c =  Calendar.getInstance();

                if(Build.VERSION.SDK_INT >= 23) {
                    c.setTime(fecha);
                }
                else{
                    c.set(
                            datePickerDialog.getDatePicker().getYear(),
                            datePickerDialog.getDatePicker().getMonth(),
                            datePickerDialog.getDatePicker().getDayOfMonth(),
                            fecha.getHours(),
                            fecha.getMinutes(),0

                    );



                }

                    startalarm(c.getTimeInMillis());

            }
        });







    }

    private void startalarm(long timeInMillis) {

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlarmReceiver.class);
        intent.putExtra("activa",true);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis,
                AlarmManager.INTERVAL_DAY, pendingIntent);
        boolean isNotifyactive = true;

        stateview.setText("Alarma Activada");
        Toast.makeText(this, "Alarma Activada", Toast.LENGTH_SHORT).show();
    }
    private  void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlarmReceiver.class);
        intent.putExtra("activa",false);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0,intent,0);
        Toast.makeText(this, "Alarma Desactivada", Toast.LENGTH_SHORT).show();
        alarmManager.cancel(pendingIntent);
        stateview.setText("Alarma Desactivada");
    }


    public void setSupportActionBar(){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("AddNotification");
        }
    }


    public void onClickfecha(View view) {

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText(AddActivity.this, "Has cambiado la fecha...", Toast.LENGTH_SHORT).show();
                setDay(dayOfMonth, month, year);
                SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
                String date = formatDate.format(fecha);
                fecha_view.setText(date);

            }
        }, 2018, 12, 10);

        datePickerDialog.show();
    }


    public void onClickhora(View view) {

        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(AddActivity.this,"Has cambiado la hora...", Toast.LENGTH_SHORT).show();
                setHour(hourOfDay, minute);
                SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm");
                String hora = formatHour.format(fecha);
                hora_view.setText(hora);
            }
        },00, 00,true);

        timePickerDialog.show();



    }

    private void setHour(int hourOfDay, int minute) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(fecha);
        calendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        calendar.set(Calendar.MINUTE,minute);
        fecha=calendar.getTime();

    }

    private void setDay(int dayOfMonth, int month, int year) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(fecha);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.YEAR, year);
        fecha = calendar.getTime();

    }

    public void onClickvolver(View view) {

        String titulo_editado = this.titulo_noti.getText().toString();
        String descripcion_editada = this.descripcio_noti.getText().toString();
        Date fecha_editada = this.fecha;
        int pos = this.pos_i;

        Intent data = new Intent();

        data.putExtra("titulo",titulo_editado);
        data.putExtra("descripcion",descripcion_editada);
        data.putExtra("fecha",fecha_editada);
        data.putExtra("posicion",pos);
        setResult(RESULT_OK,data);
        finish();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.option, menu);
        return true;
    }


    public boolean onOptionsItemSelected (MenuItem item){
        switch (item.getItemId()){
            case R.id.delete:
                deleteItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void deleteItem() {
        int pos = this.pos_i;
        Intent data = new Intent();
        data.putExtra("posicion",pos);
        setResult(RESULT_OK*2,data);
        finish();
    }

}
