package xmu.ringoer.myzone.message.util;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

/**
 * @author Ringoer
 */
public class CommonUtil {

    private static DateTimeFormatter longFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    public static LocalDateTime DEFAULT_TIME = LocalDateTime.of(2019,11,11,11,11,11,11);

    /**
     * 生成唯一随机数
     * @param length 增加的几位随机数
     * @return 随机数
     */
    public static String getRandomNum(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * 从请求包头部解析出userId
     * @param request 请求包
     * @return userId
     */
    public static Integer getIdFromHeader(HttpServletRequest request){
        String userId = request.getHeader("userId");
        Integer userid = null;
        try{
            userid = Integer.parseInt(userId);
        }catch (Exception ignored){ }
        return userid;
    }

    /**
     * 将map转换为JSONObject
     * @param map map
     * @return JSONObject
     */
    public static JSONObject mapToJsonobject(Map<String, String> map) {
        if(null == map) {
            return null;
        }
        JSONObject ans = new JSONObject();
        String[] objects = map.toString().substring(1, map.toString().length() - 1).split(",");
        for(String object : objects) {
            String[] entry = object.split("=");
            ans.put(entry[0], entry[1]);
        }
        return ans;
    }
}
