package xmu.ringoer.myzone.zuul.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * @author Ringoer
 */
public class HttpUtil {
    public static String sendOtherRequest(String url, Map<String,Object> map, String userId, String method){
        String result = "";
        try {
            HttpURLConnection connection = (HttpURLConnection)(new URL(url)).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(method);
            //转发token解析出的userId
            connection.setRequestProperty("userId", userId);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Content-Type", "application/json");
            //设置与服务器保持连接
            connection.addRequestProperty("Connection","Keep-Alive");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;0.9");

            JSONObject param = new JSONObject();
            if(map != null){
                for(Map.Entry<String,Object> entry : map.entrySet()){
                    param.put(entry.getKey(), entry.getValue());
                }
            }

            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(param);
            out.flush();

            result = Common.readStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sendGetRequest(String url, Map<String,Object> map, String userId){
        String result = "";
        try {
            StringBuilder param = new StringBuilder("");

            if(map != null){
                for(Map.Entry<String,Object> entry : map.entrySet()){
                    param.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                param.deleteCharAt(param.length() - 1);
            }
            String currentUrl = url + "?" + param;

            URLConnection connection = (new URL(currentUrl)).openConnection();
            //转发token解析出的userId
            connection.setRequestProperty("userId", userId);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            //设置与服务器保持连接
            connection.addRequestProperty("Connection","Keep-Alive");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;0.9");

            result = Common.readStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
