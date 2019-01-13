package com.example.saracruz.alertnotification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {

    private static final String ANOTIF_CHANNEL_ID="com.example.saracruz.alertnotification.COMEXEMPLE";
    private static final String ANOTIF_CHANNEL_NAME="AlertNotif Cahnnel";
    private NotificationManager notificationManager;

    boolean isNotificactive;
    Date fecha;
    private NotificationManager manager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {



        boolean activa = intent.getBooleanExtra("activa", true);
        MediaPlayer mediaPlayer = MediaPlayer.create(context,
                Settings.System.DEFAULT_RINGTONE_URI);
                notificationcall(context);
        mediaPlayer.start();


        if (mediaPlayer != null && activa != true) {

            mediaPlayer.stop();

            mediaPlayer.release();
        }



    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void createChannels() {
        NotificationChannel notiChannel = new NotificationChannel(ANOTIF_CHANNEL_ID, ANOTIF_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
        notiChannel.enableLights(true);
        notiChannel.enableVibration(true);
        notiChannel.setLightColor(Color.GREEN);
        notiChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(notiChannel);
    }


    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void notificationcall(Context context){
        createChannels();
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(context,ANOTIF_CHANNEL_ID)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notificacion)
                .setContentTitle("Alarma activada!! ")
                .setContentText("THIS IS MY ALARM")
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo("Info")
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notificacion))
                .setTicker("");

        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notificationBuilder.build());

        isNotificactive = true;
    }

    public NotificationManager getManager() {
        if(manager==null){
            //manager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return manager;
    }
}

   /* Intent notificationIntent = new Intent(context, AddActivity.class);
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
                .setTicker("");


                notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(100, builder.build());

                isNotificactive = true;

*/









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


