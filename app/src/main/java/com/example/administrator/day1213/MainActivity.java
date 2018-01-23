package com.example.administrator.day1213;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private PullToRefreshListView plv;
    private List<Bean.DataBean> listdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //找控件
        plv = (PullToRefreshListView) findViewById(R.id.plv);


        getData();

    }

    private void getData() {

        new Mysnc().execute("http://api.expoon.com/AppNews/getNewsList/type/1/p/1");

    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
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
            return null;
        }
    }

    private class Mysnc extends AsyncTask<String,String,String>{



        @Override
        protected String doInBackground(String... strings) {
            String path = strings[0];
            String json = NetUtils.getStr(path);
            return json;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Gson gson = new Gson();
            Bean bean = gson.fromJson(s, Bean.class);
            listdata = bean.getData();
            //Toast.makeText(MainActivity.this,listdata.toString(),Toast.LENGTH_SHORT).show();
            MyAdapter adapter =  new MyAdapter();
            plv.setAdapter(adapter);



        }
    }
}
