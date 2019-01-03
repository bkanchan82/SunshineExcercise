package architech.android.com.sunshineexcercise.ui;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import architech.android.com.sunshineexcercise.R;

public class NotificationUtils {

    private static final int WEATHER_NOTIFICATION_ID = 1832;

    private static final String NOTIFICATION_CHANNEL_ID = "weather_notification_channel_one";

    public static void clearAllNotifications(Context context){
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void showNotification(Context context){

        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.weather_notification_channel_one),
                    NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
                .setColor(context.getResources().getColor(R.color.colorPrimary))
                .setContentIntent(buildIntent(context))
                .setSmallIcon(R.drawable.art_clear)
                .setLargeIcon(largIcon(context))
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(context.getString(R.string.default_message))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.default_message)
                ))
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(WEATHER_NOTIFICATION_ID,builder.build());

    }

    private static final int LIST_ACTIVITY_REQUEST_CODE = 105;

    private static PendingIntent buildIntent(Context context){
        Intent intent = new Intent(context,ListActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                LIST_ACTIVITY_REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    private static Bitmap largIcon(Context context){
        Resources resources = context.getResources();
        Bitmap bitmap = BitmapFactory.decodeResource(resources,R.drawable.art_clear);
        return bitmap;
    }

}
