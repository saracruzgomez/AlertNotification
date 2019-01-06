package com.example.saracruz.alertnotification;

import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.support.v4.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



public class AddActivity extends AppCompatActivity {

    private EditText titulo_noti;
    private EditText descripcio_noti;
    private TextView fecha_view;
    private TextView hora_view;
    //private Date editfecha;
    private Date fecha;
    private int pos_i;
    private TextView stateview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_layout);

        setTitle("QueMeAvisesJoder");

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

            pos_i=pos;
        }


    }

    public void setSupportActionBar(){
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if(actionBar!= null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("AddNotification");
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void onClickActivar(View view){
        startalarm(fecha);
        stateview.setText("Activada");
    }

    public void onClickDesactivar(View view){
        cancelAlarm();
        stateview.setText("Desactivada");
    }

    public void onClickfecha(View view) {

        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText(AddActivity.this, "Has cambiado la fecha...", Toast.LENGTH_SHORT).show();
                setDay(dayOfMonth, month, year);
                SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
                String date = formatDate.format(fecha);
                fecha_view.setText(date);
            }
        }, 2018, 12, 10);

        dialog.show();
    }


    public void onClickhora(View view) {

        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                Toast.makeText(AddActivity.this,"Has cambiado la hora...", Toast.LENGTH_SHORT).show();
                setHour(hourOfDay, minute);
                SimpleDateFormat formatHour = new SimpleDateFormat("HH:mm");
                String hora = formatHour.format(fecha);
                hora_view.setText(hora);
            }
        }, 13, 18,true);

        dialog.show();



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



    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)


    private void updateTimeText(Calendar c){
        String timeText = "Alarm set for: ";
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());

        stateview.setText(timeText);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void startalarm(Date fecha){

        Calendar c = new GregorianCalendar();
        c.setTime(fecha);
        Intent intent = new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 10,intent,PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);


    }

    private  void cancelAlarm(){
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this,AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1,intent,0);

        alarmManager.cancel(pendingIntent);
        stateview.setText("Alarm canceled");
    }


}
