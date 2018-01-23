package com.example.day1214homework;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PullToRefreshListView plv;
    String path = "http://gank.io/api/data/Android/10/1";
    private List<Bean.ResultsBean> listdata;


    Handler handler = new Handler(){


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            String json = (String) msg.obj;
            Toast.makeText(MainActivity.this,json.toString(),Toast.LENGTH_SHORT).show();
            Gson gson = new Gson();
            Bean bean = gson.fromJson(json, Bean.class);
            listdata = bean.getResults();
            MyAdapter adapter = new MyAdapter();
            plv.setAdapter(adapter);


        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //找控件
        plv = (PullToRefreshListView) findViewById(R.id.plv);
        getData();

    }

    private void getData() {
       new Thread(){
           @Override
           public void run() {
               super.run();
               //获取网络数据
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
        public int getItemViewType(int position) {

            List<String> images = listdata.get(position).getImages();

            if(images.size()==0){
                return 0;
            }else {
                return 1;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder1 holder1 = null ;
            ViewHolder2 holder2 = null ;

            int type = getItemViewType(position);
            if(convertView == null){
                switch (type){
                    case 0:
                        holder1 = new ViewHolder1();
                        convertView = View.inflate(MainActivity.this, R.layout.plv_1, null);
                        holder1.tv1 = (TextView) convertView.findViewById(R.id.plv1_tv);
                        convertView.setTag(holder1);
                        break;

                    case 1:
                        holder2 = new ViewHolder2();

                        convertView = View.inflate(MainActivity.this,R.layout.plv_2,null);
                        holder2.iv = (ImageView) convertView.findViewById(R.id.plv2_iv);
                        holder2.tv2 = (TextView) convertView.findViewById(R.id.plv1_tv);
                        convertView.setTag(holder2);
                        break;
                }
            }else {
                switch (type){
                    case 0:
                        holder1 = (ViewHolder1) convertView.getTag();
                        break;
                    case 1:
                        holder2 = (ViewHolder2) convertView.getTag();
                        break;
                }
            }

            holder1.tv1.setText(listdata.get(position).getDesc());

            holder2.tv2.setText(listdata.get(position).getDesc());
            ImageLoader.getInstance().displayImage(listdata.get(position).getImages().get(position),holder2.iv);


            return convertView;
        }

        class ViewHolder1{
            TextView tv1 ;
        }

        class ViewHolder2{
            TextView tv2 ;
            ImageView iv ;
        }
    }

}
