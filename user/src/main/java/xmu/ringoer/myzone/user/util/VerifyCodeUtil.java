package xmu.ringoer.myzone.user.util;

import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author Ringoer
 * 在JWT基础上再套一层Base64得到token
 */
public class VerifyCodeUtil {

    private static final Logger logger = LoggerFactory.getLogger(VerifyCodeUtil.class);

    private static Base64.Encoder encoder = Base64.getEncoder();
    private static Base64.Decoder decoder = Base64.getDecoder();

    /**
     * 私钥
     */
    private static String key = "Ringoer";

    /**
     * 签发人
     */
    private static String subject = "myzone";

    /**
     * 5分钟的token有效期
     */
    private static Long life = 1000*60*5L;

    /**
     * 1天的jwt有效期
     * 由于jwt有效期一旦过期，就会报错，所以要另造一个有效期标准
     */
    private static Long expiresMillSeconds = 1000*60*5L;

    /**
     创建一个JWT
     @param username 用户名
     @param password 密码
     @param email 电子邮件
     @return JWT字符串
     **/

    public static String createJwt(String username, String password, String email, String action){
        //指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        Integer capacity = 10;
        //创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<>(capacity);
        claims.put("username", username);
        claims.put("password", password);
        claims.put("email", email);
        claims.put("action", action);

        //下面就是在为payload添加各种标准声明和私有声明了
        //这里其实就是new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                //如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setClaims(claims)
                //设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setId(UUID.randomUUID().toString())
                //iat: jwt的签发时间
                .setIssuedAt(now)
                //代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .setSubject(subject)
                //设置签名使用的签名算法和签名使用的秘钥
                .signWith(signatureAlgorithm, key);
        // 添加Token过期时间
        if (expiresMillSeconds >= 0) {
            long expMillis = nowMillis + expiresMillSeconds;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp).setNotBefore(now);
        }
        return builder.compact();
    }

    /**
     * 解析一个JWT
     * @param jwt 已经经过Base64拆解的jwt
     * @return 解析完成的JWT信息体
     */
    public static Claims parseJwt(String jwt) {
        //得到DefaultJwtParser
        Claims claims = null;
        try{
            claims = Jwts.parser()
                    //设置签名的秘钥
                    .setSigningKey(key)
                    //设置需要解析的jwt
                    .parseClaimsJws(jwt).getBody();
        }
        catch (Exception e){
            logger.info("jwt parser err");
            return null;
        }
        return claims;
    }

    public static String createVerifyCode(String username, String password, String email, String action) {
        return encoder.encodeToString(createJwt(username, password, email, action).getBytes());
    }

    public static Map<String, String> parseVerifyCode(String verifyCode){
        Map<String, String> ans = new HashMap<>();
        String string;
        try{
            string = new String(decoder.decode(verifyCode), StandardCharsets.UTF_8);
            Claims claims = parseJwt(string);
            if(claims != null) {
                for(String key : claims.keySet()) {
                    ans.put(key, claims.get(key).toString());
                }
            }
            return ans;
        }catch(Exception e) {
            return ans;
        }
    }
}
