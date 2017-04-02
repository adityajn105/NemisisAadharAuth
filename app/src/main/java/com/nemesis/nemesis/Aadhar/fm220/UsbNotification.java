package com.nemesis.nemesis.Aadhar.fm220;

import android.app.NotificationManager;
import android.content.Context;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;

public class UsbNotification
{
  private Context mApplicationContext;
  private NotificationManager mNotificationManager;
  private int mNotificationId = 255;
  private static UsbNotification instance = null;
  
  private UsbNotification(Context context)
  {
    this.mApplicationContext = context;
    this.mNotificationManager = ((NotificationManager)this.mApplicationContext.getSystemService("notification"));
  }
  
  public static synchronized UsbNotification getInstance(Context context)
  {
    if (instance == null) {
      instance = new UsbNotification(context);
    }
    return instance;
  }
  
  public Object clone()
    throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException();
  }
  
  public void createNotification(String s)
  {
    Builder mBuilder = new Builder(this.mApplicationContext)
      .setSmallIcon(17301591)
      .setContentTitle("ECS")
      .setAutoCancel(true)
      .setOnlyAlertOnce(true)
      .setContentText(s);
    
    this.mNotificationManager.cancel(this.mNotificationId);
    this.mNotificationManager.notify(this.mNotificationId, mBuilder.build());
  }
  
  public void createNotification(String s, Uri sound)
  {
    Builder mBuilder = new Builder(this.mApplicationContext)
      .setSmallIcon(17301591)
      .setContentTitle("ECS")
      .setAutoCancel(true)
      .setSound(sound)
      .setOnlyAlertOnce(true)
      .setContentText(s);
    
    this.mNotificationManager.cancel(this.mNotificationId);
    this.mNotificationManager.notify(this.mNotificationId, mBuilder.build());
  }
  
  public void cancelNofitications()
  {
    this.mNotificationManager.cancelAll();
  }
}
