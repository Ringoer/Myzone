package xmu.ringoer.myzone.zuul.filters.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
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
 */
@Component
public class TokenFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Autowired
    private ZuulDao zuulDao;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 2;
    }

    @Override
    public boolean shouldFilter() {
        String strToken = "token";
        RequestContext requestContext = RequestContext.getCurrentContext();
        if(null == requestContext.getZuulRequestHeaders().get(strToken)){
            return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        logger.info(String.format("%s AccessTokenFilter request to %s", request.getMethod(), request.getRequestURL().toString()));

        String token = TokenUtil.getToken(request);
        Claims claims = TokenUtil.parseToken(token);
        if(!TokenUtil.checkTokenType(claims)){
            //使得zuul不再继续向下执行过滤器，在此处输出
            requestContext.setSendZuulResponse(false);
            //响应码
            requestContext.setResponseStatusCode(ResponseUtil.HTTP_SUCCESS);
            //使得返回中文不乱码
            response.setContentType("application/json; charset=UTF-8");
            //响应内容
            requestContext.setResponseBody(ResponseUtil.fail(ResponseUtil.BAD_ARGUMENT,"token不合法！").toString());
            return null;
        }

        String regex = ":" + request.getLocalPort();
        logger.info("regex for split to get URL = " + regex);

        String url = request.getRequestURL().toString().split(regex)[1];
        logger.info("url = " + url);

        logger.info("claims = " + claims.toString());

        logger.info("keyset = " + claims.keySet().toString());

        String userId = claims.get("userId").toString();
        requestContext.addZuulRequestHeader("userId", userId);
        logger.info("userId = " + userId);

        String roleId = claims.get("roleId").toString();
        requestContext.addZuulRequestHeader("roleId", roleId);
        logger.info("roleId = " + roleId);

        if(!zuulDao.checkUrl(url, request.getMethod(), roleId)) {
            //使得zuul不再继续向下执行过滤器，在此处输出
            requestContext.setSendZuulResponse(false);
            //设置响应码
            requestContext.setResponseStatusCode(ResponseUtil.HTTP_SUCCESS);
            //使得返回中文不乱码
            response.setContentType("application/json; charset=UTF-8");
            //响应内容
            String userRoleId = "1";
            if(userRoleId.equals(roleId)) {
                requestContext.setResponseBody(ResponseUtil.userUnauthz().toString());
            } else {
                requestContext.setResponseBody(ResponseUtil.adminUnauthz().toString());
            }
            return null;
        }

        if(!TokenUtil.checkTokenTime(token)){
            logger.info("token已过期");
            requestContext.addZuulRequestHeader("exp", "1");
        }

        return null;
    }
}
