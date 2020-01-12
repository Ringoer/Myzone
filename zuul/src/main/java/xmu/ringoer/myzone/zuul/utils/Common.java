package xmu.ringoer.myzone.zuul.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Ringoer
 */
public class Common {
    public static String readStream(InputStream stream){
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(stream));
            String line;
            while ((line = in.readLine()) != null){
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
