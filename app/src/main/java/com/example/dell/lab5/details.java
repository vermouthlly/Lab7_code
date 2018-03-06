package com.example.dell.lab5;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.w3c.dom.Text;



import static android.widget.Toast.*;

public class details  extends AppCompatActivity {
    Boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        final int data = bundle.getInt("data");


        final String[] names = {"Enchated Forest", "Arla Milk", "Devondale Milk", "Kindle Oasis", "waitrose 早餐麦片",
                "Mcvitie's 饼干", "Ferrero Rocher", "Maltesers", "Lindt", "Borggreve"};
        final String[] ss = {"R.mipmap.enchatedforeat", "R.id.arla", "R.id.devondale", "R.id.kindle", "R.id.waitrose",
                "R.id.mcvitie", "R.id.ferrero", "R.id.maltesers", "R.id.lindt", "R.id.borggreve"};
        String[] prices = {"¥ 5.00 ", "¥ 59.00 ", "¥ 79.00 ", "¥ 2399.00 ", "¥ 179.00 ",
                "¥ 14.90", "¥ 132.59", "¥ 141.43", "¥ 139.43", "¥ 28.90"};
        String[] type = {"作者","产地","产地","版本","产地","重量","重量","重量","重量","重量"};
        final String[] info = {"Johanna Basford","德国","澳大利亚","8GB","2Kg","英国","300g","118g","249g","640g"};
        ImageView photo =  findViewById(R.id.photo_source);
        switch(data) {
            case 0:
                photo.setImageResource(R.mipmap.enchatedforest);
                break;
            case 1:
                photo.setImageResource(R.mipmap.arla);
                break;
            case 2:
                photo.setImageResource(R.mipmap.devondale);
                break;
            case 3:
                photo.setImageResource(R.mipmap.kindle);
                break;
            case 4:
                photo.setImageResource(R.mipmap.waitrose);
                break;
            case 5:
                photo.setImageResource(R.mipmap.mcvitie);
                break;
            case 6:
                photo.setImageResource(R.mipmap.ferrero);
                break;
            case 7:
                photo.setImageResource(R.mipmap.maltesers);
                break;
            case 8:
                photo.setImageResource(R.mipmap.lindt);
                break;
            case 9:
                photo.setImageResource(R.mipmap.borggreve);
                break;
        }

        TextView dname = (TextView) findViewById(R.id.name);
        TextView dprice = (TextView) findViewById(R.id.price);
        TextView moredetails = (TextView) findViewById(R.id.type);
        final TextView formore = (TextView) findViewById(R.id.formore);

        dname.setText(names[data]);
        dprice.setText(prices[data]);
        moredetails.setText(type[data]);
        formore.setText(info[data]);

        final Button star = (Button) findViewById(R.id.star);
        star.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                flag = !flag;
                if(flag) {
                    star.setBackgroundResource(R.mipmap.full_star);
                }
                else {
                    star.setBackgroundResource(R.mipmap.empty_star);
                }
            }
        });
        ListView listView = (ListView) findViewById(R.id.info_list);
        String[] four = new String[]{"一键下单", "分享商品", "不感兴趣", "查看更多商品促销信息"};
        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,four);
        listView.setAdapter(arrayAdapter);
        Button button1= findViewById(R.id.img_shoplist);
        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                EventBus.getDefault().post(new INDEX(data));
                Toast.makeText(details.this,"商品已加入购物车",Toast.LENGTH_SHORT).show();
                Intent intentBroadcast=new Intent("MyDynamicFilter");
                Bundle bundle = new Bundle();
                bundle.putInt("data", data);
                intentBroadcast.putExtras(bundle);
                sendBroadcast(intentBroadcast);
            }
        });
        Button button =  findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                finish();
            }
        });

    }
}