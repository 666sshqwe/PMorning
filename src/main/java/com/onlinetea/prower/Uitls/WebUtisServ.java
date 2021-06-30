package com.onlinetea.prower.Uitls;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

@Component
public class WebUtisServ {

    public static String getReDoGet(String url){
        try {
            URL urlObject = new URL(url);
            //开链接
            URLConnection connection =  urlObject.openConnection();
            InputStream is = connection.getInputStream();
            byte[] b = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while((len=is.read(b))!=-1){
                sb.append(new String(b,0,len));
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "falid";
    }


    public String getReDoPost(String url,String data){
        try {
            URL urlObject = new URL(url);
            //开链接
            URLConnection connection =  urlObject.openConnection();
            connection.setDoOutput(true);
            OutputStream os = connection.getOutputStream();
            os.write(data.getBytes());
            os.close();
            InputStream is = connection.getInputStream();
            byte[] b = new byte[1024];
            int len;
            StringBuilder sb = new StringBuilder();
            while((len=is.read(b))!=-1){
                sb.append(new String(b,0,len));
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "falid";
    }


}
