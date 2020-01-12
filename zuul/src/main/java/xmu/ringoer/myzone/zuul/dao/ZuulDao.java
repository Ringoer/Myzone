package xmu.ringoer.myzone.zuul.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import xmu.ringoer.myzone.zuul.domain.Role;
import xmu.ringoer.myzone.zuul.mapper.ZuulMapper;

import java.util.List;

/**
 * @author Ringoer
 */
@Repository
public class ZuulDao {

    private static final Logger logger = LoggerFactory.getLogger(ZuulDao.class);
    
    @Autowired
    private ZuulMapper zuulMapper;

    public boolean checkUrl(String url, String method, String roleId) {
        List<Role> uris = zuulMapper.findUrisByRoleId(Integer.parseInt(roleId));
        logger.info("URIs = " + uris);

        logger.info(new Role(parseUrl(url), method).toString());

        return uris.contains(new Role(parseUrl(url), method));
    }

    public boolean checkNoTokenUris(String url, String method) {
        List<Role> uris = zuulMapper.findUrisByRoleId(0);
        logger.info("URIs = " + uris);

        logger.info(new Role(parseUrl(url), method).toString());

        return uris.contains(new Role(parseUrl(url), method));
    }

    private String parseUrl(String curUrl) {
        StringBuilder url = new StringBuilder();
        String[] paths = curUrl.split("/");
        for (String path : paths) {
            if("".equals(path)) {
                continue;
            }
            url.append("/");
            try {
                Integer.parseInt(path);
            } catch (Exception e) {
                url.append(path);
                continue;
            }
            url.append("{id}");
        }
        return url.toString();
    }
}
