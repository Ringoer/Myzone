package xmu.ringoer.myzone.zuul.filters.error;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import xmu.ringoer.myzone.zuul.utils.ResponseUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Ringoer
 */
public class ErrorFilter extends ZuulFilter {

    private static final Logger logger = LoggerFactory.getLogger(ErrorFilter.class);
    
    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 4;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        logger.info("Start Error Filter");

        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();
        logger.info(String.format("%s AccessErrorFilter request to %s", request.getMethod(), request.getRequestURL().toString()));

        requestContext.setSendZuulResponse(false);
        requestContext.setResponseStatusCode(ResponseUtil.SERVER_ERROR);
        response.setContentType("application/json; charset=UTF-8");

        ZuulException exception;
        try {
            exception = findZuulException(requestContext.getThrowable());
            logger.info("发现错误：" + exception.getCause().getMessage());
            try (PrintWriter writer = response.getWriter()) {
                writer.print(ResponseUtil.fail(ResponseUtil.SERVER_ERROR, "服务器错误：" + exception.getCause().getMessage()).toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            logger.info("发生无法处理的异常");
        }
        return null;
    }

    private ZuulException findZuulException(Throwable throwable) {
        if (throwable.getCause() instanceof ZuulRuntimeException) {
            return (ZuulException)throwable.getCause().getCause();
        } else if (throwable.getCause() instanceof ZuulException) {
            return (ZuulException)throwable.getCause();
        } else {
            return throwable instanceof ZuulException ? (ZuulException)throwable : new ZuulException(throwable, ResponseUtil.SERVER_ERROR, null);
        }
    }
}
