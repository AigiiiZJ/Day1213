package com.example.day1214homework;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Administrator on 2017/12/14.
 */

public class NetUtils {

    private static ByteArrayOutputStream baos;

    public static String getStr(String path){

       //获取URL
        try {
            URL url = new URL(path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            int code = urlConnection.getResponseCode();
            if(code == 200){
                InputStream is = urlConnection.getInputStream();
                int len ;
                byte[] arr = new byte[1024*10];
                baos = new ByteArrayOutputStream();
                while((len=is.read(arr))!=-1){
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
