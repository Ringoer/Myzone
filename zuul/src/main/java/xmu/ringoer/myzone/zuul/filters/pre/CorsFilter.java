package xmu.ringoer.myzone.zuul.filters.pre;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import xmu.ringoer.myzone.zuul.dao.ZuulDao;
import xmu.ringoer.myzone.zuul.utils.ResponseUtil;
import xmu.ringoer.myzone.zuul.utils.TokenUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ringoer
 */
@Component
public class CorsFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(TokenFilter.class);

    @Autowired
    private ZuulDao zuulDao;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        if(request.getMethod().equals(RequestMethod.OPTIONS.name())){
            return true;
        }
        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        logger.info(String.format("%s AccessTokenFilter request to %s", request.getMethod(), request.getRequestURL().toString()));

        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Headers","Authorization");
        response.setHeader("Access-Control-Allow-Methods","GET,POST,PUT,DELETE");

        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(ResponseUtil.HTTP_SUCCESS);

        return null;
    }
}
