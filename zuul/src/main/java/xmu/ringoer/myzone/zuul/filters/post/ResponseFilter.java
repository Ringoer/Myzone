package xmu.ringoer.myzone.zuul.filters.post;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xmu.ringoer.myzone.zuul.utils.Common;
import xmu.ringoer.myzone.zuul.utils.ResponseUtil;
import xmu.ringoer.myzone.zuul.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author Ringoer
 */
public class ResponseFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(ResponseFilter.class);

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        logger.info(RequestContext.getCurrentContext().getResponseBody());
        if(null != RequestContext.getCurrentContext().getResponseBody()){
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        logger.info("Start Response Filter");
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        logger.info(String.format("%s AccessResponseFilter request to %s", request.getMethod(), request.getRequestURL().toString()));
        //使得zuul不再继续向下执行过滤器，在此处输出
        requestContext.setSendZuulResponse(false);
        //使得返回中文不乱码
        response.setContentType("application/json; charset=UTF-8");
        if(null == requestContext.getResponseDataStream()) {
            //响应码
            requestContext.setResponseStatusCode(ResponseUtil.HTTP_SUCCESS);
            //响应内容
            requestContext.setResponseBody(ResponseUtil.fail(ResponseUtil.SERVER_ERROR, "无回复！").toString());
        }
        String s = Common.readStream(requestContext.getResponseDataStream());
        logger.info("s = " + s);
        JSONObject resp = JSONObject.parseObject(s);
        logger.info("resp = " + resp.toString());
        Map<String, String> map = requestContext.getZuulRequestHeaders();
        JSONObject object = new JSONObject();
        logger.info("map keyset = " + map.keySet());
        logger.info("map values = " + map.values());
        String strToken = "token", str1 = "1", strLogin = "login", strExp = "exp";
        //如果不需要token
        if(null == map.get(strToken)){
            //如果是login请求
            if(null != map.get(strLogin) && str1.equals(map.get(strLogin))){
                Integer errno = resp.getInteger("errno");
                //如果登录成功
                if(null != errno && errno.equals(ResponseUtil.SUCCESS)){
                    JSONObject data = JSONObject.parseObject(resp.getString("data"));
                    String userId = data.getString("userId");
                    String roleId = data.getString("roleId");
                    String token = TokenUtil.createToken(userId, roleId);
                    logger.info("new token = " + token);
                    //响应码
                    requestContext.setResponseStatusCode(ResponseUtil.HTTP_SUCCESS);
                    object.put("token", token);
                    //响应内容
                    requestContext.setResponseBody(ResponseUtil.ok(object).toString());
                }
                else{
                    requestContext.setResponseStatusCode(requestContext.getResponseStatusCode());
                    requestContext.setResponseBody(resp.toString());
                }
            }
            //如果不是login请求
            else{
                requestContext.setResponseStatusCode(requestContext.getResponseStatusCode());
                requestContext.setResponseBody(resp.toString());
            }
        }
        //如果需要token
        else{
            //如果token已经过期
            if(null != map.get(strExp) && str1.equals(map.get(strExp))){
                //必须小写，因为被自动转换
                String userId = map.get("userid");
                String roleId = map.get("roleid");
                logger.info("in resp map keyset = " + map.keySet().toString());
                logger.info("in resp x-forwarded-for = " + map.get("x-forwarded-for"));
                logger.info("in resp x-forwarded-port = " + map.get("x-forwarded-port"));
                logger.info("in resp userId = " + userId);
                logger.info("in resp roleId = " + roleId);
                String token = TokenUtil.createToken(userId, roleId);
                object.put("token",token);
            }
            else{
                object.put("token",null);
            }
            response.setHeader("Access-Control-Allow-Origin","*");
            requestContext.setResponseStatusCode(requestContext.getResponseStatusCode());
            requestContext.setResponseBody(resp.toString());
        }
        return null;
    }
}
