package xmu.ringoer.myzone.zuul.filters.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import xmu.ringoer.myzone.zuul.dao.ZuulDao;
import xmu.ringoer.myzone.zuul.utils.*;
import xmu.ringoer.myzone.zuul.utils.ResponseUtil;
import xmu.ringoer.myzone.zuul.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ringoer
 * 判断当前访问的URI是否需要token
 * 若需要，则转至下一级
 * 若不需要，则直接回复
 */
@Component
public class LoginFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(LoginFilter.class);

    @Autowired
    private ZuulDao zuulDao;

//    private String[] noTokenURIs = {
//            "/user/login",
//            "/admin/login",
//            "/user/register",
//            "/ad"
//    };

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        logger.info(String.format("%s AccessLoginFilter request to %s", request.getMethod(), request.getRequestURL().toString()));

        String regex = ":" + request.getLocalPort();
        logger.info("regex for split to get URL = " + regex);

        String url = request.getRequestURL().toString().split(regex)[1];
        logger.info("url = " + url);

        String ip = request.getRemoteAddr();
        int port = request.getRemotePort();
        requestContext.addZuulRequestHeader("ip", ip);
        requestContext.addZuulRequestHeader("port", Integer.toString(port));

        logger.info("ADDR = " + request.getRemoteAddr());
        logger.info("PORT = " + request.getRemotePort());

        //判断当前URI是否需要token

        //如果不需要token
        if(zuulDao.checkNoTokenUris(url, request.getMethod())) {
            boolean isLogin = (
                    ("/user/login".equals(url) || "/admin/login".equals(url))
                    && "POST".equals(request.getMethod())
                    && null == TokenUtil.getToken(request));

            if(isLogin){
                requestContext.addZuulRequestHeader("login", "1");
            }
            requestContext.setSendZuulResponse(true);
            //响应码
            requestContext.setResponseStatusCode(ResponseUtil.HTTP_SUCCESS);
            requestContext.set("isSuccess",true);
            return null;
        }
        //如果需要token
        else {
            String token = TokenUtil.getToken(request);

            if(null != token && !"".equals(token)){
                requestContext.addZuulRequestHeader("token", "1");
                requestContext.setSendZuulResponse(true);
                requestContext.setResponseStatusCode(ResponseUtil.HTTP_SUCCESS);
                requestContext.set("isSuccess",true);
            }
            else{
                requestContext.setSendZuulResponse(false);
                requestContext.setResponseStatusCode(ResponseUtil.HTTP_SUCCESS);
                requestContext.set("isSuccess",false);
                //使得返回中文不乱码
                response.setContentType("application/json; charset=UTF-8");
                //响应内容
                requestContext.setResponseBody(ResponseUtil.unlogin().toString());
            }
            return null;
        }
    }
}
