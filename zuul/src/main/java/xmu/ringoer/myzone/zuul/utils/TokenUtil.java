package xmu.ringoer.myzone.zuul.utils;

import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

/**
 * @author Ringoer
 */
public class TokenUtil {

    private static final Logger logger = LoggerFactory.getLogger(TokenUtil.class);

    private static Base64.Encoder encoder = Base64.getEncoder();
    private static Base64.Decoder decoder = Base64.getDecoder();

    public static String getToken(HttpServletRequest request){
        String token = request.getHeader("authorization");
        if(null == token || "".equals(token)) {
            return null;
        }
        logger.info("token = " + token);
        return token;
    }

    public static String createToken(String userId, String roleId){
        return encoder.encodeToString(JwtUtil.createJwt(userId, roleId).getBytes());
        //return JwtUtil.createJwt(userId, role);
    }

    public static Claims parseToken(String token){
        String string;
        try{
            string = new String(decoder.decode(token), StandardCharsets.UTF_8);
        }catch(Exception e) {
            return null;
        }
        return JwtUtil.parseJwt(string);
        //return JwtUtil.parseJwt(token);
    }

    public static boolean checkTokenType(Claims claims){
        String userId = "userId", roleId = "roleId", until = "until";
        if(claims == null) {
            return false;
        } else if(!claims.keySet().contains(userId)) {
            return false;
        } else if(!claims.keySet().contains(roleId)) {
            return false;
        } else if(!claims.keySet().contains(until)) {
            return false;
        }
        return true;
    }

    public static boolean checkTokenTime(String token){
        try {
            long until = Long.parseLong(Objects.requireNonNull(parseToken(token)).get("until").toString());
            if(until <  System.currentTimeMillis()) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
