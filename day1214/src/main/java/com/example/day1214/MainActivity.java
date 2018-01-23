package com.example.day1214;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    String path = "http://api.expoon.com/AppNews/getNewsList/type/1/p/1" ;
    private List<Bean.ResultBean.DataBean> listdata;


    Handler handler = new Handler(){



        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String json = (String) msg.obj;
            Gson gson = new Gson();
            Bean bean = gson.fromJson(json, Bean.class);
            listdata = bean.getResult().getData();
            //Toast.makeText(MainActivity.this,listdata.toString(),Toast.LENGTH_SHORT).show();
            MyAdapter adapter = new MyAdapter();
            lv.setAdapter(adapter);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //找控件
        lv = (ListView) findViewById(R.id.lv);

        new Thread(){
            @Override
            public void run() {
                super.run();

                String json = NetUtils.getStr(path);
                Message msg = new Message();
                msg.obj = json ;
                handler.sendMessage(msg);

            }
        }.start();
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return listdata.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView = View.inflate(MainActivity.this, R.layout.lv_items, null);
            }

            ImageView iv = (ImageView) convertView.findViewById(R.id.lv_iv);
            TextView tv = (TextView) convertView.findViewById(R.id.lv_tv);

            tv.setText(listdata.get(position).getTitle());
            ImageLoader.getInstance().displayImage(listdata.get(position).getThumbnail_pic_s(),iv);


            return convertView;
        }
    }
}
