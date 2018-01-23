package com.example.administrator.day1213;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/12/13.
 */

public class NetUtils {

    private static ByteArrayOutputStream baos;

    //获取网络数据的方法
    public static String getStr(String path){

        try {
            URL url = new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            int code = urlConnection.getResponseCode();
            if(code==200){
                //获取数据成功
                InputStream is = urlConnection.getInputStream();
                int len ;
                byte[] arr = new byte[1024*20];
                baos = new ByteArrayOutputStream();

                while ((len=is.read(arr))!=-1){
                    baos.write(arr,0,len);
                }
                return baos.toString();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null ;
    }

}

