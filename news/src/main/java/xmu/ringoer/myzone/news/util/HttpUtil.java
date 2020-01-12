package xmu.ringoer.myzone.news.util;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author Ringoer
 */
public class HttpUtil {
    public static String readStream(InputStream stream, boolean isEnter){
        StringBuilder result = new StringBuilder("");
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = in.readLine()) != null){
                result.append(line);
                if(isEnter) {
                    result.append('\n');
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String sendRequest(String url, Object data, String method){
        String result = "";
        try {
            HttpURLConnection connection = (HttpURLConnection)(new URL(url)).openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod(method);
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestProperty("Content-Type", "application/json");
            //设置与服务器保持连接
            connection.addRequestProperty("Connection","Keep-Alive");
            connection.setRequestProperty("Accept-Language", "zh-CN,zh;0.9");

            JSONObject body = JSONObject.parseObject(JacksonUtil.toJson(data));

            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(body);
            out.flush();

            result = readStream(connection.getInputStream(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
