package com.example.dell.lab5;

import android.content.IntentFilter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.OvershootInRightAnimator;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    Boolean star_flag=false;
    private DynamicReceiver receiver;
    TextView text;
    //private AlertDialog.Builder mbuilder;
    //private RecyclerView mRecyclerView=(RecyclerView) findViewById(R.id.recycler_view);
    //private ListView list= (ListView) findViewById(R.id.shoppinglist);
    //private FloatingActionButton fab= findViewById(R.id.fab);

    final List<Map<String, Object>> double_text = new ArrayList<>();
    final List<Map<String, Object>> trible_text = new ArrayList<>();
    final List<Integer> double_num = new ArrayList<>();
    final List<Integer> trible_num = new ArrayList<>();
    String[] first_letters= new String[] {"E", "A", "D", "K", "w", "M", "F", "M", "L", "B"};
    String[] names= new String[] {"Enchated Forest", "Arla Milk", "Devondale Milk", "Kindle Oasis", "waitrose 早餐麦片", "Mcvitie's 饼干", "Ferrero Rocher", "Maltesers", "Lindt", "Borggreve"};
    String[] prices= new String[] {"¥ 5.00", "¥ 59.00", "¥ 79.00", "¥ 2399.00", " ¥ 179.00", "¥ 14.90", "¥ 132.59", "¥ 141.43", "¥ 139.43", "¥ 28.90"};
    String[] type = {"作者","产地","产地","版本","产地","重量","重量","重量","重量","重量"};
    String[] info = {"Johanna Basford","德国","澳大利亚","8GB","2Kg","英国","300g","118g","249g","640g"};
    String[] infomation = {"Johanna Basford","德国","澳大利亚","8GB","2Kg","英国","300g","118g","249g","640g"};
    SimpleAdapter simpleAdapter;

    @Override
    protected  void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);//设置新的intent  
        RecyclerView mRecyclerView=(RecyclerView) findViewById(R.id.recycler_view);
        ListView list= (ListView) findViewById(R.id.shoppinglist);
        FloatingActionButton fab= findViewById(R.id.fab);

        fab.setImageResource(R.mipmap.mainpage);
        list.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);

        receiver = new DynamicReceiver();
        IntentFilter dynamic_filter= new IntentFilter();
        dynamic_filter.addAction("MyDynamicFilter");
        registerReceiver(receiver, dynamic_filter);

        final AlertDialog.Builder mbuilder = new AlertDialog.Builder(this);
        final RecyclerView mRecyclerView =  (RecyclerView) findViewById(R.id.recycler_view) ;
        final ListView list= (ListView) findViewById(R.id.shoppinglist);

        int n=10;
        int number;
        Random random=new Random();
        number = random.nextInt(n);
        Intent intentBroadcast=new Intent("MyStaticFilter");
        Bundle bundle = new Bundle();
        bundle.putInt("data", number);
        intentBroadcast.putExtras(bundle);
        sendBroadcast(intentBroadcast);

        simpleAdapter=new SimpleAdapter(this,trible_text,R.layout.shopping_list_item,
                new String[]{"first_letter2", "name2", "price"},
                new int[]{R.id.first_letter2, R.id.name2,R.id.price});

        for(int i = 0; i < names.length; i++){
            Map<String, Object> temp = new LinkedHashMap<>();
            temp.put("first_letter", first_letters[i]);
            temp.put("name", names[i]);
            double_text.add(temp);
            double_num.add(i);
        }

        Map<String, Object> t = new LinkedHashMap<>();
        t.put("first_letter2","*");
        t.put("name2","购物车");
        t.put("price","价格");
        trible_num.add(-1);
        trible_text.add(t);


        list.setAdapter(simpleAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(position!=0){
                    Intent intent = new Intent(MainActivity.this, details.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("data", trible_num.get(position));
                    intent.putExtras(bundle);
                    startActivityForResult(intent,0);
                }
            }
        });

        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long id) {
                if(position != 0) {
                    mbuilder.setTitle("移除商品").setMessage("从对话框移除"+names[position]);
                    mbuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            trible_text.remove(position);
                            trible_num.remove(position);
                            simpleAdapter.notifyDataSetChanged();
                        }
                    });
                    mbuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {}
                    });
                    mbuilder.create().show();
                }
                return true;
            }
        });


        final FloatingActionButton fab= findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                star_flag = !star_flag;
                if(!star_flag){
                    fab.setImageResource(R.mipmap.shoplist);
                    list.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
                else{
                    fab.setImageResource(R.mipmap.mainpage);
                    list.setVisibility(View.VISIBLE);
                    mRecyclerView.setVisibility(View.GONE);
                }
            }
        });

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        LinearLayoutManager layoutManager =new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        layoutManager.scrollToPosition(0);
        mRecyclerView.setLayoutManager(layoutManager);
        final CommonAdapter commonAdapter = new CommonAdapter(this,R.layout.item,double_text) {
            @Override
            protected void convert(ViewHolder holder, Map<String, Object> data) {
                text = holder.getView(R.id.first_letter);
                text.setText(data.get("first_letter").toString());
                TextView first = holder.getView(R.id.name);
                first.setText(data.get("name").toString());
            }
        };
        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, details.class);
                Bundle bundle = new Bundle();
                bundle.putInt("data", double_num.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
            @Override
            public void onLongClick(final int position) {
                int a=position+1;
                double_text.remove(position);
                double_num.remove(position);
                commonAdapter.notifyDataSetChanged();
                Toast.makeText(getApplicationContext(), "第"+String.valueOf(a)+"个商品被删除", Toast.LENGTH_SHORT).show();
            }
        });
        // mRecyclerView.setAdapter(commonAdapter);
        ScaleInAnimationAdapter animationAdapter=new ScaleInAnimationAdapter(commonAdapter);
        animationAdapter.setDuration(1000);
        mRecyclerView.setAdapter(animationAdapter);
        mRecyclerView.setItemAnimator(new OvershootInRightAnimator());
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(INDEX come) {
        int number=come.getIndex();
        Map<String, Object> t = new LinkedHashMap<>();
        t.put("first_letter2",first_letters[number]);
        t.put("name2",names[number]);
        t.put("price",prices[number]);
        trible_num.add(number);
        trible_text.add(t);
        simpleAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        unregisterReceiver(receiver);
    }
}
