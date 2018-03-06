package com.example.dell.lab5;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.view.View;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Objects;

import static android.content.Context.NOTIFICATION_SERVICE;
/**
 * Created by dell on 2017/10/28.
 */

public class StaticReceiver extends BroadcastReceiver{

    String[] names= new String[] {"Enchated Forest", "Arla Milk", "Devondale Milk", "Kindle Oasis", "waitrose 早餐麦片", "Mcvitie's 饼干", "Ferrero Rocher", "Maltesers", "Lindt", "Borggreve"};
    String[] prices= new String[] {"¥ 5.00", "¥ 59.00", "¥ 79.00", "¥ 2399.00", " ¥ 179.00", "¥ 14.90", "¥ 132.59", "¥ 141.43", "¥ 139.43", "¥ 28.90"};
    String[] type = {"作者","产地","产地","版本","产地","重量","重量","重量","重量","重量"};
    String[] info = {"Johanna Basford","德国","澳大利亚","8GB","2Kg","英国","300g","118g","249g","640g"};
    String[] infomation = {"Johanna Basford","德国","澳大利亚","8GB","2Kg","英国","300g","118g","249g","640g"};
    int[] pic= new int[] {R.mipmap.enchatedforest, R.mipmap.arla, R.mipmap.devondale, R.mipmap.kindle, R.mipmap.waitrose, R.mipmap.mcvitie, R.mipmap.ferrero, R.mipmap.maltesers, R.mipmap.lindt, R.mipmap.borggreve};

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("MyStaticFilter")) {
            Bundle bundle=intent.getExtras();
            int number=bundle.getInt("data");

            Intent i = new Intent(context, details.class);
            Bundle b = new Bundle();
            b.putInt("data", number);
            i.putExtras(b);

            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder builder=new Notification.Builder(context);
            builder.setContentTitle("新商品热卖")
                    .setContentText(names[number]+"仅售"+prices[number]+"!")
                    .setTicker("您有一条新消息")
                    .setSmallIcon(pic[number])
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), pic[number]))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.notify(0, builder.build());
        }
    }
}
