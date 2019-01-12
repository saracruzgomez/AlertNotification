package com.example.saracruz.alertnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {


Date fecha;

    private NotificationManager notificationManager;
    boolean isNotificactive;

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean activa = intent.getBooleanExtra("activa",true);
        MediaPlayer mediaPlayer = MediaPlayer.create(context,
                Settings.System.DEFAULT_RINGTONE_URI);
        mediaPlayer.start();

        if(mediaPlayer!=null && activa!= true){
            mediaPlayer.release();
        }

        Intent notificationIntent = new Intent(context, AddActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setAutoCancel(true) //Cuando se pulsa la notificación, ésta  desaparece
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notificacion)
                .setContentTitle("Alarma activada!! ")
                .setContentText("THIS IS MY ALARM")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo("Info")
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notificacion))
                .setTicker("" );


        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(100,builder.build());

        isNotificactive = true;















        /*
        notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        Intent repeating_intent = new Intent(context,AddActivity.class);

        context.startService(repeating_intent);
        Calendar c = Calendar.getInstance();
        int hora,minuto, dia, mes,año;


        hora = c.get(Calendar.HOUR_OF_DAY);
        minuto = c.get(Calendar.MINUTE);
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        año = c.get(Calendar.YEAR);







        Intent notificationIntent = new Intent(context, MainActivity.class);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        long[] pattern = new long[]{2000, 1000, 2000};

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentIntent(contentIntent)

                .setTicker("" )
                .setContentTitle("alarma ")
                .setContentTitle("")
        //.setContentText(t)
                .setContentInfo("Info")
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notificacion))
                .setSmallIcon(R.drawable.ic_notificacion)
                .setAutoCancel(true) //Cuando se pulsa la notificación, ésta  desaparece
                .setSound(defaultSound)
                .setVibrate(pattern);

        Notification notificacion = new NotificationCompat.BigTextStyle(builder)
          //      .bigText(t)
                .setBigContentTitle("ejemplo")
                .setSummaryText("Resumen de tareas")
                .build();


    }

}






    /*public void onReceive(Context context, Intent intent) {

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getAlert();
        notificationHelper.getManager().notify(1,nb.build());
    }*/
    }
}
