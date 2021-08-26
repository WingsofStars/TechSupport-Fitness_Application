package delware.apps.techsupport_scampermobile;

import static delware.apps.techsupport_scampermobile.MainActivity.CHANNEL_ID;
import static delware.apps.techsupport_scampermobile.Screens.trackingScreen.INTENT_START_NAME;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import delware.apps.techsupport_scampermobile.Screens.trackingScreen;

public class NavigationService extends Service {
    public static trackingScreen.State state;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String input = intent.getStringExtra(INTENT_START_NAME);
        Intent notificationIntent = new Intent(this, trackingScreen.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

       Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
               .setContentTitle("Location Service"). setContentText(input)
               .setSmallIcon(R.drawable.ic_baseline_directions_run_24)
               .setContentIntent(pendingIntent).build();

       startForeground(1, notification);

       if (state == trackingScreen.State.stopped){
           stopSelf();
       }

       return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
